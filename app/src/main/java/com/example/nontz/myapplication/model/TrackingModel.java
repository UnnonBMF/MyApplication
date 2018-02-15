package com.example.nontz.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nontz on 1/9/2018.
 */

public class TrackingModel {
    @SerializedName("id")
    private int id;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("user_id")
    private int uid;

    public TrackingModel(int id, double latitude, double longitude, int uid) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.uid = uid;
    }

    public TrackingModel(double latitude, double longitude, int uid) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.uid = uid;
    }

    public TrackingModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
