package com.example.speedybuy.AuthenticationU;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.speedybuy.R;
import com.example.speedybuy.key.Ikey;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    CircularProgressIndicator circularProgressIndicator;
    private FirebaseAuth mAuth;
    LinearLayout login_activity_navi;
    AppCompatEditText sign_up_email, sign_up_password, sign_up_password_confirm;
    AppCompatButton sign_up_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_sign_up);
        initializeViews();
        mAuth = FirebaseAuth.getInstance();
        sign_up_password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = Objects.requireNonNull(sign_up_password.getText()).toString();
                String confirmPassword = s.toString();
                if (!password.equals(confirmPassword)) {
                    sign_up_password_confirm.setError("Passwords do not match");
                } else {
                    sign_up_password_confirm.setError(null);

                }
            }
        });
        login_activity_navi.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });
        sign_up_btn.setOnClickListener(v -> {
            if (Objects.requireNonNull(sign_up_email.getText()).toString().isEmpty()) {
                sign_up_email.setError("please fill required field");
                sign_up_email.requestFocus();
            } else if (Objects.requireNonNull(sign_up_password.getText()).toString().isEmpty()) {
                sign_up_password.setError("please fill required field");
                sign_up_password.requestFocus();
            }
            else if (Objects.requireNonNull(sign_up_password_confirm.getText()).toString().isEmpty()){
                sign_up_password_confirm.requestFocus();
                sign_up_password_confirm.setError("please fill confirm password");
            }
            else {
                String email = sign_up_email.getText().toString();
                String password = sign_up_password.getText().toString();
                String confirm =sign_up_password_confirm.getText().toString();
                if(confirm.equals(password)){
                    circularProgressIndicator.setVisibility(View.VISIBLE);
                    signUpWithEmail(email,password);
                }
                else {
                    Toast.makeText(SignUp.this, "password didn't  match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initializeViews() {
        OnBackPressedCallback ob = createOnBackPressed();
        getOnBackPressedDispatcher().addCallback(this, ob);
        login_activity_navi = findViewById(R.id.login_activity_navi);
        sign_up_email = findViewById(R.id.sign_up_email);
        sign_up_password = findViewById(R.id.sign_up_password);
        sign_up_password_confirm = findViewById(R.id.sign_up_password_confirm);
        sign_up_btn = findViewById(R.id.sign_up_btn);
        circularProgressIndicator=findViewById(R.id.circularProgressIndicator);
    }
    private void signUpWithEmail(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    circularProgressIndicator.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Log.d("log", "createUserWithEmail:success");
                        Toast.makeText(SignUp.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SignUp.this, Login.class);
                        intent.putExtra(Ikey.EMAIL,email);
                        intent.putExtra(Ikey.PASSWORD,password);
                        startActivity(intent);
                        finish();
                    } else {

                        Log.w("error", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUp.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
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