package com.example.speedybuy.AuthenticationU;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.speedybuy.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Forget extends AppCompatActivity {
    MaterialToolbar toolbar;
    private FirebaseAuth mAuth;
    TextInputEditText forget_email_id;
    AppCompatButton forget_continue_btn;
    CircularProgressIndicator circularProgressIndicator_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initializeViews();
        forget_continue_btn.setOnClickListener(v -> setForget_continue_btn());
    }


    private void initializeViews(){
        mAuth = FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.forget_toolbar);
        forget_email_id=findViewById(R.id.forget_email_id);
        forget_continue_btn=findViewById(R.id.forget_continue_btn);
        circularProgressIndicator_forget=findViewById(R.id.circularProgressIndicator_forget);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
    private void resetPass(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            circularProgressIndicator_forget.setVisibility(View.GONE);
            if(task.isSuccessful()){
                new MaterialAlertDialogBuilder(Forget.this)
                        .setTitle(getString(R.string.dialog_title_link))
                        .setMessage(getString(R.string.dialog_subtitle_link))

                        .setPositiveButton(getString(R.string.dialog_positive_button_link), (dialog, which) -> {
                            dialog.dismiss();
                            finish();
                        })
                        .show();
            }
            else {
                Toast.makeText(Forget.this, "something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setForget_continue_btn(){
        if(Objects.requireNonNull(forget_email_id.getText()).toString().isEmpty()){
            new MaterialAlertDialogBuilder(Forget.this)
                    .setTitle(getString(R.string.dialog_title))
                    .setMessage(getString(R.string.dialog_body))
                    .setPositiveButton(getString(R.string.dialog_positive_button), (dialog, which) -> dialog.dismiss()).show();

        }
        else {
            circularProgressIndicator_forget.setVisibility(View.VISIBLE);
            String email= Objects.requireNonNull(forget_email_id.getText()).toString();
            resetPass(email);

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}