package com.example.english_communication_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener{
 Button btnToRegister, btnLogin;
 EditText userEmail, userPassword;
 FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize
        firebaseAuth=FirebaseAuth.getInstance();

        // Move to registration activity
        btnToRegister = findViewById(R.id.btnToRegister);
        btnToRegister.setOnClickListener(this);

        //Login the user
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        //extract user inputs -> email & password
        userEmail = findViewById(R.id.edEmailInputLogin);
        userPassword = findViewById(R.id.etPasswordInputLogin);

    }

    @Override
    public void onClick(View v) {
        if (v == btnToRegister) {
            Intent i = new Intent(getApplicationContext(), Register.class);
            startActivity(i);
        }
        if (v == btnLogin) {
            // Extract & validate user inputs
            if(userEmail.getText().toString().isEmpty()){
                userEmail.setError("Email is missing");
                return;
            }
            if(userPassword.getText().toString().isEmpty()){
                userPassword.setError("Password is missing");
                return;
            }
            //data is valid -> Login the user
            firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(),userPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Login success, send user to the Main activity
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        startActivity(new Intent(getApplicationContext(),MainApp.class));
                        finish();
                    } else {
                        // If Login fails, display a message to the user.
                        Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //If user previously logged in to app current user != null
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            // if true -> send user to main activity, app wont ask user to login again
            startActivity(new Intent(getApplicationContext(),MainApp.class));
            finish();
        }
    }
}
