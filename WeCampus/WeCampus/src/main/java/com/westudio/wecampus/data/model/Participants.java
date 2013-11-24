package com.westudio.wecampus.data.model;

import java.util.ArrayList;

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

    public static class ParticipantsRequestData {

        private ArrayList<Participants> objects;

        public ArrayList<Participants> getObjects() {
            return objects;
        }

        public void setObjects(ArrayList<Participants> objects) {
            this.objects = objects;
        }
    }
}
