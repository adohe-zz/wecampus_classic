package com.westudio.wecampus.data.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.westudio.wecampus.data.ActivityDataHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by martian on 13-9-13.
 */
public class Activity {

    private static final HashMap<Integer, Activity> CACHE = new HashMap<Integer, Activity>();

    public int id;

    public String title;
    public String image;
    public String description;
    public String category;
    public String location;
    public String begin;
    public String end;
    public boolean provide_ticket;
    public String ticket_service;
    public String sponsor_name;
    public String sponsor_url;
    public int organization_id;
    public boolean have_sponsor;
    public int count_of_fans;

    public static Activity fromJson(String json) {
        return new Gson().fromJson(json, Activity.class);
    }

    private static void addToCache(Activity activity) {
        CACHE.put(activity.getId(), activity);
    }

    private static Activity getFromCache(int id) {
        return CACHE.get(id);
    }

    public static Activity fromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.ID));
        Activity activity = getFromCache(id);
        if(activity != null) {
            return activity;
        }
        Activity ac = new Activity();
        ac.id = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.ID));
        ac.begin = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.BEGIN));
        ac.end = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.END));
        ac.title = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.TITLE));
        ac.location = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.LOCATION));
        ac.organization_id = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.ORGANIZATION_ID));
        ac.description = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.DESCRIPTION));
        ac.category = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.CATEGORY));
        ac.sponsor_name = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.SPONSOR_NAME));
        ac.image = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.IMAGE));
        ac.sponsor_url = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.SPONSOR_URL));
        ac.ticket_service = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.TICKET_SERVICE));
        ac.count_of_fans = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.COUNT_OF_FANS));
        ac.provide_ticket = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.PROVIDE_TICKET)) > 0;
        ac.have_sponsor = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.HAVE_SPONSOR)) > 0;
        addToCache(ac);
        return ac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean isProvide_ticket() {
        return provide_ticket;
    }

    public void setProvide_ticket(boolean provide_ticket) {
        this.provide_ticket = provide_ticket;
    }

    public String getTicket_service() {
        return ticket_service;
    }

    public void setTicket_service(String ticket_service) {
        this.ticket_service = ticket_service;
    }

    public String getSponsor_name() {
        return sponsor_name;
    }

    public void setSponsor_name(String sponsor_name) {
        this.sponsor_name = sponsor_name;
    }

    public String getSponsor_url() {
        return sponsor_url;
    }

    public void setSponsor_url(String sponsor_url) {
        this.sponsor_url = sponsor_url;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public boolean isHave_sponsor() {
        return have_sponsor;
    }

    public void setHave_sponsor(boolean have_sponsor) {
        this.have_sponsor = have_sponsor;
    }

    public int getCount_of_fans() {
        return count_of_fans;
    }

    public void setCount_of_fans(int count_of_fans) {
        this.count_of_fans = count_of_fans;
    }

    public static class ActivityRequestData {

        private ArrayList<Activity> objects;

        public ArrayList<Activity> getObjects() {
            return objects;
        }

        public void setObjects(ArrayList<Activity> objects) {
            this.objects = objects;
        }
    }
}
