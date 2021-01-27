package com.example.english_communication_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText etFullNameR, etEmailR, etPasswordR;
    Button btnSubmitRegister, btnToLogin;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullNameR = findViewById(R.id.etFullNameInput);
        etEmailR = findViewById(R.id.etEmailInput);
        etPasswordR = findViewById(R.id.etPasswordInput);

        fAuth = FirebaseAuth.getInstance();

        btnSubmitRegister = findViewById(R.id.btnRegister);
        btnSubmitRegister.setOnClickListener(this);

        btnToLogin = findViewById(R.id.btnToLogin);
        btnToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String fullName, email, password ;
        if (v == btnToLogin) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }
        if (v == btnSubmitRegister) {
            //extract user input
            fullName = etFullNameR.getText().toString();
            email = etEmailR.getText().toString();
            password = etPasswordR.getText().toString();

            //validate user input
            if(fullName.isEmpty()){
                etFullNameR.setError("Full Name is Required");
                return;
            }
            if(email.isEmpty()){
                etEmailR.setError("Email is Required");
                return;
            }
            if(password.isEmpty()){
                etPasswordR.setError("Password is Required");
                return;
            }

            fAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, send user to the next page
                                FirebaseUser user = fAuth.getCurrentUser();
                                startActivity(new Intent(getApplicationContext(),MainApp.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }
}