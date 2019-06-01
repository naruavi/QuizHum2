package com.example.design1.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.design1.R;
import com.example.design1.models.ContestDefinition;

import java.util.List;

public class RecyclerAdapterForContest extends RecyclerView.Adapter<RecyclerAdapterForContest.RecyclerViewHolder> {
    public List<ContestDefinition> contestList;

    public RecyclerAdapterForContest(List<ContestDefinition> l1) {
        this.contestList = l1;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(ContestDefinition item) {
            TextView contestName = itemView.findViewById(R.id.tv_category);
            TextView noOfQuestions = itemView.findViewById(R.id.no_of_questions);
            TextView noOfSkips = itemView.findViewById(R.id.no_of_skips);
            contestName.setText(item.getContestName());
            noOfQuestions.setText("No of Questions: "+item.getTotalQuestionsInContest() +" ");
            noOfSkips.setText("No of Skips: "+item.getSkipsAllowed()+" ");
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup , int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_contests,viewGroup,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterForContest.RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.bind(contestList.get(i));

    }

    @Override
    public int getItemCount() {
        return contestList.size();
    }
}