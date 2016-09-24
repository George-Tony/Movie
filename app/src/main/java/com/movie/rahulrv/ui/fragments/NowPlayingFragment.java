package com.movie.rahulrv.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movie.rahulrv.MovieAPI;
import com.movie.rahulrv.R;
import com.movie.rahulrv.RetrofitClient;
import com.movie.rahulrv.databinding.FragmentNowPlayingBinding;
import com.movie.rahulrv.model.Movie;
import com.movie.rahulrv.ui.adapters.NowPlayingAdapter;

import java.util.Collections;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Fragment displaying now playing movies.
 */

public class NowPlayingFragment extends Fragment {

    private FragmentNowPlayingBinding binding;
    private List<Movie> movies = Collections.emptyList();
    private MovieAPI movieAPI;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieAPI = RetrofitClient.getInstance().create(MovieAPI.class);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater,
                                                 @Nullable ViewGroup container,
                                                 @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_now_playing, container, false);
        return binding.getRoot();
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.nowPlayingList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        movieAPI.nowPlaying()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieWrapper -> {
                    this.movies = movieWrapper.getResults();
                    binding.nowPlayingList.setAdapter(new NowPlayingAdapter(movies));
                });
    }
}
