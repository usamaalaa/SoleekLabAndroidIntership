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

public class RegistrationActivity extends AppCompatActivity {
    EditText email, pass, confPass;
    Button registerBtn, loginBtn;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.registerEmail);
        pass = findViewById(R.id.registerPass);
        confPass = findViewById(R.id.registerConfirmPass);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.loginBtn);

        progressDialog = new ProgressDialog(this);

        loginBtn.setOnClickListener(e -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });
        registerBtn.setOnClickListener(e -> {
            registerUser();
        });
    }

    public void registerUser() {
        String userEmail = email.getText().toString();

        String userPass = pass.getText().toString();
        String userConfPass = confPass.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Please Type Your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPass)) {
            Toast.makeText(this, "Please Type Your Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userConfPass)) {
            Toast.makeText(this, "Please Confirm Your password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userPass.equals(userConfPass)) {
            Toast.makeText(this, "Your password and confirm pass didn't equal each other", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(RegistrationActivity.this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        email.setText("");
                        pass.setText("");
                        confPass.setText("");
                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        email.setText("");
                        pass.setText("");
                        confPass.setText("");
                        Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
