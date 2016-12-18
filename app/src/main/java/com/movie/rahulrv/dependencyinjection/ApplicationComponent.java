package com.movie.rahulrv.dependencyinjection;

import com.movie.rahulrv.dependencyinjection.module.RetrofitClient;
import com.movie.rahulrv.ui.movies.activities.HomeActivityJava;
import com.movie.rahulrv.ui.movies.activities.MovieDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 *
 */
@Singleton
@Component(modules = {RetrofitClient.class})
public interface ApplicationComponent {

    void inject(MovieDetailActivity activity);

    void injectFragment(HomeActivityJava activity);
}