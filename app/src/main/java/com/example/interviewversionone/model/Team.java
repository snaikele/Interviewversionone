package com.example.interviewversionone.model;

public class Team {
    String TeamName,TeamId,TeamImgUrl;

    public Team() {
    }

    public String getTeamImgUrl() {
        return TeamImgUrl;
    }

    public void setTeamImgUrl(String teamImgUrl) {
        TeamImgUrl = teamImgUrl;
    }

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String teamId) {
        TeamId = teamId;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }
}
