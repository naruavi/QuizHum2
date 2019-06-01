package com.example.quizhum.models;

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


}
