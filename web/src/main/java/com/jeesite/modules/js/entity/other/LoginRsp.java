package com.jeesite.modules.js.entity.other;


import com.jeesite.modules.js.entity.JsUser;

public class LoginRsp {

    private String token;
 	private String name;		// 不能为空
	private String mobile;		    // 电话
	private Integer rank;		// rank
	private String teamId;		// team_id
    private String zoneId;

    public LoginRsp() {
        super();
    }

    public LoginRsp(String token, JsUser s) {
        super();
        this.token = token;
        this.mobile = s.getMobile();
        this.name = s.getName();
        this.rank = s.getRank();
        this.teamId = s.getTeamId();
        this.zoneId = s.getZoneId();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
