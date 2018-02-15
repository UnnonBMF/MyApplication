package com.example.nontz.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nontz on 1/16/2018.
 */

public class TrackingResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private TrackingModel data;

    public TrackingResponse(boolean status, String message, TrackingModel data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public TrackingResponse() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TrackingModel getData() {
        return data;
    }

    public void setData(TrackingModel data) {
        this.data = data;
    }
}
