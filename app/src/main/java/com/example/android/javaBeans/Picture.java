package com.example.android.javaBeans;

import android.graphics.Bitmap;

import android.net.Uri;


import java.io.Serializable;

public class Picture implements Serializable {

    private int id;
    private int album_id;
    private String description;
    private int picture;
    private transient Bitmap imgBitmap;
    private transient Uri imgUri;
    private String imgUriStr;
    private String mediaUriStr;

    public Picture(int id,  String description, int album_id, String imgUriStr, String mediaUriStr) {
        this.id = id;
        this.album_id = album_id;
        this.description = description;
        this.imgUriStr = imgUriStr;
        this.mediaUriStr = mediaUriStr;
    }
    public Picture(String description, int album_id, String imgUriStr, String mediaUriStr) {

        this.album_id = album_id;
        this.description = description;
        this.imgUriStr = imgUriStr;
        this.mediaUriStr = mediaUriStr;
    }


    public Picture(Bitmap imgBitmap, Uri imgUri, int album_id) {
        this.imgBitmap = imgBitmap;
        this.imgUri = imgUri;
        this.imgUriStr = imgUri.toString();
        this.album_id = album_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImgBitmap() { return imgBitmap; }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public String getImgUriStr() {
        return imgUriStr;
    }

    public void setImgUriStr(String imgUriStr) {
        this.imgUriStr = imgUriStr;
    }

    public String getMediaUriStr() {
        return mediaUriStr;
    }

    public void setMediaUriStr(String mediaUriStr) {
        this.mediaUriStr = mediaUriStr;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }
}
