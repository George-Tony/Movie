package com.movie.rahulrv.ui.movies.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.movie.rahulrv.R;
import com.movie.rahulrv.databinding.ActivityMainBinding;
import com.movie.rahulrv.ui.movies.fragments.NowPlayingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        FragmentManager fm = getSupportFragmentManager();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.now_playing);
        }
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
