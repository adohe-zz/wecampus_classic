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

    public int Id;

    public String Begin;
    public String End;
    public String Title;
    public String Location;
    public int Channel_Id;
    public String Organizer;
    public String OrganizerAvatar;
    public String Status;
    public String Image;
    public String CreatedAt;
    public String Description;
    public int Like;
    public boolean CanLike;
    public int Schedule;
    public boolean CanSchedule;
    public int FriendsCount;
    public int AccountId;

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
        ac.Id = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.ID));
        ac.Begin = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.BEGIN));
        ac.End = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.END));
        ac.Title = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.TITLE));
        ac.Location = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.LOCATION));
        ac.Channel_Id = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.CHANNEL_ID));
        ac.Organizer = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.ORGANIZER));
        ac.OrganizerAvatar = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.ORGANIZER_AVATAR));
        ac.Status = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.STATUS));
        ac.Image = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.IMAGE));
        ac.CreatedAt = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.CRTEATE_AT));
        ac.Description = cursor.getString(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.DESCRIPTION));
        ac.Like = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.LIKE));
        ac.CanLike = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.CAN_LIKE)) > 0;
        ac.Schedule = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.SCHEDULE));
        ac.CanSchedule = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.CAN_SCHEDULE)) > 0;
        ac.FriendsCount = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.FRIEND_COUNT));
        ac.AccountId = cursor.getInt(cursor.getColumnIndex(ActivityDataHelper.ActivityDBInfo.ACCOUNT_ID));
        addToCache(ac);
        return ac;
    }

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getLocation() {
        return Location;
    }

    public int getChannel_Id() {
        return Channel_Id;
    }

    public String getOrganizer() {
        return Organizer;
    }

    public String getOrganizerAvatar() {
        return OrganizerAvatar;
    }

    public String getStatus() {
        return Status;
    }

    public String getImage() {
        return Image;
    }

    public String getDescription() {
        return Description;
    }

    public int getLike() {
        return Like;
    }

    public boolean isCanLike() {
        return CanLike;
    }

    public int getSchedule() {
        return Schedule;
    }

    public boolean isCanSchedule() {
        return CanSchedule;
    }

    public int getFriendsCount() {
        return FriendsCount;
    }

    public int getAccountId() {
        return AccountId;
    }

    public String getBegin() {
        return Begin;
    }

    public String getEnd() {
        return End;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public static class ActivityRequestData {
        private int NextPager;
        private ArrayList<Activity> Activities;


        public int getNextPager() {
            return NextPager;
        }

        public void setNextPager(int nextPager) {
            NextPager = nextPager;
        }

        public ArrayList<Activity> getActivities() {
            return Activities;
        }

        public void setActivities(ArrayList<Activity> activities) {
            Activities = activities;
        }
    }
}
