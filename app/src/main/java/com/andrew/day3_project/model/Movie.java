package com.andrew.day3_project.model;

public class Movie {
    private double popularity, vote_average;
    private int vote_count, id;
    private boolean video, adult;
    private String poster_path, backdrop_path, original_language, original_title, title, overview, release_date;

    public Movie() {
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
}
