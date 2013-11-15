package com.westudio.wecampus.data.model;

/**
 * Created by nankonami on 13-11-15.
 */
public class Organization {

    //Organization id
    private int id;
    //Organization admin email
    private String admin_url;
    //Organization admin name
    private String admin_name;
    //Organization avatar
    private String avatar;
    //Count of fans
    private int count_of_fans;
    //Organization description
    private String description;
    //Organization name
    private String name;

    public Organization(int id, String admin_url, String admin_name, String avatar, String description, int count_of_fans, String name) {
        this.id = id;
        this.admin_url = admin_url;
        this.admin_name = admin_name;
        this.avatar = avatar;
        this.description = description;
        this.count_of_fans = count_of_fans;
        this.name = name;
    }

    public Organization() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdmin_url() {
        return admin_url;
    }

    public void setAdmin_url(String admin_url) {
        this.admin_url = admin_url;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCount_of_fans() {
        return count_of_fans;
    }

    public void setCount_of_fans(int count_of_fans) {
        this.count_of_fans = count_of_fans;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
