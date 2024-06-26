package com.example.musicapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MusicPlayer extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView noMusicTextView;
    ArrayList<AudioModel> songsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        recyclerView=findViewById(R.id.recycler_view);
        noMusicTextView=findViewById(R.id.no_songs_text);

        if(checkPermission() == false){
            requestPermission();
            return;
        }
        String[] projection={
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection= MediaStore.Audio.Media.IS_MUSIC +"!=0";
        Cursor cursor=getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while (cursor.moveToNext()){
            AudioModel songData=new AudioModel(cursor.getString(1), cursor.getString(0),cursor.getString(2));
            if (new File(songData.getPath()).exists()){
                songsList.add(songData);
            }
        }
        if(songsList.size()==0){
            noMusicTextView.setVisibility(View.VISIBLE);
        }
        else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MusicListAdapter(songsList,getApplicationContext()));
        }
    }

    boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(MusicPlayer.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }
    void requestPermission(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(MusicPlayer.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MusicPlayer.this, "Read permission is required, please allow from settings", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(MusicPlayer.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(recyclerView!=null){
            recyclerView.setAdapter(new MusicListAdapter(songsList,getApplicationContext()));
        }
    }
}