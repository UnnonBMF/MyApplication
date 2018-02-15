package com.example.nontz.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nontz.myapplication.model.ReportResponse;
import com.example.nontz.myapplication.model.UserModel;
import com.example.nontz.myapplication.model.UserResponse;
import com.example.nontz.myapplication.service.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {
    private UserModel user;
    private Context context = this;

    private EditText ed_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendReport();
            }
        });
        Bundle data = getIntent().getExtras();
        if (data == null) {
            Toast.makeText(context, "Haven't data", Toast.LENGTH_LONG).show();
            return;
        }
        user = data.getParcelable(Config.USER_KEY);

        ed_text = findViewById(R.id.ed_text);
    }

    private void sendReport(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.Host)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        HttpClient client = retrofit.create(HttpClient.class);

        Call<ReportResponse> call = client.resendReport(ed_text.getText().toString(),user.getUserID());
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                Toast.makeText(context, "Send complete", Toast.LENGTH_LONG).show();
                return;
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                Toast.makeText(context, "onFailure "+t.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

}
