package com.andrew.day3_project.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResult {
    private int id;
    @SerializedName("results")
    private List<Review> reviewList;

    public ReviewResult() {
    }

    public int getId() {
        return id;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }
}
