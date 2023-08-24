package com.example.pollandvote.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pollandvote.R;
import com.google.android.material.textfield.TextInputEditText;

public class AdminLogin extends AppCompatActivity {
    Button login_btn;
    TextView forgotPasswordTab;
    TextView registerTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        forgotPasswordTab = findViewById(R.id.forgotPasswordTab);
        registerTab = findViewById(R.id.signUpTab);
        register(registerTab);

        login_btn = findViewById(R.id.submitButton);
        loginVatidate(login_btn);
    }

    public void onTabClick(View view) {
    }
    public void loginVatidate(Button login_btn){
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminHome.class);
                startActivity(intent);
            }
        });
    }
    public void register(TextView signup){
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordTab.setVisibility(View.VISIBLE);
                forgotPasswordTab.setTextColor(Color.RED);
//                Intent intent = new Intent(getApplicationContext(), AdminHome.class);
//                startActivity(intent);
            }
        });
    }

    public void onPasswordToggleClick(View view) {
        TextInputEditText passwordEditText = findViewById(R.id.admin_password);
        int inputType = passwordEditText.getInputType();

        if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_password_toggle, 0);
        } else {
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_backarrow, 0);
        }

        passwordEditText.setSelection(passwordEditText.getText().length()); // Maintain cursor position
    }

}