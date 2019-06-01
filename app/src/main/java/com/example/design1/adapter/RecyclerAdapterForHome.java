package com.example.design1.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.design1.R;

import java.util.List;

public class RecyclerAdapterForHome extends RecyclerView.Adapter<RecyclerAdapterForHome.RecyclerViewHolder> {

    public List<String> l;

    public RecyclerAdapterForHome(List<String> l) {
        this.l = l;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(String item) {
            TextView textView = itemView.findViewById(R.id.tv_category);
            textView.setText(item);
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
        recyclerViewHolder.bind(l.get(i));
    }

    @Override
    public int getItemCount() {
        return  l.size();
    }
}
