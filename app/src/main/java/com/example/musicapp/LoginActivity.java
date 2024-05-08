package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    //create obj of DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://musicapp-234b3-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        final EditText phone=findViewById(R.id.phone);
        final EditText password=findViewById(R.id.password);
        final Button loginBtn=findViewById(R.id.loginBtn);
        final TextView registerNowBtn=findViewById(R.id.registerNowBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneTxt=phone.getText().toString();
                final String passwordTxt=password.getText().toString();

                if(phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please enter your mobile or password",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if mobile/phone is exist in firebase database
                            if(snapshot.hasChild(phoneTxt)){
                                //mobile is exist in firebase database
                                //now get password of user from firebase data and match it with user entered password

                                final String getPassword=snapshot.child(phoneTxt).child("password").getValue(String.class);

                                if(getPassword.equals(passwordTxt)){
                                    Toast.makeText(LoginActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();

                                    //open MainActivity on success
                                    Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                                    intent.putExtra("user",phoneTxt);
                                   startActivity(intent);
                                    finish();

                                }
                                else{
                                    Toast.makeText(LoginActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(LoginActivity.this,"Wrong mobile number",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
         }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register activity
                startActivity(new Intent(LoginActivity.this,Register.class));

            }
        });




    }


}













