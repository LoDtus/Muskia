package com.example.musika;

import android.app.Application;

public class GlobalClass extends Application {
    public static String nickname;
    public static String playID;

    public GlobalClass(String nickname, String playID) {
        this.nickname = nickname;
        this.playID = playID;
    }
    public GlobalClass() {

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPlayID() {
        return playID;
    }

    public void setPlayID(String playID) {
        this.playID = playID;
    }
}
