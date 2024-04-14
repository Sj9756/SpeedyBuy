package com.example.speedybuy.fragmants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.speedybuy.Login;
import com.example.speedybuy.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class Fragment_setting extends Fragment {


    public Fragment_setting() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        MaterialToolbar toolbar=view.findViewById(R.id.toolbar);
         activity.setSupportActionBar(toolbar);
         Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
         activity.getSupportActionBar().setTitle("");
        ImageView profile=view.findViewById(R.id.setting_profile);
        TextView name=view.findViewById(R.id.user_name);
        TextView email_add=view.findViewById(R.id.email_id);
        Button log_out=view.findViewById(R.id.sign_out_button);
        SharedPreferences pref= requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String Username=pref.getString("name","John");
        String emailid=pref.getString("email","example12");
        String url=pref.getString("profile","");
        url=url.replace("s96","s900");
        Log.d("url",url);
        Glide.with(this).load(url).into(profile);
        name.setText(Username);
        email_add.setText(emailid);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=pref.edit();
                editor.putBoolean("flag",false);
                editor.apply();
                Intent intent =new Intent(requireActivity(), Login.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        return view;
    }
}