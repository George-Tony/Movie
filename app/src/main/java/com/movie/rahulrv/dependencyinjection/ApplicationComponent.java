package com.movie.rahulrv.dependencyinjection;

import com.movie.rahulrv.dependencyinjection.module.RetrofitClient;
import com.movie.rahulrv.ui.movies.activities.MovieDetailActivity;
import com.movie.rahulrv.ui.movies.fragments.NowPlayingFragment;

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

    void injectFragment(NowPlayingFragment fragment);
}