package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;


public class PlayerActivity extends AppCompatActivity {
    ImageView CoverImage;
    TextView SongTitle,SongArtist;

    Button Play, Pause,Next,Prev;
    MediaPlayer mediaPlayer;  //to play songs

    SeekBar seekBar;
    TextView Pass,Due; //for time due and pass

    Handler handler;
    String out,out2;
    Integer diff,position;

    ArrayList<String> arrayListUrl;
    ArrayList<String> arrayListSong;
    ArrayList<String> arrayListArtist;
    ArrayList<String> arrayListImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        CoverImage=(ImageView)findViewById(R.id.coverImageView);
        SongTitle=(TextView) findViewById(R.id.song_title);
        SongArtist=(TextView) findViewById(R.id.song_artist);

        Play=(Button)findViewById(R.id.playBtn);
        Pause=(Button)findViewById(R.id.pauseBtn);
        seekBar=(SeekBar) findViewById(R.id.seek_bar);
        Pass=(TextView) findViewById(R.id.tv_pass);
        Due=(TextView) findViewById(R.id.tv_due);

        Next=(Button)findViewById(R.id.next);
        Prev=(Button)findViewById(R.id.prev);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayListUrl.size()==position+1){
                    position=0;
                    init(arrayListSong.get(position),arrayListArtist.get(position),arrayListUrl.get(position),arrayListImage.get(position));
                }
                else {
                    position=position+1;
                    init(arrayListSong.get(position),arrayListArtist.get(position),arrayListUrl.get(position),arrayListImage.get(position));
                }
            }
        });

        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0){
                    position=arrayListUrl.size() -1;
                    init(arrayListSong.get(position),arrayListArtist.get(position),arrayListUrl.get(position),arrayListImage.get(position));
                }
            }
        });


        mediaPlayer=new MediaPlayer();
        handler=new Handler();

        Intent intent=getIntent();
        String song_name=intent.getStringExtra("song_name");
        String artist_name=intent.getStringExtra("artist_name");
        String url=intent.getStringExtra("external_urls");
        String cover=intent.getStringExtra("cover");

        arrayListUrl=intent.getStringArrayListExtra("arrayListUrl");
        arrayListSong=intent.getStringArrayListExtra("arrayListSong");
        arrayListArtist=intent.getStringArrayListExtra("arrayListArtist");
        arrayListImage=intent.getStringArrayListExtra("arrayListImage");
        position=Integer.parseInt(intent.getStringExtra("position"));

        Toast.makeText(this, song_name, Toast.LENGTH_SHORT).show();

       init(song_name,artist_name,url,cover);
        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });


        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });
        //method to initialize seekbar
        initializeSeekBar();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress*1000);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void init(String song_name, String artist_name, String url, String cover) {
        SongTitle.setText(song_name);
        SongArtist.setText(artist_name);


        Glide.with(this)
                .load(cover)
                .override(300,200)
                .into(CoverImage);

        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }

        try {
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        mediaPlayer.start();
        initializeSeekBar();


    }

    private void initializeSeekBar() {

        seekBar.setMax(mediaPlayer.getDuration()/1000); //let max limit of song // set total duration
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition=mediaPlayer.getCurrentPosition() /1000;
                    seekBar.setProgress(mCurrentPosition);

                    out=String.format("%02d:%02d",seekBar.getProgress() /60, seekBar.getProgress() %60);
                    Pass.setText(out);

                    diff=mediaPlayer.getDuration()/1000 - mediaPlayer.getCurrentPosition()/1000;
                    out2=String.format("%02d:%02d",diff /60,diff %60);
                    Due.setText(out2);
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    private void play(){
        mediaPlayer.start();
        Play.setVisibility(View.INVISIBLE);
        Pause.setVisibility(View.VISIBLE);
    }

    private void pause(){
        mediaPlayer.pause();
        Play.setVisibility(View.VISIBLE);
        Pause.setVisibility(View.INVISIBLE);

    }
}