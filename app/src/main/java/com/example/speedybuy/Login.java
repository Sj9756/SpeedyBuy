package com.example.speedybuy;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

/** @noinspection deprecation*/
public class Login extends AppCompatActivity {
    EditText Userid, Password;
    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;
    LottieAnimationView lottieAnimationView;
    private final static int RC_SIGN_IN = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login);
        lottieAnimationView = findViewById(R.id.animationView);
        Userid = findViewById(R.id.user_id);
        Password = findViewById(R.id.password_EditText);
        ImageView button = findViewById(R.id.bt_sign_in);

        button.setOnClickListener(v -> {
            lottieAnimationView.setVisibility(View.VISIBLE);
            signInWithGoogle();
        });
        OnBackPressedCallback ob = createOnBackPressed();
        getOnBackPressedDispatcher().addCallback(this, ob);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInWithGoogle() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            } else {
                // Handle sign-out failure
                Toast.makeText(Login.this, "Failed to sign out.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode!=RESULT_CANCELED) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.e("ApiException", "Error occurred", e);
            }
        }
       else if (resultCode == RESULT_CANCELED && requestCode==RC_SIGN_IN) {
            lottieAnimationView.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Sign-in canceled", Toast.LENGTH_SHORT).show();
        }


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            SharedPreferences pref=getSharedPreferences("login",MODE_PRIVATE);
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putBoolean("flag",true);
                            editor.putString("name",user.getDisplayName());
                            editor.putString("email",user.getEmail());
                            String url= Objects.requireNonNull(user.getPhotoUrl()).toString();
                            editor.putString("profile",url);
                            editor.apply();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Log.w("fail", "signInWithCredential:failure", task.getException());
                        Toast.makeText(Login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private OnBackPressedCallback createOnBackPressed() {
        return new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        };
    }
}
