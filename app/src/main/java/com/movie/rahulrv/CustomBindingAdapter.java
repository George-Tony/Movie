package com.movie.rahulrv;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Custom binding adapter for data binding;
 */

public class CustomBindingAdapter {

    @BindingAdapter({"app:poster"})
    public static void loadImage(ImageView view, String path) {
        String url = String.format("%s/%s/%s", BuildConfig.IMAGE_BASE_URL, "w500", path);
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.ic_movie_24dp)
                .fit()
                .into(view);
    }
}
