package com.movie.rahulrv;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.movie.rahulrv.utils.RoundedCornerTransformation;
import com.squareup.picasso.Picasso;

/**
 * Custom binding adapter for data binding;
 */

public class CustomBindingAdapter {

    @BindingAdapter({"app:poster"})
    public static void loadImage(ImageView view, String path) {
        String url = String.format("%s/%s/%s", BuildConfig.IMAGE_BASE_URL, "w342", path);
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(R.color.colorAccent)
                .fit()
                .into(view);
    }

    @BindingAdapter({"app:backdrop"})
    public static void loadBackdropImage(ImageView view, String path) {
        String url = String.format("%s/%s/%s", BuildConfig.IMAGE_BASE_URL, "original", path);
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.ic_movie_24dp)
                .fit()
                .into(view);
    }

    @BindingAdapter({"app:posterRounded"})
    public static void loadBackdropImageRoundedCorner(ImageView view, String path) {
        String url = String.format("%s/%s/%s", BuildConfig.IMAGE_BASE_URL, "original", path);
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.ic_movie_24dp)
                .fit()
                .transform(new RoundedCornerTransformation())
                .into(view);
    }
}
