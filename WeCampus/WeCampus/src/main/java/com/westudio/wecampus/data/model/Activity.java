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
    public String ticket_service;
    public String sponsor_name;
    public String sponsor_url;
    public boolean have_sponsor;
    public int count_of_fans;
    public int organization_id;
    public int count_of_participants;
    public int count_of_views;
    public boolean have_ticket;
    public boolean can_join;
    public boolean can_like;
    public String url;
    public Organization organization;

    public static Activity fromJson(String json) {
        return new Gson().fromJson(json, Activity.class);
    }

    private static void addToCache(Activity activity) {
        CACHE.put(activity.id, activity);
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
        ac.have_sponsor = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.HAVE_SPONSOR)) > 0;
        ac.count_of_participants = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.COUNT_OF_PARTICIPANTS));
        ac.count_of_views = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.COUNT_OF_VIEWS));
        ac.have_ticket = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.HAVE_TICKETS)) > 0;
        ac.can_join = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.CAN_JOIN)) > 0;
        ac.can_like = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.CAN_LIKE)) > 0;
        ac.url = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.URL));
        addToCache(ac);
        return ac;
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
