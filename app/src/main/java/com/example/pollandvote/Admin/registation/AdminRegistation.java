package com.example.pollandvote.Admin.registation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pollandvote.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class AdminRegistation extends AppCompatActivity {
    Button sign_up_btn;
    TextView login_btn;
    EditText fullName, password, confirmPassword, e_mail;
    FirebaseAuth auth;
    FirebaseFirestore db;
    List<EditText> editTextList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registation);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.admin_full_name);
        e_mail = findViewById(R.id.admin_email);
        password = findViewById(R.id.admin_regi_password);
        confirmPassword = findViewById(R.id.admin_confirm_password);

        editTextList = new ArrayList<>();
        editTextList.add(fullName);
        editTextList.add(e_mail);
        editTextList.add(password);
        editTextList.add(confirmPassword);

        login_btn = findViewById(R.id.login_Tab);
        sign_up_btn = findViewById(R.id.submitButton);
        register(sign_up_btn);

        login_btn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
            startActivity(intent);
        });
    }

    public void onTabClick(View view){}
    public void register(Button signup){
        signup.setOnClickListener(view -> {
            if(validateInput()) {
                login_btn.setVisibility(View.VISIBLE);
                login_btn.setTextColor(Color.GREEN);
                performSignUp(e_mail.getText().toString(), password.getText().toString(), fullName.getText().toString());
                clearField();
            }
        });
    }
    public void performSignUp(String email, String password, String fullName){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        //To store registration data
                        addToFireStore(email,password,fullName);
                    }
                    else {
                        // Signup failed, display a message to the user
                        Toast.makeText(this, "Failed to register...", Toast.LENGTH_SHORT).show();
                    }
                });
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Verification email sent
                    } else {
                        // Sending verification email failed
                    }
                });
        }

    }
    public boolean validateInput(){
        String f_name = fullName.getText().toString();
        String p_word = password.getText().toString();
        String c_password = confirmPassword.getText().toString();
        String mail = e_mail.getText().toString();

        String[] parts = mail.split("@");

        boolean allFieldsValid = true;
        for (EditText editText : editTextList) {
            String text = editText.getText().toString().trim();
            if (text.isEmpty()) {
                editText.setError("Field cannot be empty");
                allFieldsValid = false;
            }
        }
        if(f_name.length() < 3){
            fullName.setError("Enter name");
            return false;
        }else if(!validatePassword(p_word, c_password)){
            return false;
        }else if(mail.equals("") || Patterns.EMAIL_ADDRESS.matcher(parts[0].trim()).matches()){
            e_mail.setError("Enter mail");
            return false;
        }
        return allFieldsValid;
    }

    public boolean validatePassword(String passwordInput,String cpass) {
        // defining our own password pattern
        final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{4,}" +                // at least 4 characters
                        "$");
        if (passwordInput.isEmpty()) {
            password.setError("Field can not be empty");
            confirmPassword.setError("Field can not be empty");
            return false;
        }
        if(!passwordInput.equals(cpass)){
            password.setError("password and confirm password both should be same");
            confirmPassword.setError("password and confirm password both should be same");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password is too weak");
            confirmPassword.setError("Password is too weak");
            return false;
        } else {
            return true;
        }
    }
    public void addToFireStore(String email, String password, String fullName){
        RegistrationModule registrationModule = new RegistrationModule();
        registrationModule.setFullName(fullName);
        registrationModule.setPassword(password);
        registrationModule.setEmail(email);
        // Add a new document
        db.collection("AdminRegister").document(email)
                .set(registrationModule).addOnSuccessListener(unused -> Toast.makeText(AdminRegistation.this, "Success...", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(AdminRegistation.this, "Failed...", Toast.LENGTH_SHORT).show());

    }
    public void clearField(){
        fullName.setText("");
        e_mail.setText("");
        password.setText("");
        confirmPassword.setText("");
    }

    public void onRegisterConfirmPasswordToggleClick(View view) {
        TextInputEditText passwordEditText = findViewById(R.id.admin_confirm_password);
        eventHandling(view, passwordEditText);
    }
    public void onRegisterPasswordToggleClick(View view) {
        TextInputEditText passwordEditText = findViewById(R.id.admin_regi_password);
        eventHandling(view, passwordEditText);
    }
    public void eventHandling(View view, TextInputEditText passwordEditText){
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