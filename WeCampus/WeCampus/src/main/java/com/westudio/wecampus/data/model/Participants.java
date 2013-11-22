package com.westudio.wecampus.data.model;

/**
 * Created by nankonami on 13-11-23.
 */
public class Participants {

    public int id;
    public String avatar;
    public String nickname;


    public Participants() {
    }

    public Participants(int id, String avatar, String nickname) {
        this.id = id;
        this.avatar = avatar;
        this.nickname = nickname;
    }
}
