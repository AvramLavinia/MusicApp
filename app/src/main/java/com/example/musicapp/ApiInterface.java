package com.example.musicapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("0TnOYISbd1XYRBk9myaseg/top-tracks")
    Call<List<SongModel>> getStudio();
}
