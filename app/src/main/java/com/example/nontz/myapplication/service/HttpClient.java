package com.example.nontz.myapplication.service;

import com.example.nontz.myapplication.model.ReportResponse;
import com.example.nontz.myapplication.model.TrackingResponse;
import com.example.nontz.myapplication.model.UserResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by nontz on 1/16/2018.
 */

public interface HttpClient {
    @FormUrlEncoded
    @POST("/ProjectN/public/api/tracking")
    Call<TrackingResponse> resendTracking(@Field("latitude") double latitude, @Field("longitude") double longitude, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("/ProjectN/public/api/account")
    Call<UserResponse> resendLogin(@Field("username") String username, @Field("password") String password);


    @FormUrlEncoded
    @POST("/ProjectN/public/api/report")
    Call<ReportResponse> resendReport(@Field("message") String message, @Field("user_id") int user_id);

    @GET("/")
    Call<String> getTracking();
}
