package com.example.android.javaBeans;

import java.io.Serializable;
import java.util.List;

public class Album  implements Serializable {

    private int id;
    private int icon;
    private String name;
    private List<Picture> pictures;

    public Album() {
    }

    public Album(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Setters & Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public List<Picture> getPictures() { return pictures; }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }



}
