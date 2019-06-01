package com.example.design1.models;

public class ContestDefinition {

    private int contestId;
    private String contestName;
    private String createdBy;
    private String skipsAllowed;
    private String contestType;
    private Long startTimeOfContest;
    private Long endTimeOfContest;
    private int totalQuestionsInContest;
    private String categoryName;

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
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

    public String getSkipsAllowed() {
        return skipsAllowed;
    }

    public void setSkipsAllowed(String skipsAllowed) {
        this.skipsAllowed = skipsAllowed;
    }

    public String getContestType() {
        return contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public Long getStartTimeOfContest() {
        return startTimeOfContest;
    }

    public void setStartTimeOfContest(Long startTimeOfContest) {
        this.startTimeOfContest = startTimeOfContest;
    }

    public Long getEndTimeOfContest() {
        return endTimeOfContest;
    }

    public void setEndTimeOfContest(Long endTimeOfContest) {
        this.endTimeOfContest = endTimeOfContest;
    }

    public int getTotalQuestionsInContest() {
        return totalQuestionsInContest;
    }

    public void setTotalQuestionsInContest(int totalQuestionsInContest) {
        this.totalQuestionsInContest = totalQuestionsInContest;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ContestDefinition{" +
                "contestId=" + contestId +
                ", contestName='" + contestName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", skipsAllowed='" + skipsAllowed + '\'' +
                ", contestType='" + contestType + '\'' +
                ", startTimeOfContest=" + startTimeOfContest +
                ", endTimeOfContest=" + endTimeOfContest +
                ", totalQuestionsInContest=" + totalQuestionsInContest +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
