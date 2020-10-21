package com.andrew.day3_project.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.andrew.day3_project.db.entity.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insertFavoriteMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM MovieEntity")
    LiveData<List<MovieEntity>> selectMovie();

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    MovieEntity selectSpecificMovie(int id);

    @Delete
    void deleteMovie(MovieEntity movieEntity);
}
