package com.movie.rahulrv.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 *
 */
@AutoValue
public abstract class Movie implements Parcelable {

    @SerializedName("poster_path")
    public abstract String posterPath();

    public abstract boolean adult();

    public abstract String overview();

    @SerializedName("release_date")
    public abstract String releaseDate();

    @SerializedName("genre_ids")
    public abstract List<Integer> genreIds();

    public abstract int id();

    @SerializedName("original_title")
    public abstract String originalTitle();

    @SerializedName("original_language")
    public abstract String originalLanguage();

    public abstract String title();

    @SerializedName("backdrop_path")
    public abstract String backdropPath();

    public abstract float popularity();

    @SerializedName("vote_count")
    public abstract int voteCount();

    public abstract boolean video();

    @SerializedName("vote_average")
    public abstract float voteAverage();

    public static TypeAdapter<Movie> typeAdapter(Gson gson) {
        return new AutoValue_Movie.GsonTypeAdapter(gson);
    }
}
