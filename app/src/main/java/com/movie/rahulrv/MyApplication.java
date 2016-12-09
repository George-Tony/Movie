package com.movie.rahulrv;

import android.app.Application;

import com.movie.rahulrv.dependencyinjection.ApplicationComponent;
import com.movie.rahulrv.dependencyinjection.DaggerApplicationComponent;

/**
 *
 *
 */

public class MyApplication extends Application {

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.create();
    }

    public ApplicationComponent getComponent() {
        return this.component;
    }
}
