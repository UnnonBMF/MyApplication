package com.example.nontz.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nontz.myapplication.model.UserModel;
import com.example.nontz.myapplication.service.TrackingService;

public class HomeActivity extends AppCompatActivity {


    private TextView tx_name,tx_uid,tx_tel;
    UserModel user;

private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intentReport = new Intent(context,ReportActivity.class);
               intentReport.putExtra(Config.USER_KEY,user);
               startActivity(intentReport);
            }
        });

        tx_name = findViewById(R.id.tx_name);
        tx_uid = findViewById(R.id.tx_uid);
        tx_tel = findViewById(R.id.tx_tel);

        Bundle data = getIntent().getExtras();
        if(data == null){
            Toast.makeText(context, "Haven't data", Toast.LENGTH_LONG).show();
            return;
        }
        user = data.getParcelable(Config.USER_KEY);
        tx_name.setText(user.getName());
        tx_uid.setText("UID : "+user.getUserID());
        tx_tel.setText("Tel : "+user.getTel());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001){
            //Start service
            //startTrackingService();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //close service
    }


}
