package com.example.english_communication_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainApp extends AppCompatActivity implements View.OnClickListener{
    Button logOut, btnEditprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);


        //Logout the user
        logOut = findViewById(R.id.btnLogout);
        logOut.setOnClickListener(this);

        //Navigate to "Edit Profile" activity
        btnEditprofile = findViewById(R.id.btnEditprofile);
        btnEditprofile.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == logOut){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        if(v == btnEditprofile){
            startActivity(new Intent(getApplicationContext(),EditProfile.class));
            finish();
        }
    }
}