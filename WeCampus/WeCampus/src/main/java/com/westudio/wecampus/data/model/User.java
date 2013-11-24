package com.westudio.wecampus.data.model;

import android.database.Cursor;

import com.westudio.wecampus.data.UserDataHelper;

/**
 * Created by jam on 13-9-19.
 */
public class User {
    public int id;
    public String name;
    public String birthday;
    public String avatar;
    public String contact_email;
    public int count_of_fans;
    public int count_of_follower;
    public int count_of_follow_organizations;
    public int count_of_follow_activities;
    public String email;
    public String emotion;
    public String nickname;
    public int school_id;
    public String gender;
    public String phone;
    public String stage;
    public String words;
    public String password;
    public String token;
    public School school;

    public static User fromCursor(Cursor cursor) {
        User user = new User();
        user.id = cursor.getInt(cursor.getColumnIndex(UserDataHelper.UserDBInfo.ID));
        user.words = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.WORDS));
        user.name = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.NAME));
        user.avatar = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.AVATAR));
        user.birthday = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.BIRTHDAY));
        user.contact_email = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.CONTANT_EMAIL));
        user.count_of_fans = cursor.getInt(cursor.getColumnIndex(UserDataHelper.UserDBInfo.CONT_OF_FANS));
        user.count_of_follow_activities = cursor.getInt(cursor.getColumnIndex(
                UserDataHelper.UserDBInfo.CONT_OF_FOLLOW_ACTIVITIES));
        user.count_of_follow_organizations = cursor.getInt(cursor.getColumnIndex(
                UserDataHelper.UserDBInfo.CONT_OF_FOLLOW_ORGANIZATIONS));
        user.count_of_follower = cursor.getInt(cursor.getColumnIndex(UserDataHelper.UserDBInfo.CONT_OF_FOLLOWER));
        user.email = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.EMAIL));
        user.emotion = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.EMOTION));
        user.nickname = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.NICKNAME));
        user.school_id = cursor.getInt(cursor.getColumnIndex(UserDataHelper.UserDBInfo.SCHOOL_ID));
        user.gender = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.GENDER));
        user.phone = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.PHONE));
        user.stage = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.STAGE));
        return user;
    }
}
