package com.westudio.wecampus.data.model;

import android.database.Cursor;

import com.westudio.wecampus.data.OrgDataHelper;

import java.util.ArrayList;

/**
 * Created by nankonami on 13-11-15.
 */
public class Organization {

    //Organization id
    public int id;
    //Organization admin email
    public String admin_url;
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

    public Organization(int id, String admin_url, String admin_name, String avatar, String description, int count_of_fans, String name) {
        this.id = id;
        this.admin_url = admin_url;
        this.admin_name = admin_name;
        this.avatar = avatar;
        this.description = description;
        this.count_of_fans = count_of_fans;
        this.name = name;
    }

    public static Organization fromCursor(Cursor cursor) {
        Organization org = new Organization();
        org.id = cursor.getInt(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.ID));
        org.admin_name = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.ADMIN_NAME));
        org.admin_url = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.ADMIN_URL));
        org.avatar = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.AVATAR));
        org.count_of_fans = cursor.getInt(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.COUNT_OF_FANS));
        org.description = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.DESCRIPTION));
        org.name = cursor.getString(cursor.getColumnIndex(OrgDataHelper.OrganiztionDBInfo.NAME));
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdmin_url() {
        return admin_url;
    }

    public void setAdmin_url(String admin_url) {
        this.admin_url = admin_url;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCount_of_fans() {
        return count_of_fans;
    }

    public void setCount_of_fans(int count_of_fans) {
        this.count_of_fans = count_of_fans;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
