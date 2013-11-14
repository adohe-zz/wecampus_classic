package com.westudio.wecampus.data.model;

import java.util.ArrayList;

/**
 * Created by nankonami on 13-10-22.
 */
public class Advertisement {

    private int id;
    private String title;
    private String url;
    private String image;

    public Advertisement() {

    }

    public Advertisement(String image, String url, String title, int id) {
        this.image = image;
        this.url = url;
        this.title = title;
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static class AdRequestDate {

        private ArrayList<Advertisement> advertisements;

        public ArrayList<Advertisement> getAdvertisements() {
            return advertisements;
        }

        public void setAdvertisements(ArrayList<Advertisement> advertisements) {
            this.advertisements = advertisements;
        }
    }

    public class AdResultData {
        public ArrayList<Advertisement> objects;
    }
}
