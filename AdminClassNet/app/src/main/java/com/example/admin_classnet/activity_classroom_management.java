package com.example.admin_classnet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class activity_classroom_management extends AppCompatActivity {

    public RecyclerView rv_classroom;
    public ClassroomAdapter classroomAdapter;
    public OkHttpClient client = new OkHttpClient();
    public HTTPRequests httpRequests;
    public List<String> class_ids, class_names, mentors;
    public List<ArrayList<String>> teachers, students;
    public Button btn_add_new_classroom;
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_management);
        rv_classroom = findViewById(R.id.rv_classrooms);
        rv_classroom.setLayoutManager(new LinearLayoutManager(this));
        httpRequests = new HTTPRequests();
        btn_add_new_classroom = findViewById(R.id.btn_add_new_classrooom);
        client.newCall(httpRequests.getRequest("http://192.168.43.38:8080/admin/class/0")).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("error", e.getMessage().toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.e("res", jsonObject.toString());
                    JSONArray ja = jsonObject.getJSONArray("class_ids");
                    class_ids = new ArrayList<>();
                    for(int i = 0; i < ja.length(); i++)
                    {
                        class_ids.add((ja.get(i).toString()));
                    }
                    ja = jsonObject.getJSONArray("class_names");
                    class_names = new ArrayList<>();
                    for(int i = 0; i < ja.length(); i++)
                    {
                        class_names.add((ja.get(i).toString()));
                    }
                    ja = jsonObject.getJSONArray("mentors");
                    mentors = new ArrayList<>();
                    for(int i = 0; i < ja.length(); i++)
                    {
                        mentors.add((ja.get(i).toString()));
                    }
                    ja = jsonObject.getJSONArray("teachers");
                    teachers = new ArrayList<>();
                    for(int i = 0; i < ja.length(); i++)
                    {
                        teachers.add(new ArrayList<>());
                        JSONObject jo = new JSONObject(ja.get(i).toString());
                        String jaa = jo.getString("teachers");
                        String[] strsplit = jaa.split(",");
                        for(int j = 0 ; j < strsplit.length; j++)
                        {
                            teachers.get(i).add(strsplit[j]);
                        }
                    }
                    ja = jsonObject.getJSONArray("students");
                    students = new ArrayList<>();
                    for(int i = 0; i < ja.length(); i++)
                    {
                        students.add(new ArrayList<>());
                        JSONObject jo = new JSONObject(ja.get(i).toString());
                        String jaa = jo.getString("students");
                        String[] strsplit = jaa.split(",");
                        for(int j = 0 ; j < strsplit.length; j++)
                        {
                            students.get(i).add(strsplit[j]);
                        }
                    }
                    Log.e("class_ids", class_ids.toString());
                    Log.e("class_names", class_names.toString());
                    Log.e("mentors", mentors.toString());
                    Log.e("teachers", teachers.toString());
                    Log.e("students", students.toString());
                    classroomAdapter = new ClassroomAdapter(getApplicationContext(), class_ids, class_names, mentors, teachers, students);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rv_classroom.setAdapter(classroomAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_add_new_classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), activity_aaadd_class.class));
            }
        });
    }
}