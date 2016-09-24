package com.movie.rahulrv.ui.movies.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.movie.rahulrv.R;
import com.movie.rahulrv.ui.movies.fragments.NowPlayingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        NowPlayingFragment dataFragment = (NowPlayingFragment) fm.findFragmentByTag("data");
        if (dataFragment == null) {
            switchFragment(new NowPlayingFragment());
        }
    }

    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, fragment, "data").commit();
    }
}
