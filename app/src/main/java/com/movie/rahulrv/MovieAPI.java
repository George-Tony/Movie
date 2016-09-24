package com.movie.rahulrv;

import com.movie.rahulrv.model.Movie;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Interface to define movie APIs
 */

public interface MovieAPI {

    @GET("/3/movie/now_playing")
    Observable<Movie> nowPlaying();
}
