package com.example.admin_classnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_teacher_management extends AppCompatActivity {

    public RecyclerView rv_classroom;
    public TeacherAdapter teacherAdapter;
    public OkHttpClient client = new OkHttpClient();
    public HTTPRequests httpRequests;
    public ArrayList<String> teacher_ids, names, emails, phones;
    public ArrayList<ArrayList<String>> teachers, students;
    public Button btn_add_new_classroom;
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_management);
        rv_classroom = findViewById(R.id.rv_teachers);
        rv_classroom.setLayoutManager(new LinearLayoutManager(this));
        httpRequests = new HTTPRequests();
        btn_add_new_classroom = findViewById(R.id.btn_add_new_teacher);
        btn_add_new_classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), activity_create_new_teacher.class));
            }
        });
        client.newCall(httpRequests.getRequest("http://192.168.43.38:8080/admin/getall/teacher/")).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("error", e.getMessage().toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.e("res", jsonObject.toString());
                    JSONArray ja = jsonObject.getJSONArray("teacher_ids");
                    teacher_ids = new ArrayList<>();
                    for(int i = 0; i < ja.length(); i++)
                    {
                        teacher_ids.add((ja.get(i).toString()));
                    }
                    ja = jsonObject.getJSONArray("names");
                    names = new ArrayList<>();
                    for(int i = 0; i < ja.length(); i++)
                    {
                        names.add((ja.get(i).toString()));
                    }
                    ja = jsonObject.getJSONArray("emails");
                    emails = new ArrayList<>();
                    for(int i = 0; i < ja.length(); i++)
                    {
                        emails.add((ja.get(i).toString()));
                    }
                    ja = jsonObject.getJSONArray("phones");
                    phones = new ArrayList<>();
                    for(int i = 0; i < ja.length(); i++)
                    {
                        phones.add((ja.get(i).toString()));
                    }
                    Log.e("teacher_ids", teacher_ids.toString());
                    Log.e("names", names.toString());
                    Log.e("emails", emails.toString());
                    Log.e("phones", phones.toString());
                    teacherAdapter = new TeacherAdapter(getApplicationContext(), teacher_ids, names, emails, phones);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rv_classroom.setAdapter(teacherAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}