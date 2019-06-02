package com.example.design1.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class ContestTotal {
    @SerializedName("contestDefinitionDTO")
    private ContestDefinition contestDefinition;

    @SerializedName("questionDTOList")
    private List<QuestionDefinition> questionList;

    @SerializedName("userResponseDTO")
    private HashMap<Integer,String> userResponse;

    public ContestDefinition getContestDefinition() {
        return contestDefinition;
    }

    public void setContestDefinition(ContestDefinition contestDefinition) {
        this.contestDefinition = contestDefinition;
    }

    public List<QuestionDefinition> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionDefinition> questionList) {
        this.questionList = questionList;
    }

    public HashMap<Integer, String> getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(HashMap<Integer, String> userResponse) {
        this.userResponse = userResponse;
    }
}
