package com.example.design1.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.design1.R;
import com.example.design1.activity.ContestActivity;
import com.example.design1.models.CategoryDefinition;

import java.util.List;

public class RecyclerAdapterForHome extends RecyclerView.Adapter<RecyclerAdapterForHome.RecyclerViewHolder> {

    public List<CategoryDefinition> categoryList;

    public RecyclerAdapterForHome(List<CategoryDefinition> l) {
        this.categoryList = l;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(String item) {
            TextView textView = itemView.findViewById(R.id.tv_category);
            textView.setText(item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ContestActivity.class);
            Log.e("in adapter", categoryList.get(getAdapterPosition()).getCategoryId() + "");
            intent.putExtra("Category",categoryList.get(getAdapterPosition()).getCategoryName());
            view.getContext().startActivity(intent);
        }
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_home, viewGroup, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.bind(categoryList.get(i).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return  categoryList.size();
    }
}
