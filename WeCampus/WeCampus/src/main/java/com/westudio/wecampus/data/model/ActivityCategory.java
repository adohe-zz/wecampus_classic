package com.westudio.wecampus.data.model;

import java.util.ArrayList;

/**
 * Created by nankonami on 13-11-21.
 */
public class ActivityCategory {

    public String color;
    public String name;

    public ActivityCategory() {
    }

    public ActivityCategory(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public static class CategoryRequestData {

        private ArrayList<ActivityCategory> objects;

        public ArrayList<ActivityCategory> getObjects() {
            return objects;
        }

        public void setObjects(ArrayList<ActivityCategory> objects) {
            this.objects = objects;
        }
    }
}
