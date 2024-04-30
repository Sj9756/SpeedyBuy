package com.example.speedybuy.AuthenticationU;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.airbnb.lottie.LottieAnimationView;
import com.example.speedybuy.MainActivity;
import com.example.speedybuy.R;
import com.example.speedybuy.key.Ikey;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {
    LinearLayout register_user;
    CircularProgressIndicator circularProgressIndicator_log_in;
    AppCompatButton login_btn;
    EditText login_email_id, login_password;
    private FirebaseAuth mAuth;
    LottieAnimationView lottieAnimationView;
    ImageView google_sign_btn;
    TextView forget_pass_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login);
        initializeViews();
        Intent intent=getIntent();
        if(intent.hasExtra(Ikey.EMAIL)){
            String email=intent.getStringExtra(Ikey.EMAIL);
            String password=intent.getStringExtra(Ikey.PASSWORD);
            login_email_id.setText(email);
            login_password.setText(password);
        }


        register_user.setOnClickListener(v -> {
            Intent registerIntent=new Intent(Login.this,SignUp.class);
            startActivity(registerIntent);
            finish();
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
        });
        login_btn.setOnClickListener(v -> logInButtonAction());

        google_sign_btn.setOnClickListener(v -> {
            lottieAnimationView.setVisibility(View.VISIBLE);
            logInWithGoogle();
        });
        OnBackPressedCallback ob = createOnBackPressed();
        getOnBackPressedDispatcher().addCallback(this, ob);

        forget_pass_btn.setOnClickListener(v -> {
            Intent forget=new Intent(Login.this,Forget.class);
            startActivity(forget);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
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
    public void handleSignIn(GetCredentialResponse result) {
        Credential credential = result.getCredential();
        if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(credential.getType())) {
            GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());
            String idToken = googleIdTokenCredential.getIdToken();
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(firebaseCredential).addOnCompleteListener(task -> {
                lottieAnimationView.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("flag", true);
                        editor.putString("name", user.getDisplayName());
                        editor.putString("email", user.getEmail());
                        String url = Objects.requireNonNull(user.getPhotoUrl()).toString();
                        editor.putString("profile", url);
                        editor.apply();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                        finish();

                    } else {
                        Log.w("fail", "signInWithCredential:failure", task.getException());
                        Toast.makeText(Login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void logInWithEmailPassword(String email,String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            circularProgressIndicator_log_in.setVisibility(View.GONE);
            if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("flag", true);
                        editor.putString("email", user.getEmail());
                        editor.apply();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } else {
                        Log.w("fail", "signInWithCredential:failure", task.getException());
                        Toast.makeText(Login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Log.e("error", Objects.requireNonNull(task.getException()).toString());
                new MaterialAlertDialogBuilder(Login.this)
                        .setTitle(getString(R.string.dialog_login_fail_title))
                        .setMessage(getString(R.string.dialog_login_fail_sub_title))
                        .setPositiveButton(getString(R.string.dialog_positive_button_link), (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }
    private void initializeViews() {
        mAuth = FirebaseAuth.getInstance();
        lottieAnimationView = findViewById(R.id.animationView);
        login_email_id = findViewById(R.id.login_email_id);
        login_password = findViewById(R.id.login_password);
        login_btn=findViewById(R.id.login_btn);
        google_sign_btn = findViewById(R.id.google_sign_btn);
        register_user=findViewById(R.id.register_user);
        circularProgressIndicator_log_in=findViewById(R.id.circularProgressIndicator_log_in);
        forget_pass_btn=findViewById(R.id.forget_pass_btn);
    }

    private void logInButtonAction(){
        if (login_email_id.getText().toString().isEmpty()){
            login_email_id.setError("please fill required field");
            login_email_id.requestFocus();
        } else if (login_password.getText().toString().isEmpty()) {
            login_password.setError("please fill required field");
            login_password.requestFocus();
        } else{
            circularProgressIndicator_log_in.setVisibility(View.VISIBLE);
            String email = login_email_id.getText().toString();
            String password = login_password.getText().toString();
            logInWithEmailPassword(email, password);
        }
    }
    private void logInWithGoogle(){
        byte[] nonce = new byte[16];
        new SecureRandom().nextBytes(nonce);
        String randomNonce = new String(nonce);
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(getString(R.string.default_web_client_id))
                .setNonce(randomNonce)
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();
        CredentialManager credentialManager = CredentialManager.create(this);
        CancellationSignal cancellationSignal=new CancellationSignal();
        Executor executor= Runnable::run;

        credentialManager.getCredentialAsync(
              this,
                request,
                cancellationSignal,
                executor,
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {

                    @Override
                    public void onResult(GetCredentialResponse result) {
                        handleSignIn(result);
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        Log.d("fail",e.toString());
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        lottieAnimationView.setVisibility(View.GONE);
                    }
                }
);
    }
}
