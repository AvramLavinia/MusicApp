package com.example.musicapp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongModel {
    @Expose
    @SerializedName("song_name")
    String song;

    @Expose
    @SerializedName("external_urls")
    String url;

    @Expose
    @SerializedName("artist_name")
    String artists;

    @Expose
    @SerializedName("cover")
    String cover;

    public SongModel(String song, String url, String artists,String cover) {
        this.song = song;
        this.url = url;
        this.artists = artists;
        this.cover = cover;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtist() {
        return artists;
    }

    public void setArtist(String artists) {
        this.artists = artists;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
