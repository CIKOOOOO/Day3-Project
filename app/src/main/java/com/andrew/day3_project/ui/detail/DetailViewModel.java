package com.andrew.day3_project.ui.detail;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.andrew.day3_project.db.ApplicationDB;
import com.andrew.day3_project.db.entity.MovieEntity;
import com.andrew.day3_project.model.Movie;
import com.andrew.day3_project.model.ReviewResult;
import com.andrew.day3_project.model.TrailerResult;
import com.andrew.day3_project.utils.Constant;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private DetailCallback callback;
    private Context mContext;

    private MutableLiveData<List<Object>> trailerLiveData;
    private MutableLiveData<List<Object>> reviewLiveData;

    public void setMovieID(int movieID) {
        getTrailer(movieID);
        getReview(movieID);
    }

    public void setCallback(DetailCallback callback) {
        this.callback = callback;
    }

    public DetailViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
        trailerLiveData = new MutableLiveData<>();
        reviewLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Object>> getReviewLiveData() {
        return reviewLiveData;
    }

    public MutableLiveData<List<Object>> getTrailerLiveData() {
        return trailerLiveData;
    }

    public void deleteFavoriteMovie(Movie movie) {
        MovieEntity movieEntity = convertToMovieEntity(movie);
        ApplicationDB.getInstance(mContext).movieDao().deleteMovie(movieEntity);
        getFavoriteMovie(movie);
    }

    public void favoriteAMovie(Movie movie) {
        MovieEntity movieEntity = convertToMovieEntity(movie);
        ApplicationDB.getInstance(mContext).movieDao().insertFavoriteMovie(movieEntity);
        getFavoriteMovie(movie);
    }

    public void getFavoriteMovie(Movie movie) {
        MovieEntity movieEntity = ApplicationDB.getInstance(mContext).movieDao().selectSpecificMovie(movie.getId());
        boolean isFavorite = movieEntity != null && movieEntity.getTitle() != null;
        callback.getFavoriteStatus(isFavorite);
    }

    private void getTrailer(int movieID) {
        AndroidNetworking.get(Constant.BASE_URL + "/{pageNumber}/movie/{movieID}/videos")
                .addPathParameter("pageNumber", "3")
                .addPathParameter("movieID", String.valueOf(movieID))
                .addQueryParameter("api_key", Constant.API_KEY)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        TrailerResult trailerResult = gson.fromJson(response.toString(), TrailerResult.class);
                        List<Object> objects = new ArrayList<Object>(trailerResult.getTrailerList());
                        trailerLiveData.postValue(objects);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    private void getReview(int movieID){
        AndroidNetworking.get(Constant.BASE_URL + "/{pageNumber}/movie/{movieID}/reviews")
                .addPathParameter("pageNumber", "3")
                .addPathParameter("movieID", String.valueOf(movieID))
                .addQueryParameter("api_key", Constant.API_KEY)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ReviewResult trailerResult = gson.fromJson(response.toString(), ReviewResult.class);
                        List<Object> objects = new ArrayList<Object>(trailerResult.getReviewList());
                        reviewLiveData.postValue(objects);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    private MovieEntity convertToMovieEntity(Movie movie) {
        Gson gson = new Gson();
        String data = gson.toJson(movie);
        return gson.fromJson(data, MovieEntity.class);
    }
}
