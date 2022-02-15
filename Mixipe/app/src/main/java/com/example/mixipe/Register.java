package com.example.mixipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;


public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword, mConfirm;
    Button mRegisterBtn, mLoginBtn;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName       = findViewById(R.id.FullName);
        mEmail          = findViewById(R.id.Email);
        mPassword       = findViewById(R.id.Password);
        mConfirm        = findViewById(R.id.Confirm);
        mRegisterBtn    = findViewById(R.id.RegisterBtn);
        mLoginBtn       = findViewById(R.id.LoginBtn);

        mAuth           = FirebaseAuth.getInstance();
        progressBar     = findViewById(R.id.progressBar3);


        if(mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText() .toString() .trim();
                String password = mPassword.getText() .toString() .trim();

                // User must enter a name
                if(TextUtils.isEmpty(email)) {
                    mFullName.setError("Name is Required");
                    return;
                }

                // User must enter a valid email
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }

                // User must set a password
                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }

                // Password set must be at least 6 characters long
                if(password.length() < 6) {
                    mPassword.setError("Password must be >= to 6 characters");
                    return;
                }

                // Verifies if both passwords match
                String pass2 = mConfirm.getText().toString();
                if (!password.equals(pass2)) {
                    Toast.makeText(Register.this, "Passwords must be identical", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Register the user in firebase
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });




            }
        });

        // Switches to Login Page
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
}