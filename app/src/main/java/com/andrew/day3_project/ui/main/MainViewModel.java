package com.andrew.day3_project.ui.main;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andrew.day3_project.db.ApplicationDB;
import com.andrew.day3_project.db.entity.MovieEntity;
import com.andrew.day3_project.model.Movie;
import com.andrew.day3_project.model.MovieResult;
import com.andrew.day3_project.utils.Constant;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<Movie>> liveData;
    private MainCallback callback;
    private Context mContext;

    public void setCallback(MainCallback callback) {
        this.callback = callback;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
        liveData = new MutableLiveData<>();
        getData(Constant.POPULAR_TYPE);
    }

    public LiveData<List<Movie>> getLiveData() {
        return liveData;
    }

    public void popularData() {
        getData(Constant.POPULAR_TYPE);
    }

    public void topRatedData() {
        getData(Constant.TOP_RATED_TYPE);
    }

    public void favoriteData() {
        List<MovieEntity> movieEntityList = ApplicationDB.getInstance(mContext).movieDao().selectMovie();
        List<Movie> movieList = new ArrayList<>();
        for (MovieEntity movieEntity : movieEntityList) {
            Movie movie = convertToMovieEntity(movieEntity);
            movieList.add(movie);
        }
        liveData.postValue(movieList);
    }

    private void getData(String type) {
        AndroidNetworking.get(Constant.BASE_URL + "/{pageNumber}/movie/{type}")
                .addPathParameter("pageNumber", "3")
                .addPathParameter("type", type)
                .addQueryParameter("api_key", Constant.API_KEY)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        MovieResult movieResult = gson.fromJson(response.toString(), MovieResult.class);
                        liveData.postValue(movieResult.getMovieList());
                    }

                    @Override
                    public void onError(ANError anError) {
                        callback.onFailed();
                    }
                });
    }

    private Movie convertToMovieEntity(MovieEntity movie) {
        Gson gson = new Gson();
        String data = gson.toJson(movie);
        return gson.fromJson(data, Movie.class);
    }
}
