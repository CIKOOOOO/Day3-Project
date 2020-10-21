package com.andrew.day3_project.utils;

import com.andrew.day3_project.db.entity.MovieEntity;
import com.andrew.day3_project.model.Movie;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat, String inputDate) throws ParseException {
        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat = new SimpleDateFormat(inputDateFormat, java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat = new SimpleDateFormat(outputDateFormat, java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(inputDate);
        mOutputDateString = mOutputDateFormat.format(mParsedDate);
        return mOutputDateString;
    }

    public static Movie convertToMovie(MovieEntity movie) {
        Gson gson = new Gson();
        String data = gson.toJson(movie);
        return gson.fromJson(data, Movie.class);
    }

}
