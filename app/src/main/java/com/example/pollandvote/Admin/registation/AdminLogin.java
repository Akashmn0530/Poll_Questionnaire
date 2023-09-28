package com.example.pollandvote.Admin.registation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pollandvote.Admin.homescreen.AdminHome;
import com.example.pollandvote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {
    Button login_btn;
    TextView forgotPasswordTab, registerTab;
    EditText username_edit, password_edit;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    public static String loginId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();

        username_edit = findViewById(R.id.admin_username);
        password_edit = findViewById(R.id.admin_password);

        progressBar = findViewById(R.id.idProgressBar1);
        progressBar.setVisibility(View.GONE);

        forgotPasswordTab = findViewById(R.id.forgotPasswordTab);
        registerTab = findViewById(R.id.signUpTab);
        register(registerTab);

        login_btn = findViewById(R.id.submitButton);
        loginValidate(login_btn);
//        login_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), AdminHome.class);
//                startActivity(intent);
//            }
//        });
    }

    public void onTabClick(View view) {
        forgotPasswordTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle forgot password click
                String email = username_edit.getText().toString();
                if (!email.isEmpty()) {
                    sendPasswordResetEmail(email);
                } else {
                    // Handle case when email field is empty
                    Toast.makeText(AdminLogin.this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminLogin.this, "Password reset email sent. Check your email inbox.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdminLogin.this, "Failed to send password reset email. Please check the email address.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void loginValidate(Button login_btn){
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username_edit.getText().toString().equals("") || password_edit.getText().toString().equals("")){
                    forgotPasswordTab.setVisibility(View.VISIBLE);
                    forgotPasswordTab.setTextColor(Color.RED);
                    progressBar.setVisibility(View.GONE);
                    username_edit.setError("Both fields are required");
                    password_edit.setError("Both fields are required");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginId = username_edit.getText().toString();
                    performLogin(username_edit.getText().toString(), password_edit.getText().toString());
                }
            }
        });
    }
    public void register(TextView signup){
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminRegistation.class);
                startActivity(intent);
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
    public void clearField(){
        username_edit.setText("");
        password_edit.setText("");
    }
    public void performLogin(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Login successful
                                FirebaseUser user = mAuth.getCurrentUser();
                                // Proceed with further actions
                                clearField();
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(), AdminHome.class);
                                startActivity(intent);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                username_edit.setError("Wrong username");
                                password_edit.setError("Wrong password");
                                forgotPasswordTab.setVisibility(View.VISIBLE);
                                forgotPasswordTab.setTextColor(Color.RED);
                            }
                        }
                    });
        } else {
        }
    }
}