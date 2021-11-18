package com.example.teacher_classnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_class_list extends AppCompatActivity {

    public OkHttpClient client;
    public HTTPRequests httpRequests;
    public RecyclerView rv_classes;
    public ClassAdapter classAdapter;
    public ArrayList<String> class_ids, class_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        rv_classes = findViewById(R.id.rv_clases);
        rv_classes.setLayoutManager(new LinearLayoutManager(this));
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        Log.e("kklklk", getPreferences(Context.MODE_PRIVATE).getString("token", "null"));
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
                    Log.e("class_ids", class_ids.toString());
                    Log.e("class_names", class_names.toString());
                    classAdapter = new ClassAdapter(getApplicationContext(), class_ids, class_names);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rv_classes.setAdapter(classAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}