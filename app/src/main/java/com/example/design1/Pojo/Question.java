package com.example.design1.Pojo;

import java.util.HashMap;

public class Question {
    private int questionId;
    private int  order;
    private String question;
    private String questionType;
    private String binaryFilePath;
    private String optionA;
    private String optionB;
    private String optionC;
    private String categoryOfQuestion;
    private String difficultyLevel;
    private String answerType;


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getBinaryFilePath() {
        return binaryFilePath;
    }

    public void setBinaryFilePath(String binaryFilePath) {
        this.binaryFilePath = binaryFilePath;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getCategoryOfQuestion() {
        return categoryOfQuestion;
    }

    public void setCategoryOfQuestion(String categoryOfQuestion) {
        this.categoryOfQuestion = categoryOfQuestion;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public Question(int questionId, int order, String question, String questionType, String binaryFilePath,
                    String optionA, String optionB, String optionC,
                    String categoryOfQuestion, String difficultyLevel, String answerType) {
        this.questionId = questionId;
        this.order = order;
        this.question = question;
        this.questionType = questionType;
        this.binaryFilePath = binaryFilePath;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.categoryOfQuestion = categoryOfQuestion;
        this.difficultyLevel = difficultyLevel;
        this.answerType = answerType;
    }
}
