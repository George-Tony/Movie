package com.movie.rahulrv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */

public class VideoWrapper {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<VideoInfo> results = new ArrayList<>();

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The results
     */
    public List<VideoInfo> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<VideoInfo> results) {
        this.results = results;
    }
}
