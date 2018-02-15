package com.example.nontz.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nontz on 2/7/2018.
 */

public class ReportResponse {
    @SerializedName("status")
    private boolean Status;

    @SerializedName("message")
    private String Message;

    public ReportResponse() {

    }

    public ReportResponse(boolean status, String message) {
        Status = status;
        Message = message;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
