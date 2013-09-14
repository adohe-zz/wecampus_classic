package com.westudio.wecampus.modle;

import android.database.Cursor;

import com.google.gson.Gson;

/**
 * Created by martian on 13-9-13.
 */
public class Activity {
    private int Id;

    private String Begin;

    private String End;

    private String Title;

    private String Location;

    private int Channel_Id;

    private String Organizer;

    private String OrganizerAvatar;

    private String Status;

    private String Image;

    private String CreatedAt;

    private String Description;

    private int Like;

    private boolean CanLike;

    private int Schedule;

    private boolean CanSchedule;

    private int FriendsCount;

    private int AccountId;

    public static Activity fromJson(String json) {
        return new Gson().fromJson(json, Activity.class);
    }

    public static Activity fromCursor(Cursor cursor) {
        Activity activity = new Activity();


        return activity;
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
}
