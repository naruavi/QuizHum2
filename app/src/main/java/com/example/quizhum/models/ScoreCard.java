package com.example.quizhum.models;

public class ScoreCard {

    private int totalScore;
    private int correctAnswers;

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    @Override
    public String toString() {
        return "ScoreCard{" +
                "totalScore=" + totalScore +
                ", correctAnswers=" + correctAnswers +
                '}';
    }

}
