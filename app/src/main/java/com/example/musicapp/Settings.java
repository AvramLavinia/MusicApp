package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Settings extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://musicapp-234b3-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText fullname1 =findViewById(R.id.fullname1);
        final EditText password1 =findViewById(R.id.password1);
        final EditText conPassword1 =findViewById(R.id.conPassword1);
        final EditText phone1=findViewById(R.id.phone1);
        final Button uploadBtn=findViewById(R.id.uploadBtn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullnameTxt1=fullname1.getText().toString();
                final String phoneTxt1=phone1.getText().toString();
                String passwordTxt1=password1.getText().toString();
                String conPasswordTxt1=conPassword1.getText().toString();
                HashMap hashMap=new HashMap();
                if(!passwordTxt1.equals(conPasswordTxt1)){
                    Toast.makeText(Settings.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();

                }
                else {
                    hashMap.put("fullname", fullnameTxt1);
                    hashMap.put("password", passwordTxt1);


                }


             databaseReference.child("users").child(phoneTxt1).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                 @Override
                 public void onSuccess(Object o) {
                     Toast.makeText(Settings.this, "Your data is updated", Toast.LENGTH_SHORT).show();
                 }
             });
            }
        });
    }
}

