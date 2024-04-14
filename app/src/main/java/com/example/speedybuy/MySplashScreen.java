package com.example.speedybuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MySplashScreen extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        boolean flag=  sharedPreferences.getBoolean("flag",false);
        new Handler().postDelayed(() -> {
            if(flag){
                intent=new Intent(MySplashScreen.this, MainActivity.class);

            }
            else {
                intent=new Intent(MySplashScreen.this,firstActivity.class);
            }
            startActivity(intent);
            finish();
        },500);
    }
}