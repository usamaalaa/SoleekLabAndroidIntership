package com.example.usama.androidinternship_osamaalaa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText loginEmail, loginPass;
    Button signUpBtn, siginInBtn;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.loginEmail);
        loginPass = findViewById(R.id.loginPass);
        signUpBtn = findViewById(R.id.siginUpBtn);
        siginInBtn = findViewById(R.id.loginBtn2);

        signUpBtn.setOnClickListener(e -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });

        siginInBtn.setOnClickListener(e -> {
            loginUser();
        });
    }

    public void loginUser() {
        String email = loginEmail.getText().toString();
        String pass = loginPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Type Your Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please Type Your Password", Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Login User.....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                loginEmail.setText("");
                loginPass.setText("");
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            } else {
                loginEmail.setText("");
                loginPass.setText("");
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
