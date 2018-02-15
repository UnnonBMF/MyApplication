package com.example.nontz.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nontz.myapplication.model.UserModel;
import com.example.nontz.myapplication.model.UserResponse;
import com.example.nontz.myapplication.service.HttpClient;
import com.example.nontz.myapplication.service.TrackingService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.internal.connection.StreamAllocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUsername, txtPassword;
    Button btnSignin;
    String TAG = "MainActivity";
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_password);
        btnSignin = findViewById(R.id.btn_signin);
        btnSignin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_signin) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1001);
                return;
            }

            String username = txtUsername.getText().toString();
            if ("".equals(username)) {
                Toast.makeText(this, "Username is required", Toast.LENGTH_LONG).show();
                return;
            }

            String password = txtPassword.getText().toString();
            if ("".equals(password)) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show();
                return;
            }

            login(username, password);

        }
    }


    private void login(String username, String password) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.Host)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        HttpClient client = retrofit.create(HttpClient.class);

        Call<UserResponse> call = client.resendLogin(username, password);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserModel data = response.body().getData();
                if (data == null) {
                    Toast.makeText(context, "Cannot connect service", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!response.body().isStatus()) {
                    Toast.makeText(context, "Username or password incorrect", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), TrackingService.class);
                intent.putExtra(Config.USER_KEY, data);
                startService(intent);


                Intent intentHome = new Intent(context, HomeActivity.class);
                intentHome.putExtra(Config.USER_KEY, data);
                startActivity(intentHome);

                finish();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, "onFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            //Start service
            startTrackingService();
        }
    }

    private void startTrackingService() {
        Intent intent = new Intent(getApplicationContext(), TrackingService.class);
        startService(intent);
    }

}
