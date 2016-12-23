package com.movie.rahulrv.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 *
 *
 */
@AutoValue
public abstract class VideoWrapper {

    public abstract int id();

    public abstract List<VideoInfo> results();

    public static TypeAdapter<VideoWrapper> typeAdapter(Gson gson) {
        return new AutoValue_VideoWrapper.GsonTypeAdapter(gson);
    }
}
