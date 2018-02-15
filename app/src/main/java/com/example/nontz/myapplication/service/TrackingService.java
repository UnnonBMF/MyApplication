package com.example.nontz.myapplication.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.nontz.myapplication.Config;
import com.example.nontz.myapplication.model.TrackingResponse;
import com.example.nontz.myapplication.model.UserModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nontz on 1/9/2018.
 */

public class TrackingService extends Service implements LocationListener {

    private LocationManager locationManager;
    // final parameters
    private final int MY_PERMISSIONS_REQUEST = 10001;
    private final String TAG = "TrackingService";
    private final int interval = 1 * 30 * 1000;
    // location listener
    private LocationListener locationListener = this;
    // user model
    private UserModel user;
    // context
    private Context context = this;

    // google api
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // retrofit
    private Gson gson;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.Host)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mLocationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(interval);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);


        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                double Longitude = locationResult.getLastLocation().getLongitude();
                double Latitude = locationResult.getLastLocation().getLatitude();

                callTrackingService(Longitude, Latitude);
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }

        }, null);

        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }

//        try {
//            if (locationManager != null) {
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, interval, 10f, locationListener);
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, interval, 10f, locationListener);
//
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception : " + e.getMessage());
//        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //super.onStartCommand(intent, flags, startId);
        Bundle data = intent.getExtras();
        if (data != null) {
            user = data.getParcelable(Config.USER_KEY);
        }

        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        Bundle data = intent.getExtras();
        if (data != null) {
            user = data.getParcelable(Config.USER_KEY);
        }

        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "getLatitude : " + location.getLatitude());
        Log.d(TAG, "getLongitude : " + location.getLongitude());

        callTrackingService(location.getLatitude() , location.getLongitude());

    }

    private void callTrackingService(double lat, double log) {
        HttpClient client = retrofit.create(HttpClient.class);

        Call<TrackingResponse> call = client.resendTracking(lat, log, user.getUserID());

        call.enqueue(new Callback<TrackingResponse>() {
            @Override
            public void onResponse(Call<TrackingResponse> call, Response<TrackingResponse> response) {
                Log.d(TAG, "onResponse : ");
            }

            @Override
            public void onFailure(Call<TrackingResponse> call, Throwable t) {
                Log.d(TAG, "onFailure : ");
            }
        });
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d(TAG, "onStatusChanged : " + s);
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d(TAG, "onProviderEnabled : " + s);
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d(TAG, "onProviderDisabled : " + s);
    }
}
