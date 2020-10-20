package com.andrew.day3_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.day3_project.R;
import com.andrew.day3_project.delegate.OnItemClickListener;
import com.andrew.day3_project.model.Review;
import com.andrew.day3_project.model.Trailer;
import com.andrew.day3_project.viewholder.SingleTextViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SingleTextAdapter extends RecyclerView.Adapter {

    private List<Object> objectList;
    private Context mContext;
    private OnItemClickListener clickListener;

    public SingleTextAdapter(Context mContext) {
        this.mContext = mContext;
        objectList = new ArrayList<>();
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_single_tv, parent, false);
        return new SingleTextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Object o = objectList.get(position);

        SingleTextViewHolder viewHolder = (SingleTextViewHolder) holder;

        String data = "";

        if (o instanceof Trailer) {
            Trailer trailer = (Trailer) o;
            data = trailer.getName();
            viewHolder.tvItem.setTextColor(mContext.getResources().getColor(R.color.palette_cyan));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(o);
                }
            });
        } else if (o instanceof Review) {
            Review review = (Review) o;
            data = review.getContent() + "\n-" + review.getAuthor();
            viewHolder.tvItem.setTextColor(mContext.getResources().getColor(android.R.color.black));
        }

        viewHolder.tvItem.setText(data);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }
}
