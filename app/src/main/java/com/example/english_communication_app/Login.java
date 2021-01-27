package com.example.english_communication_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity implements View.OnClickListener{
 Button btnToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnToRegister = findViewById(R.id.btnToRegister);
        btnToRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnToRegister) {
            Intent i = new Intent(getApplicationContext(), Register.class);
            startActivity(i);
        }
    }
}