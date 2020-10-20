package com.andrew.day3_project.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.day3_project.R;

public class SingleTextViewHolder extends RecyclerView.ViewHolder {
    public TextView tvItem;

    public SingleTextViewHolder(@NonNull View itemView) {
        super(itemView);
        tvItem = itemView.findViewById(R.id.recycler_tv_single_tv);
    }
}
