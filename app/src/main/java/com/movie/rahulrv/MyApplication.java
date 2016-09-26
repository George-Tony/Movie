package com.movie.rahulrv;

import android.app.Application;

import com.movie.rahulrv.dependencyinjection.ApplicationComponent;
import com.movie.rahulrv.dependencyinjection.DaggerApplicationComponent;
import com.movie.rahulrv.dependencyinjection.module.ApplicationModule;

/**
 *
 *
 */

public class MyApplication extends Application {

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return this.component;
    }
}
