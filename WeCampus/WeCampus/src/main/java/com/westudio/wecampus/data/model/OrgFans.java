package com.westudio.wecampus.data.model;

import java.util.ArrayList;

/**
 * Created by nankonami on 13-12-8.
 */
public class OrgFans {
    public int id;
    public String avatar;
    public String nickname;

    public OrgFans() {
    }

    public OrgFans(int id, String avatar, String nickname) {
        this.id = id;
        this.avatar = avatar;
        this.nickname = nickname;
    }

    public static class RequestData {
        private ArrayList<OrgFans> objects;

        public ArrayList<OrgFans> getObjects() {
            return objects;
        }

        public void setObjects(ArrayList<OrgFans> objects) {
            this.objects = objects;
        }
    }
}
