package com.example.nontz.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nontz on 2/1/2018.
 */

public class UserModel implements Parcelable {

    @SerializedName("user_id")
    private int UserID;

    @SerializedName("username")
    private String Username;

    @SerializedName("name")
    private String Name;

    @SerializedName("age")
    private int Age;

    @SerializedName("tel")
    private String Tel;

    public UserModel() {
    }

    public UserModel(int userID, String username, String name, int age, String tel) {
        UserID = userID;
        Username = username;
        Name = name;
        Age = age;
        Tel = tel;
    }


    protected UserModel(Parcel in) {
        UserID = in.readInt();
        Username = in.readString();
        Name = in.readString();
        Age = in.readInt();
        Tel = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(UserID);
        parcel.writeString(Username);
        parcel.writeString(Name);
        parcel.writeInt(Age);
        parcel.writeString(Tel);
    }
}
