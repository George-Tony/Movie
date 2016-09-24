package com.movie.rahulrv.viewmodel;

import com.movie.rahulrv.MovieAPI;
import com.movie.rahulrv.RetrofitClient;
import com.movie.rahulrv.model.Movie;
import com.movie.rahulrv.model.MovieWrapper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * ViewModel layer for now playing movies
 */

public class MovieViewModel {

    private int page = 1;
    private BehaviorSubject<List<Movie>> movies = BehaviorSubject.create(new ArrayList<Movie>());
    private BehaviorSubject<Boolean> isLoading = BehaviorSubject.create(false);
    private MovieAPI movieAPI;

    public MovieViewModel() {
        // TODO use Dagger
        movieAPI = RetrofitClient.getInstance()
                .create(MovieAPI.class);
    }

    public Observable<List<Movie>> loadMovies() {
        if (isLoading.getValue()) {
            return Observable.empty();
        }

        isLoading.onNext(true);

        return movieAPI
                .nowPlaying(page)
                .map(MovieWrapper::getResults)
                .doOnNext(movies1 -> {
                    movies.onNext(movies1);
                    page++;
                })
                .doOnTerminate(() -> isLoading.onNext(false));
    }

    public BehaviorSubject<Boolean> getIsLoading() {
        return isLoading;
    }

    public Observable<List<Movie>> getMovies() {
        return movies.asObservable();
    }
}
