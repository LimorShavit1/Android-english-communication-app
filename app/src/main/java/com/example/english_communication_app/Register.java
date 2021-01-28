package com.example.english_communication_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText etFullNameR, etEmailR, etPasswordR,etCountry,etPhone;
    Button btnSubmitRegister, btnToLogin;
    RadioGroup radioGroup;
    RadioButton selectedRBlevel;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullNameR = findViewById(R.id.etFullNameInput);
        etEmailR = findViewById(R.id.etEmailInput);
        etPasswordR = findViewById(R.id.etPasswordInput);
        etCountry = findViewById(R.id.etCountry);
        etPhone = findViewById(R.id.etPhone);

        fAuth = FirebaseAuth.getInstance();

        btnSubmitRegister = findViewById(R.id.btnRegister);
        btnSubmitRegister.setOnClickListener(this);

        btnToLogin = findViewById(R.id.btnToLogin);
        btnToLogin.setOnClickListener(this);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        //radioGroup.clearCheck();

    }

    @Override
    public void onClick(View v) {
        String name, email, password,country,level,phone ;
        if (v == btnToLogin) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }
        if (v == btnSubmitRegister) {
            //extract user input from register form
            int selectedId = radioGroup.getCheckedRadioButtonId();
            selectedRBlevel = findViewById(selectedId);
            name = etFullNameR.getText().toString();
            email = etEmailR.getText().toString();
            password = etPasswordR.getText().toString();
            country = etCountry.getText().toString() ;
            level = selectedRBlevel.getText().toString();
            phone = etPhone.getText().toString();
            //Toast.makeText(Register.this,selectedRBlevel.getText().toString(),Toast.LENGTH_SHORT).show();


            if(name.isEmpty() && email.isEmpty() && password.isEmpty() && country.isEmpty() && phone.isEmpty()){
                Toast.makeText(Register.this, "Please complete all required data", Toast.LENGTH_LONG).show();
                return;
            }

            //validate user input
            if(name.isEmpty() || !InputValidation.isNameValid(name)){
                etFullNameR.setError("Valid Full Name Is Required");
                return;
            }
            if(email.isEmpty() || !InputValidation.isEmailValid(email)){
                etEmailR.setError("Valid Email Is Required");
                return;
            }
            if(password.isEmpty()){
                etPasswordR.setError("Password is Required");
                return;
            }
            if(country.isEmpty()){
                etCountry.setError("Country is Required");
                return;
            }
            if(phone.isEmpty() || !InputValidation.isPhoneValid(phone)){
                etPhone.setError("Valid Phone Number Is Required");
                return;
            }
//            if(level.isEmpty()){
//                selectedRBlevel.setError("Phone Number is Required");
//                Toast.makeText(Register.this, "Please complete all required data", Toast.LENGTH_LONG).show();
//                return;
//            }

            fAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, send user to the next page
                                FirebaseUser user = fAuth.getCurrentUser();
                                String uid = user.getUid();
                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("uid",uid);
                                hashMap.put("name",name);
                                hashMap.put("email",email);
                                hashMap.put("phone",phone);
                                hashMap.put("country",country);
                                hashMap.put("password",password);
                                hashMap.put("level",level);
                                //firebase database instance
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                //path to store user data named "Users"
                                DatabaseReference reference = database.getReference("Users");
                                //put data within hashmap in database
                                reference.child(uid).setValue(hashMap);


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