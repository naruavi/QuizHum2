package com.example.design1.models;

public class NewResponse {

    private int questionId;
    private int contestId;
    private String response;
    private int userId;
    private String username;

    @Override
    public String toString() {
        return "NewResponse{" +
                "questionId=" + questionId +
                ", contestId=" + contestId +
                ", response='" + response + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
