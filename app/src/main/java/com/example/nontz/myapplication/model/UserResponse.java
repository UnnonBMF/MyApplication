package com.example.nontz.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nontz on 2/7/2018.
 */

public class UserResponse implements Parcelable {

    @SerializedName("status")
    private boolean Status;

    @SerializedName("message")
    private String Message;

    @SerializedName("data")
    private UserModel Data;

    public UserResponse() {

    }

    public UserResponse(boolean status, String message, UserModel data) {

        Status = status;
        Message = message;
        Data = data;
    }

    protected UserResponse(Parcel in) {
        Status = in.readByte() != 0;
        Message = in.readString();
    }

    public static final Creator<UserResponse> CREATOR = new Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel in) {
            return new UserResponse(in);
        }

        @Override
        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };

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

    public UserModel getData() {
        return Data;
    }

    public void setData(UserModel data) {
        Data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (Status ? 1 : 0));
        parcel.writeString(Message);
    }
}
