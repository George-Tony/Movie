package com.movie.rahulrv.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 *
 *
 */
@AutoValue
public abstract class Dates {

    abstract String maximum();

    abstract String minimum();

    public static TypeAdapter<Dates> typeAdapter(Gson gson) {
        return new AutoValue_Dates.GsonTypeAdapter(gson);
    }
}
