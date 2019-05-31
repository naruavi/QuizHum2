package com.example.quizhum.Pojo;

import java.util.Date;

public class DetailsOfContest {
    Integer contestId;
    String contestName;
    String createdBy;
    Date createdAt;
    Integer numberOfskipsAllowed;
    String contestType;
    Date timeStartOfContest;
    Date timeEndOfContest;
    Integer totalQuestions;
    String categoryOfContest;

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getNumberOfskipsAllowed() {
        return numberOfskipsAllowed;
    }

    public void setNumberOfskipsAllowed(Integer numberOfskipsAllowed) {
        this.numberOfskipsAllowed = numberOfskipsAllowed;
    }

    public String getContestType() {
        return contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public Date getTimeStartOfContest() {
        return timeStartOfContest;
    }

    public void setTimeStartOfContest(Date timeStartOfContest) {
        this.timeStartOfContest = timeStartOfContest;
    }

    public Date getTimeEndOfContest() {
        return timeEndOfContest;
    }

    public void setTimeEndOfContest(Date timeEndOfContest) {
        this.timeEndOfContest = timeEndOfContest;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getCategoryOfContest() {
        return categoryOfContest;
    }

    public void setCategoryOfContest(String categoryOfContest) {
        this.categoryOfContest = categoryOfContest;
    }

    public DetailsOfContest(Integer contestId, String contestName,
                            Integer numberOfskipsAllowed, Integer totalQuestions) {
        this.contestId = contestId;
        this.contestName = contestName;
        this.numberOfskipsAllowed = numberOfskipsAllowed;
        this.totalQuestions = totalQuestions;
    }
}
