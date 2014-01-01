package com.westudio.wecampus.data.model;

import android.database.Cursor;

import com.westudio.wecampus.data.UserDataHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jam on 13-9-19.
 */
public class User implements Serializable {
    public int id;
    public String name;
    public String birthday;
    public String avatar;
    public String contact_email;
    public int count_of_fans;
    public int count_of_followers;
    public int count_of_follow_organizations;
    public int count_of_follow_activities;
    public int count_of_join_activities;
    public String email;
    public String emotion;
    public String nickname;
    public int school_id;
    public String school_name;
    public String gender;
    public String phone;
    public String stage;
    public String words;
    public String password;
    public String token;
    public School school;
    public boolean can_follow = true;
    public String like_activity;
    public String attend_activity;
    public String like_organization;

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
        user.count_of_join_activities = cursor.getInt(cursor.getColumnIndex(UserDataHelper.UserDBInfo.CONT_OF_JOIN_ACTIVITIES));
        user.count_of_followers = cursor.getInt(cursor.getColumnIndex(UserDataHelper.UserDBInfo.CONT_OF_FOLLOWER));
        user.email = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.EMAIL));
        user.emotion = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.EMOTION));
        user.nickname = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.NICKNAME));
        user.school_id = cursor.getInt(cursor.getColumnIndex(UserDataHelper.UserDBInfo.SCHOOL_ID));
        user.school_name = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.SCHOOL_NAME));
        user.gender = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.GENDER));
        user.phone = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.PHONE));
        user.stage = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.STAGE));
        user.can_follow = cursor.getInt(cursor.getColumnIndex(UserDataHelper.UserDBInfo.CAN_FOLLOW)) > 0;
        user.like_activity = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.LIKE_ACTIVITY));
        user.attend_activity = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.ATTEND_ACTIVITY));
        user.like_organization = cursor.getString(cursor.getColumnIndex(UserDataHelper.UserDBInfo.LIKE_ORGANIZATION));
        return user;
    }

    public class UserListData {
        public ArrayList<User> objects;

        public ArrayList<User> getObjects() {
            return objects;
        }

        public void setObjects(ArrayList<User> objects) {
            this.objects = objects;
        }
    }
}
