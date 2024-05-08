package com.example.musicapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder> {

    private List<SongModel> songList;
    ArrayList<String> arrayListUrl=new ArrayList<>();
    ArrayList<String> arrayListSong=new ArrayList<>();
    ArrayList<String> arrayListArtist=new ArrayList<>();
    ArrayList<String> arrayListImage=new ArrayList<>();
    public MyAdapter(List<SongModel> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.raw_layout,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        //i will access our textview here to set retrieved name url image, url,etc
        holder.songNameTextView.setText(songList.get(position).getSong());
        holder.songArtistTextView.setText(songList.get(position).getArtist());
        holder.songUrlTextView.setText(songList.get(position).getUrl());
        holder.songCoverTextView.setText(songList.get(position).getCover());


        //add data to arraylist
        if(!(arrayListUrl.contains(songList.get(position).getUrl())))
            arrayListUrl.add(songList.get(position).getUrl());
        if(!(arrayListSong.contains(songList.get(position).getSong())))
            arrayListSong.add(songList.get(position).getSong());
        if(!(arrayListArtist.contains(songList.get(position).getArtist())))
            arrayListArtist.add(songList.get(position).getArtist());
        if(!(arrayListImage.contains(songList.get(position).getCover())))
            arrayListImage.add(songList.get(position).getCover());

        //now on click any song we have to pass songname, artist,cover and url
        //for that i will pass these string in intent

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),PlayerActivity.class);

                intent.putExtra("song_name",holder.songNameTextView.getText().toString());
                intent.putExtra("artist_name",holder.songArtistTextView.getText().toString());
                intent.putExtra("external_urls",holder.songUrlTextView.getText().toString());
                intent.putExtra("cover",holder.songCoverTextView.getText().toString());

                //pass these arraylist and position to next activity
                intent.putExtra("arrayListUrl",arrayListUrl);
                intent.putExtra("arrayListSong",arrayListSong);
                intent.putExtra("arrayListArtist",arrayListArtist);
                intent.putExtra("arrayListImage",arrayListImage);
                intent.putExtra("position",String.valueOf(position));

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTextView,songArtistTextView,songUrlTextView,songCoverTextView;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            songNameTextView=itemView.findViewById(R.id.title);
            songArtistTextView=itemView.findViewById(R.id.artist);
            songUrlTextView=itemView.findViewById(R.id.url);
            songCoverTextView=itemView.findViewById(R.id.cover);
        }
    }
}
