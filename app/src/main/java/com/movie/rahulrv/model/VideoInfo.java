package com.movie.rahulrv.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
*
*
*/
@AutoValue
public abstract class VideoInfo {

    public abstract String id();

    @SerializedName("iso_639_1")

    public abstract String iso6391();

    @SerializedName("iso_3166_1")
    public abstract String iso31661();

    @SerializedName("key")
    public abstract String key();

    public abstract String name();

    public abstract String site();

    public abstract int size();

    public abstract String type();

    public static TypeAdapter<VideoInfo> typeAdapter(Gson gson) {
        return new AutoValue_VideoInfo.GsonTypeAdapter(gson);
    }
}
