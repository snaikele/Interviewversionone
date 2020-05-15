package com.example.interviewversionone.model;

public class TeamMembers {
    String Mobile,Name, TeamId, MembersId;

    public TeamMembers() {
    }

    public String getMembersId() {
        return MembersId;
    }

    public void setMembersId(String membersId) {
        MembersId = membersId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String teamId) {
        TeamId = teamId;
    }
}
