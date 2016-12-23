package com.movie.rahulrv.model;

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
public abstract class MovieWrapper {

    @SerializedName("page")
    public abstract int page();

    @SerializedName("results")
    public abstract List<Movie> results();

    @SerializedName("dates")
    public abstract Dates dates();

    @SerializedName("total_pages")
    abstract int totalPages();

    @SerializedName("total_results")
    abstract int totalResults();

    public static TypeAdapter<MovieWrapper> typeAdapter(Gson gson) {
        return new AutoValue_MovieWrapper.GsonTypeAdapter(gson);
    }
}
