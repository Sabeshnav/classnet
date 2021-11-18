package com.example.teacher_classnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_test_details extends AppCompatActivity {

    public TextView tv_test_name, tv_status;
    public Button btn_live, btn_unlive;
    public OkHttpClient client;
    public HTTPRequests httpRequests;
    public String test_id, test_name, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_details);
        tv_status = findViewById(R.id.tv_test_details_status);
        tv_test_name = findViewById(R.id.tv_test_details_name);
        btn_live = findViewById(R.id.btn_make_live);
        btn_unlive = findViewById(R.id.btn_take_down);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        test_id = getIntent().getStringExtra("test_id");
        test_name = getIntent().getStringExtra("name");
        status = getIntent().getStringExtra("status");
        if(status.equals("1"))
        {
            tv_status.setText("The test is live.");
            btn_live.setVisibility(View.GONE);
        }
        else
        {
            tv_status.setText("The test is not live.");
            btn_unlive.setVisibility(View.GONE);
        }
        tv_test_name.setText(test_name);
        btn_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.newCall(httpRequests.post("http://192.168.43.38:8080/teacher/make/live/", "{\"test_id\": \"" + getIntent().getStringExtra("test_id") +  "\"}"))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("asdfghjkl", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("lived again", String.valueOf(response.code()));
                            }
                        });
            }
        });
        btn_unlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.newCall(httpRequests.post("http://192.168.43.38:8080/teacher/take/down/", "{\"test_id\": \"" + getIntent().getStringExtra("test_id") +  "\"}"))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("asdfghjkl", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("down", String.valueOf(response.code()));
                            }
                        });
            }
        });
    }
}