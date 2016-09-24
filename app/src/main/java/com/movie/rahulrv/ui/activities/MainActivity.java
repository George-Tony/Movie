package com.movie.rahulrv.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.movie.rahulrv.MovieAPI;
import com.movie.rahulrv.R;
import com.movie.rahulrv.RetrofitClient;

import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieAPI movieAPI = RetrofitClient.getInstance().create(MovieAPI.class);
        movieAPI.nowPlaying().subscribeOn(Schedulers.computation()).subscribe();
    }
}
