package com.movie.rahulrv.viewmodel;

import com.movie.rahulrv.MovieAPI;
import com.movie.rahulrv.model.Movie;
import com.movie.rahulrv.model.MovieWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    @Inject
    public MovieViewModel(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
    }

    public Observable<List<Movie>> loadMovies() {
        if (isLoading.getValue()) {
            return Observable.empty();
        }

        isLoading.onNext(true);

        return movieAPI
                .nowPlaying(page)
                .map(MovieWrapper::results)
                .doOnNext(movies1 -> {
                    List<Movie> fullList = new ArrayList<>(movies.getValue());
                    fullList.addAll(movies1);
                    movies.onNext(fullList);
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
