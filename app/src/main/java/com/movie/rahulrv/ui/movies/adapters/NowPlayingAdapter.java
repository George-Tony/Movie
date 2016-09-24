package com.movie.rahulrv.ui.movies.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.movie.rahulrv.R;
import com.movie.rahulrv.databinding.ViewHolderNowPlayingBinding;
import com.movie.rahulrv.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display now playing movies in recycler view.
 */

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder> {
    private List<Movie> movies;

    public NowPlayingAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public NowPlayingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderNowPlayingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_holder_now_playing, parent, false);
        return new NowPlayingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NowPlayingViewHolder holder, int position) {
        holder.bindTo(movies.get(position));
    }

    @Override public int getItemCount() {
        return movies.size();
    }

    public class NowPlayingViewHolder extends RecyclerView.ViewHolder {
        ViewHolderNowPlayingBinding binding;

        public NowPlayingViewHolder(ViewHolderNowPlayingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Movie movie) {
            binding.setMovie(movie);
            binding.executePendingBindings();
        }
    }

    public void setItems(List<Movie> items)
    {
        if (items == null)
        {
            return;
        }

        this.movies = new ArrayList<>(items);
        notifyDataSetChanged();
    }
}
