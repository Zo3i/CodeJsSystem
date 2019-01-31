package com.jeesite.modules.js.entity.other;


import org.apache.ibatis.type.Alias;

@Alias("UserRankRes")
public class UserRankRes {
    private String name;
    private String teamName;
    private Integer rank;
    private String zoneId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
