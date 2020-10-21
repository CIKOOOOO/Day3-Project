package com.andrew.day3_project.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.day3_project.R;
import com.andrew.day3_project.model.Movie;
import com.andrew.day3_project.ui.detail.DetailActivity;
import com.andrew.day3_project.utils.Constant;
import com.andrew.day3_project.utils.CustomLoading;
import com.androidnetworking.AndroidNetworking;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.onItemClickListener, MainCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerAdapter adapter;
    private CustomLoading customLoading;
    private RecyclerView recyclerView;
    private MainViewModel viewModel;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVar();
    }

    private void initVar() {
        type = "";

        recyclerView = findViewById(R.id.recycler_main);

        adapter = new RecyclerAdapter(this, this);
        customLoading = new CustomLoading();

        AndroidNetworking.initialize(getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.setCallback(this);
        viewModel.getLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null && movies.size() > 0) {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                } else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
                adapter.setMovieList(movies);
                adapter.notifyDataSetChanged();
                if (customLoading != null && customLoading.getTag() != null) {
                    customLoading.dismiss();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = "";
        type = "";
        switch (item.getItemId()) {
            case R.id.menu_popular:
                title = "Popular Movie";
                viewModel.popularData();
                break;
            case R.id.menu_top_rated:
                title = "Top Rated Movie";
                viewModel.topRatedData();
                break;
            case R.id.menu_favorite:
                title = "Favorite Movie";
                type = Constant.FAVORITE_TYPE;
                viewModel.favoriteData();
                break;
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
        return true;
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        Gson gson = new Gson();
        intent.putExtra(DetailActivity.DATA, gson.toJson(movie));
        intent.putExtra(DetailActivity.TYPE, type);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onFailed() {
        Log.e(TAG, "ERROR - Android Fast Networking");
        Toast.makeText(MainActivity.this, "Error - Please try again later", Toast.LENGTH_SHORT).show();
        if (customLoading != null && customLoading.getTag() != null) {
            customLoading.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            viewModel.favoriteData();
        }
    }
}