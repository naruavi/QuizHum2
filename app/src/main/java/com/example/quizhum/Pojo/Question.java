package com.example.quizhum.Pojo;

import java.util.HashMap;

public class Question {
    private int questionId;
    private int  order;
    private String question;
    private String questionType;
    private HashMap<Integer,String> options;
//    [{optionId : Integer
//        optionValue: String
//    }]


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

    public HashMap<Integer, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<Integer, String> options) {
        this.options = options;
    }

    public Question(int questionId, int order, String question, String questionType, HashMap<Integer, String> options) {
        this.questionId = questionId;
        this.order = order;
        this.question = question;
        this.questionType = questionType;
        this.options = options;
    }
}
