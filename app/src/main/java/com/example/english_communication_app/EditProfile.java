package com.example.english_communication_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "profileActivity";
    Button btnSaveChanges, btnCancel;
    RadioGroup radioGroup;
    RadioButton selectedRBlevel;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        // Linking
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnSaveChanges.setOnClickListener(this);

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

    }

    @Override
    public void onClick(View v) {
        if (v == btnCancel) {
            // Navigate user to main page
            Intent i = new Intent(getApplicationContext(), MainApp.class);
            startActivity(i);
        }
        if (v == btnSaveChanges) {
            // Save changes & Navigate user to main page
            String useruid=fUser.getUid();
            int selectedId = radioGroup.getCheckedRadioButtonId();
            selectedRBlevel = findViewById(selectedId);
            String newLevel = selectedRBlevel.getText().toString();

            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(useruid);
            ref.child("level").setValue(newLevel);

            Toast.makeText(EditProfile.this, "Saved Successfully!",Toast.LENGTH_SHORT).show();

            // Navigate user back to main activity
            Intent i = new Intent(getApplicationContext(), MainApp.class);
            startActivity(i);
        }

    }
}