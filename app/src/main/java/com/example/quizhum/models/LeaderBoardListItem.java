package com.example.quizhum.models;

public class LeaderBoardListItem {

    private int monthId;
    private String username;
    private int score;
    private int userRank;

    public int getMonthId() {
        return monthId;
    }

    public void setMonthId(int monthId) {
        this.monthId = monthId;
    }

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
                "monthId=" + monthId +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", userRank=" + userRank +
                '}';
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

}
