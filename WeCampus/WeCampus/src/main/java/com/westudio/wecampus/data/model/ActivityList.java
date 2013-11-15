package com.westudio.wecampus.data.model;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount_of_fans() {
        return count_of_fans;
    }

    public void setCount_of_fans(int count_of_fans) {
        this.count_of_fans = count_of_fans;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount_of_views() {
        return count_of_views;
    }

    public void setCount_of_views(int count_of_views) {
        this.count_of_views = count_of_views;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
