package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
  private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button profil=findViewById(R.id.profil);
        final Button setari=findViewById(R.id.setari);
        final Button playlist=findViewById(R.id.playlist);
        final Button playlistOff=findViewById(R.id.playlistOff);
        final Button signOut=findViewById(R.id.signOut);

        user=getIntent().getStringExtra("user");

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the userprofile
                Intent intent = new Intent(MainActivity.this,UserActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);



            }
        });
        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the playlist
                Intent intent = new Intent(MainActivity.this, SongList.class);
                startActivity(intent);



            }
        });
        playlistOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start playlist
                Intent intent = new Intent(MainActivity.this,MusicPlayer.class);
                startActivity(intent);
            }
        });

        setari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //porneste setarile
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);



            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //iesire
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);



            }
        });
    }
}