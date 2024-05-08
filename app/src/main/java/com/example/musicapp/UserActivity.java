package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://musicapp-234b3-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final EditText fullname=findViewById(R.id.fullname);
        final EditText email=findViewById(R.id.email);
        final EditText phone=findViewById(R.id.phone);

        //get data from EditTexts into Strings variables

        final String phoneTxt=getIntent().getStringExtra("user");
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(phoneTxt)){
                    //mobile is exist in firebase database


                    final String getfullname=snapshot.child(phoneTxt).child("fullname").getValue(String.class);
                    final String getemail=snapshot.child(phoneTxt).child("email").getValue(String.class);
                    fullname.setText(getfullname);
                    email.setText(getemail);
                    phone.setText(phoneTxt);

            }


        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
}
}