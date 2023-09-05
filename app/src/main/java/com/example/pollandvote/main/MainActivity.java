package com.example.pollandvote.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.pollandvote.Admin.registation.AdminLogin;
import com.example.pollandvote.R;
import com.example.pollandvote.Voter.register_login.LoginUser;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout_voter, linearLayout_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout_voter = findViewById(R.id.linearLayout_voter);
        linearLayout_admin = findViewById(R.id.linearLayout_admin);
        linearLayout_voter.setOnClickListener(v -> {
            linearLayout_voter.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.focus));
            linearLayout_admin.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
            Intent intent = new Intent(getApplicationContext(), LoginUser.class);
            startActivity(intent);
        });
        linearLayout_admin.setOnClickListener(v -> {
            linearLayout_admin.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.focus));
            linearLayout_voter.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
            Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
            startActivity(intent);
        });
    }
}