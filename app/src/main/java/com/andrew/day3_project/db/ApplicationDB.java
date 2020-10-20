package com.andrew.day3_project.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.andrew.day3_project.db.dao.MovieDao;
import com.andrew.day3_project.db.entity.MovieEntity;
import com.andrew.day3_project.utils.Constant;

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class ApplicationDB extends RoomDatabase {
    private static ApplicationDB instance = null;

    public static ApplicationDB getInstance(Context mContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(mContext, ApplicationDB.class, Constant.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract MovieDao movieDao();

}
