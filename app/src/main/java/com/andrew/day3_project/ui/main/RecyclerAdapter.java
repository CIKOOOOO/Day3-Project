package com.andrew.day3_project.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.day3_project.R;
import com.andrew.day3_project.model.Movie;
import com.andrew.day3_project.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private static final int EMPTY_DATA = 0;
    private Context mContext;

    public interface onItemClickListener {
        void onItemClick(Movie movie);
    }

    private onItemClickListener onItemClickListener;
    private List<Movie> movieList;

    public RecyclerAdapter(RecyclerAdapter.onItemClickListener onItemClickListener, Context mContext) {
        this.onItemClickListener = onItemClickListener;
        this.mContext = mContext;
        movieList = new ArrayList<>();
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = viewType == 1 ? R.layout.recycler_item_empty : R.layout.recycler_item;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (getItemViewType(position) == 2) {
            final Movie movie = movieList.get(position);
            String URL = Constant.IMAGE_BASE_URL + movie.getPoster_path();

            Picasso.get()
                    .load(URL)
                    .fit()
                    .into(holder.imgView);

            holder.tvTitle.setText(movie.getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(movie);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return movieList.size() == EMPTY_DATA ? 1 : 2;
    }

    @Override
    public int getItemCount() {
        return movieList.size() == EMPTY_DATA ? 1 : movieList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private ImageView imgView;
        private TextView tvTitle;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.recycler_image_poster);
            tvTitle = itemView.findViewById(R.id.recycler_tv_title);
        }
    }

}
