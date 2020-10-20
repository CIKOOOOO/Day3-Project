package com.andrew.day3_project.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResult {

    private int page, total_results, total_pages;

    @SerializedName("results")
    private List<Movie> movieList;

    public MovieResult() {
    }

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
