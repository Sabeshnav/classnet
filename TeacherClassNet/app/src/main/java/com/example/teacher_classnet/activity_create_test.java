package com.example.teacher_classnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_create_test extends AppCompatActivity {

    public Button btn_create_test;
    public EditText et_test_name;
    public OkHttpClient client;
    public HTTPRequests httpRequests;
    public String tkn;
    public String class_name, class_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        class_name = getIntent().getStringExtra("class_name");
        class_id = getIntent().getStringExtra("class_id");
        btn_create_test = findViewById(R.id.btn_create_test);
        et_test_name = findViewById(R.id.et_test_name);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        btn_create_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mp;
                mp = getSharedPreferences("myPrefs", MODE_PRIVATE);
                tkn=mp.getString("token", "");
                String json = "{\"token\": \"" + tkn +  "\", \"class_id\": \"" + class_id +  "\", \"test_name\": \"" + et_test_name.getText().toString() +  "\", \"subject_id\": 0}";
                client.newCall(httpRequests.post("http://192.168.43.38:8080/teacher/test/", json))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("ioop", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("den", String.valueOf(response.code()));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity_create_test.this, "Test started successfully!!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), activity_add_question.class);
                                        String jsonObject = "";
                                        try {
                                            jsonObject = response.body().string();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        intent.putExtra("test_id", jsonObject);
                                        intent.putExtra("test_name", et_test_name.getText().toString());
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
            }
        });
    }
}