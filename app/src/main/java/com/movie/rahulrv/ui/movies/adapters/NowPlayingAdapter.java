package com.movie.rahulrv.ui.movies.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movie.rahulrv.R;
import com.movie.rahulrv.databinding.InfiniteLoaderBinding;
import com.movie.rahulrv.databinding.ViewHolderBackdropBinding;
import com.movie.rahulrv.model.Movie;
import com.movie.rahulrv.ui.movies.activities.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display now playing movies in recycler view.
 */

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder> {
    private static final int LIST_ITEM = 0;
    private static final int LOAD_MORE = 1;
    private List<Movie> movies;
    private boolean showLoadingMore = false;

    public NowPlayingAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public NowPlayingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == LOAD_MORE) {
            InfiniteLoaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.infinite_loader, parent, false);
            return new LoadingMoreHolder(binding);
        } else {
            ViewHolderBackdropBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_holder_backdrop, parent, false);
            return new NowPlayingViewHolderBackdrop(binding);
        }
    }

    @Override
    public void onBindViewHolder(NowPlayingViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case LIST_ITEM:
                holder.bindTo(movies.get(position));
                holder.itemView.setOnClickListener(v -> {
                    Context context = holder.itemView.getContext();
                    context.startActivity(MovieDetailActivity.newIntent(context, movies.get(position)));
                });
                break;
            case LOAD_MORE:
                break;
        }
    }

    @Override public int getItemCount() {
        return movies.size();
    }

    public void setShowLoadingMore(boolean isLoading) {
        showLoadingMore = isLoading;
    }

    public class NowPlayingViewHolderBackdrop extends NowPlayingViewHolder {
        ViewHolderBackdropBinding binding;

        public NowPlayingViewHolderBackdrop(ViewHolderBackdropBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Movie movie) {
            binding.setMovie(movie);
            binding.executePendingBindings();
        }
    }

    public class LoadingMoreHolder extends NowPlayingViewHolder {

        public LoadingMoreHolder(InfiniteLoaderBinding binding) {
            super(binding.getRoot());
        }

        @Override void bindTo(Movie movie) {

        }
    }

    public void setItems(List<Movie> items) {
        if (items == null) {
            return;
        }

        this.movies = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    abstract class NowPlayingViewHolder extends RecyclerView.ViewHolder {

        NowPlayingViewHolder(View view) {
            super(view);
        }

        abstract void bindTo(Movie movie);
    }

}
