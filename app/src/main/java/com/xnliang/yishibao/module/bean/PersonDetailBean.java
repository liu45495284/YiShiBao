package com.xnliang.yishibao.module.bean;

/**
 * Created by JackLiu on 2018-03-02.
 */

public class PersonDetailBean {
//    "score": 0,
//            "coin": "0.00",
//            "user_nickname": "15394315510",
//            "avatar": "http://ysb.appxinliang.cn/static/images/headicon.png",
//            "mobile": "15394315510",
//            "position": "员工"

    private int score = 0;
    private int coin = 0;
    private String user_nickname;
    private String avatar;
    private String mobile;
    public String position;

    public PersonDetailBean(int score, int coin, String user_nickname, String avatar, String mobile, String position) {
        this.score = score;
        this.coin = coin;
        this.user_nickname = user_nickname;
        this.avatar = avatar;
        this.mobile = mobile;
        this.position = position;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
