package com.andrew.day3_project.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.day3_project.R;
import com.andrew.day3_project.adapter.SingleTextAdapter;
import com.andrew.day3_project.delegate.OnItemClickListener;
import com.andrew.day3_project.model.Movie;
import com.andrew.day3_project.model.Trailer;
import com.andrew.day3_project.utils.Constant;
import com.andrew.day3_project.utils.Utils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, DetailCallback, OnItemClickListener {

    public static final String DATA = "data";
    public static final String TYPE = "type";

    private DetailViewModel viewModel;
    private Movie movie;
    private ImageButton imgBtnFavorite;
    private SingleTextAdapter trailerAdapter, reviewAdapter;

    private boolean isFavorite;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initVar();
    }

    private void initVar() {
        isFavorite = false;
        type = "";

        RecyclerView recycler_trailer = findViewById(R.id.recycler_trailer_detail);
        RecyclerView recycler_review = findViewById(R.id.recycler_review_detail);

        imgBtnFavorite = findViewById(R.id.img_btn_favorite_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        trailerAdapter = new SingleTextAdapter(this);
        reviewAdapter = new SingleTextAdapter(this);
        trailerAdapter.setClickListener(this);

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        viewModel.setCallback(this);

        recycler_trailer.setLayoutManager(new LinearLayoutManager(this));
        recycler_review.setLayoutManager(new LinearLayoutManager(this));
        recycler_trailer.setAdapter(trailerAdapter);
        recycler_review.setAdapter(reviewAdapter);

        Intent intent = getIntent();
        if (intent.hasExtra(DATA)) {
            String movieGson = intent.getStringExtra(DATA);
            Gson gson = new Gson();
            movie = gson.fromJson(movieGson, Movie.class);
            if (movie != null)
                setData();
        }

        if (intent.hasExtra(TYPE)) {
            type = intent.getStringExtra(TYPE);
        }

        imgBtnFavorite.setOnClickListener(this);
    }

    private void setData() {
        TextView tvTitle = findViewById(R.id.tv_title_detail);
        ImageView imgPoster = findViewById(R.id.img_poster_detail);
        TextView tvDate = findViewById(R.id.tv_date_detail);
        TextView tvRating = findViewById(R.id.tv_rating_detail);
        TextView tvDesc = findViewById(R.id.tv_description_detail);

        tvTitle.setText(movie.getTitle());

        Picasso.get()
                .load(Constant.IMAGE_BASE_URL + movie.getPoster_path())
                .fit()
                .into(imgPoster);

        viewModel.getFavoriteMovie(movie);
        viewModel.setMovieID(movie.getId());

        viewModel.getTrailerLiveData().observe(this, new Observer<List<Object>>() {
            @Override
            public void onChanged(List<Object> trailers) {
                trailerAdapter.setObjectList(trailers);
                trailerAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getReviewLiveData().observe(this, new Observer<List<Object>>() {
            @Override
            public void onChanged(List<Object> objects) {
                reviewAdapter.setObjectList(objects);
                reviewAdapter.notifyDataSetChanged();
            }
        });

        try {
            String date = Utils.formatDateFromDateString(Constant.DATE_FORMAT_1, Constant.DATE_FORMAT_2, movie.getRelease_date());
            tvDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String popularity = movie.getVote_average() + "/10";

        tvRating.setText(popularity);
        tvDesc.setText(movie.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_btn_favorite_detail) {
            if (isFavorite) {
                viewModel.deleteFavoriteMovie(movie);
            } else {
                viewModel.favoriteAMovie(movie);
            }
        }
    }

    @Override
    public void getFavoriteStatus(boolean isFavorite) {
        this.isFavorite = isFavorite;
        int drawable = isFavorite ? R.drawable.ic_favorite_red : R.drawable.ic_favorite_border_red;
        imgBtnFavorite.setBackgroundResource(drawable);
    }

    @Override
    public void onItemClick(Object o) {
        if (o instanceof Trailer) {
            Trailer trailer = (Trailer) o;
            String url = Constant.TRAILER_YOUTUBE_BASE_URL + trailer.getKey();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (type.equals(Constant.FAVORITE_TYPE)) {
            Log.e("asd", "favorite");
            setResult(100);
            finish();
        } else
            super.onBackPressed();
    }
}