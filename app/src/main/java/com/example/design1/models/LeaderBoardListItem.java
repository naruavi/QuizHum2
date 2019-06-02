package com.example.design1.models;

public class LeaderBoardListItem {

    private String username;
    private int score;
    private int userRank;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserRank() {
        return userRank;
    }

    @Override
    public String toString() {
        return "LeaderBoardListItem{" +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", userRank=" + userRank +
                '}';
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

}
