package com.westudio.wecampus.data.model;

import java.util.ArrayList;

/**
 * Created by nankonami on 13-11-15.
 * This class is the model of list activity
 */
public class ActivityList {

    //Activity id
    public int id;
    //Activity title
    public String title;
    //Activity begin time
    public String begin;
    //Activity end time
    public String end;
    //Activity location
    public String location;
    //Activity category
    public String category;
    //Count of fans
    public int count_of_fans;
    //Count of views
    public int count_of_views;
    //Activity description
    public String description;
    //Activity image
    public String image;
    //Activity summary
    public String summary;

    public ActivityList() {
    }

    public ActivityList(String title, int id, String begin, String end,
                        String location, String category, int count_of_fans, int count_of_views,
                        String description, String image) {
        this.title = title;
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.location = location;
        this.category = category;
        this.count_of_fans = count_of_fans;
        this.count_of_views = count_of_views;
        this.description = description;
        this.image = image;
    }

    public static class RequestData {
        ArrayList<ActivityList> objects;

        public ArrayList<ActivityList> getObjects() {
            return objects;
        }

        public void setObjects(ArrayList<ActivityList> objects) {
            this.objects = objects;
        }
    }
}
