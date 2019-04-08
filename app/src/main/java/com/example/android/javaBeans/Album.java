package com.example.android.javaBeans;

import com.example.android.zoomTableau.R;

import java.io.Serializable;
import java.util.List;

public class Album  implements Serializable {

    private int id;
    private String name;
    private List<Picture> pictures;

    public Album() {}

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Picture> getPictures() { return pictures; }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }


}
