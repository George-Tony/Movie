package com.movie.rahulrv.dependencyinjection.module;

import com.movie.rahulrv.MyApplication;

import dagger.Module;

/**
 *
 *
 */
@Module
public class ApplicationModule {

    private MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }
}
