package com.westudio.wecampus.data.model;

import android.database.Cursor;

import com.westudio.wecampus.data.OrgDataHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nankonami on 13-11-15.
 */
public class Organization implements Serializable{

    //Organization id
    public int id;
    //Organization admin email
    public String admin_email;
    //Organization admin name
    public String admin_name;
    //Organization avatar
    public String avatar;
    //Count of fans
    public int count_of_fans;
    //Organization description
    public String description;
    //Organization name
    public String name;
    public boolean can_follow;

    public Organization(int id, String admin_email, String admin_name,
       String avatar, String description, int count_of_fans, String name, boolean can_follow) {
        this.id = id;
        this.admin_email = admin_email;
        this.admin_name = admin_name;
        this.avatar = avatar;
        this.description = description;
        this.count_of_fans = count_of_fans;
        this.name = name;
        this.can_follow = can_follow;
    }

    public static Organization fromCursor(Cursor cursor) {
        Organization org = new Organization();
        org.id = cursor.getInt(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.ID));
        org.admin_name = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.ADMIN_NAME));
        org.admin_email = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.ADMIN_EMAIL));
        org.avatar = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.AVATAR));
        org.count_of_fans = cursor.getInt(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.COUNT_OF_FANS));
        org.description = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.DESCRIPTION));
        org.name = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.NAME));
        org.can_follow = cursor.getInt(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.CAN_FOLLOW)) > 0;
        return org;
    }

    public Organization() {
    }

    public static class OrganizationRequestData {
        private ArrayList<Organization> objects;

        public ArrayList<Organization> getObjects() {
            return objects;
        }

        public void setObjects(ArrayList<Organization> objects) {
            this.objects = objects;
        }
    }
}
