package com.example.admin_classnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class activity_classroom extends AppCompatActivity {

    public TextView tv_class_name;
    public HTTPRequests httpRequests;
    public Button btn_add_new_student;
    public OkHttpClient client;
    public ArrayList<String> teacher_ids, teacher_names,  student_ids, student_names;
    public Button btn_view_class_students, btn_view_class_teachers, btn_delete_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        tv_class_name = findViewById(R.id.tv_class_name);
        tv_class_name.setText(getIntent().getStringExtra("class_name"));
        httpRequests = new HTTPRequests();
        client = new OkHttpClient();
        btn_view_class_students = findViewById(R.id.btn_view_students);
        btn_add_new_student = findViewById(R.id.btn_add_new_classrooom_student);
        btn_delete_class = findViewById(R.id.btn_delete_classroom);
        btn_add_new_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_add_student.class);
                intent.putExtra("class_id", getIntent().getStringExtra("class_id"));
                startActivity(intent);
            }
        });
        btn_view_class_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.newCall(httpRequests.getRequest1("http://192.168.43.38:8080/admin/student/list/", "class", getIntent().getStringExtra("class_id")))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("errrr", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response.body().string());
                                    Log.e("res", jsonObject.toString());
                                    JSONArray ja = jsonObject.getJSONArray("user_ids");
                                    student_ids = new ArrayList<>();
                                    for(int i = 0; i < ja.length(); i++)
                                    {
                                        student_ids.add((ja.get(i).toString()));
                                    }
                                    ja = jsonObject.getJSONArray("usernames");
                                    student_names = new ArrayList<>();
                                    for(int i = 0; i < ja.length(); i++)
                                    {
                                        student_names.add((ja.get(i).toString()));
                                    }
                                    Log.e("idss", student_ids.toString());
                                    Log.e("tea", student_names.toString());
                                    Intent intent = new Intent(getApplicationContext(), activity_class_sttudents.class);
                                    intent.putStringArrayListExtra("student_ids", student_ids);
                                    intent.putStringArrayListExtra("student_names", student_names);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(intent);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        btn_delete_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.newCall(httpRequests.delete("http://192.168.43.38:8080/admin/class/"+getIntent().getStringExtra("class_id")))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("df", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("df", String.valueOf(response.code()));
                            }
                        });
            }
        });
    }
}