package com.movie.rahulrv.ui.movies.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movie.rahulrv.R;
import com.movie.rahulrv.databinding.FragmentNowPlayingBinding;
import com.movie.rahulrv.model.Movie;
import com.movie.rahulrv.ui.movies.adapters.NowPlayingAdapter;
import com.movie.rahulrv.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Fragment displaying now playing movies.
 */

public class NowPlayingFragment extends Fragment {

    private FragmentNowPlayingBinding binding;
    private List<Movie> movies = Collections.emptyList();
    private CompositeSubscription subscription;

    private MovieViewModel viewModel;
    private LinearLayoutManager linearLayoutManager;
    private NowPlayingAdapter adapter;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        subscription = new CompositeSubscription();
        viewModel = new MovieViewModel();
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater,
                                                 @Nullable ViewGroup container,
                                                 @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_now_playing, container, false);
        return binding.getRoot();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("data", new ArrayList<>(movies));
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.nowPlayingList.setLayoutManager(linearLayoutManager);
        if (movies.isEmpty()) {
            subscription.add(viewModel.loadMovies()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movies1 -> {
                        this.movies = movies1;
                        adapter = new NowPlayingAdapter(movies);
                        binding.nowPlayingList.setAdapter(adapter);
                    }));
        } else {
            binding.nowPlayingList.setAdapter(new NowPlayingAdapter(movies));
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        subscription.clear();
    }

    public void setData(List<Movie> data) {
        this.movies = data;
    }

    public List<Movie> getData() {
        return movies;
    }
}
