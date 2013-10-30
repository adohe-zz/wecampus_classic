package com.westudio.wecampus.data.model;

import java.util.ArrayList;

/**
 * Created by nankonami on 13-10-19.
 *
 */
public class School {

    private int id;
    private String name;
    private String icon;

    public School() {

    }

    public School(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static class SchoolRequestData {

        private ArrayList<School> objects;


        public ArrayList<School> getObjects() {
            return objects;
        }

        public void setObjects(ArrayList<School> objects) {
            this.objects = objects;
        }
    }
}
