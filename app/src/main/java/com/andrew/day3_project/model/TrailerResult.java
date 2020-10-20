package com.andrew.day3_project.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResult {

    private int id;

    @SerializedName("results")
    private List<Trailer> trailerList;

    public TrailerResult() {
    }

    public int getId() {
        return id;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }
}
