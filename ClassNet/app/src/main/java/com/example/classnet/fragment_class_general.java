package com.example.classnet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class fragment_class_general extends Fragment {

    public RecyclerView rv_infos;
    public ContentAdapter contentAdapter;
    public ArrayList<String> dates, messages, message_ids;
    public HTTPRequests httpRequests;
    public OkHttpClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_general, container, false);
        rv_infos = view.findViewById(R.id.rv_general_infos);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        message_ids = new ArrayList<>();
        messages = new ArrayList<>();
        dates = new ArrayList<>();
        SharedPreferences mp;
        mp = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = mp.getString("s_token", "");
        String class_id = mp.getString("s_class_id", "");
        client.newCall(httpRequests.getRequest2("http://192.168.43.38:8080/users/class/content/", "token", token, "classid", class_id))
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("dfghjkloiuyt", e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            JSONArray ja = jsonObject.getJSONArray("messages");
                            messages = new ArrayList<>();
                            for(int i = 0; i < ja.length(); i++)
                            {
                                messages.add((ja.get(i).toString()));
                            }
                            ja = jsonObject.getJSONArray("ids");
                            message_ids = new ArrayList<>();
                            for(int i = 0; i < ja.length(); i++)
                            {
                                message_ids.add((ja.get(i).toString()));
                            }
                            ja = jsonObject.getJSONArray("date_times");
                            dates = new ArrayList<>();
                            for(int i = 0; i < ja.length(); i++)
                            {
                                dates.add((ja.get(i).toString()));
                            }
                            Log.e("idss", message_ids.toString());
                            Log.e("tea", messages.toString());
                            Log.e("tea dates", dates.toString());
                            contentAdapter = new ContentAdapter(message_ids, messages, dates);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rv_infos.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    rv_infos.setAdapter(contentAdapter);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return view;
    }
}