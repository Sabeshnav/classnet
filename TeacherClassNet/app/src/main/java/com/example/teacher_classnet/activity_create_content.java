package com.example.teacher_classnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_create_content extends AppCompatActivity {

    public Button btn_post;
    public EditText et_message;
    public OkHttpClient client;
    public HTTPRequests httpRequests;
    public String tkn;
    public String class_name, class_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_content);
        class_name = getIntent().getStringExtra("class_name");
        class_id = getIntent().getStringExtra("class_id");
        btn_post = findViewById(R.id.btn_create_content_post);
        et_message = findViewById(R.id.et_create_content_message);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                SharedPreferences mp;
                mp = getSharedPreferences("myPrefs", MODE_PRIVATE);
                tkn=mp.getString("token", "");
                String json = "{\"token\": \"" + tkn +  "\", \"class_id\": \"" + class_id +  "\", \"message\": \"" + et_message.getText().toString() +  "\", \"subject_id\": 0, \"doc_url\": \"\"}";
                client.newCall(httpRequests.post("http://192.168.43.38:8080/teacher/content/post/filename/class_id/subject_id", json))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("errorororor", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("find", String.valueOf(response.code()));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity_create_content.this, "Information posted successfully!!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity_create_content.this, "Information published successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}