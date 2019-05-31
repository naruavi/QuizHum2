package com.example.quizhum.Pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ContestObject {
        HashMap<Integer,String> response;
        DetailsOfContest detailsOfContest;
        List<Question> question;


    public ContestObject(HashMap<Integer, String> response, DetailsOfContest detailsOfContest, List<Question> question) {
        this.response = response;
        this.detailsOfContest = detailsOfContest;
        this.question = question;
    }
}
