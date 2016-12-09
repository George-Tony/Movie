package com.movie.rahulrv.ui.movies.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movie.rahulrv.MyApplication;
import com.movie.rahulrv.R;
import com.movie.rahulrv.databinding.FragmentNowPlayingBinding;
import com.movie.rahulrv.model.Movie;
import com.movie.rahulrv.ui.movies.adapters.NowPlayingAdapter;
import com.movie.rahulrv.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Fragment displaying now playing movies.
 */

public class NowPlayingFragment extends Fragment {

    @Inject MovieViewModel viewModel;

    private FragmentNowPlayingBinding binding;
    private List<Movie> movies = new ArrayList<>();
    private CompositeSubscription subscription;
    private StaggeredGridLayoutManager layoutManager;
    private NowPlayingAdapter adapter;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((MyApplication) getActivity().getApplication()).getComponent().injectFragment(this);
        subscription = new CompositeSubscription();
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("data");
        }
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
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.nowPlayingList.setLayoutManager(layoutManager);
        adapter = new NowPlayingAdapter(movies);
        binding.nowPlayingList.setAdapter(adapter);
        initBindings();
        if (movies.isEmpty()) {
            loadMovies();
        } else {
            adapter.setItems(movies);
        }
    }

    private void initBindings() {
        Observable<Void> infiniteScrollObservable = Observable.create(subscriber -> {
            binding.nowPlayingList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    int pastVisibleItems = 0;
                    int totalItemCount = layoutManager.getItemCount();
                    int visibleItemCount = layoutManager.getChildCount();
                    int[] firstVisibleItems = null;
                    firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                    if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                        pastVisibleItems = firstVisibleItems[0];
                    }

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        subscriber.onNext(null);
                    }
                }
            });
        });

        // Bind list of posts to the RecyclerView
        viewModel.getMovies()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items) -> {
                    this.movies = items;
                    adapter.setItems(movies);
                });

        viewModel.getIsLoading().observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
            adapter.setShowLoadingMore(aBoolean);
        });

        // Trigger next page load when RecyclerView is scrolled to the bottom
        infiniteScrollObservable.subscribe(x -> loadMovies());
    }

    private void loadMovies() {
        subscription.add(viewModel.loadMovies()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d("now playing fragment", "load movies error"))
                .subscribe(movies1 -> {
                    binding.nowPlayingList.setVisibility(View.VISIBLE);
                    binding.loading.setVisibility(View.GONE);
                }));
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
