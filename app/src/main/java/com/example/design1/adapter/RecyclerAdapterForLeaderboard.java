package com.example.design1.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.design1.R;
import com.example.design1.models.LeaderBoardListItem;

import java.util.List;

public class RecyclerAdapterForLeaderboard extends RecyclerView.Adapter<RecyclerAdapterForLeaderboard.RecyclerViewHolder> {

    List<LeaderBoardListItem> leaderBoardItemList;

    public RecyclerAdapterForLeaderboard(List<LeaderBoardListItem> leaderBoardItemList) {
        this.leaderBoardItemList = leaderBoardItemList;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(LeaderBoardListItem leaderBoardListItem) {
            TextView username = itemView.findViewById(R.id.leaderBoard_textView_name);
            TextView userRank = itemView.findViewById(R.id.leaderBoard_textView_rank);
            TextView userScore = itemView.findViewById(R.id.leaderBoard_textView_score);

            username.setText(leaderBoardListItem.getUsername());
            userRank.setText(""+leaderBoardListItem.getUserRank());
            userScore.setText(""+leaderBoardListItem.getScore());
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.bind(leaderBoardItemList.get(i));
    }

    @Override
    public int getItemCount() {
        return  leaderBoardItemList.size();
    }
}
