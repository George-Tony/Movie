package com.movie.rahulrv.ui.movies.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.movie.rahulrv.MovieAPI;
import com.movie.rahulrv.MyApplication;
import com.movie.rahulrv.databinding.ActivityMovieDetailBinding;
import com.movie.rahulrv.model.Movie;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Activity for Movie Detail.
 */

public class MovieDetailActivity extends AppCompatActivity {
    private static final String ARG_MOVIE = "selectedMovie";
    private Movie movie;
    @Inject
    MovieAPI movieAPI;

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(ARG_MOVIE, movie);
        return intent;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication)getApplication()).getComponent().inject(this);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            movie = bundle.getParcelable(ARG_MOVIE);
        }

        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, com.movie.rahulrv.R.layout.activity_movie_detail);
        setContentView(binding.getRoot());
        binding.setMovie(movie);
        binding.executePendingBindings();
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        movieAPI.getVideos(movie.getId())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(videoWrapper -> {
                    if (videoWrapper.getResults().size() > 0) {
                        return Observable.from(videoWrapper.getResults());
                    } else {
                        return Observable.empty();
                    }
                }).first()
                .subscribe(result -> {
                    if (result != null) {
                        binding.playOverlay.setVisibility(View.VISIBLE);
                        binding.playOverlay.setOnClickListener(v -> {
                            String url = "http://www.youtube.com/watch?v=" + result.getKey();
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        });
                    }
                }, Throwable::printStackTrace);


    }
}
