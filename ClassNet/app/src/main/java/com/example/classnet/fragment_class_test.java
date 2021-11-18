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
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class fragment_class_test extends Fragment {

    public RecyclerView rv_tests;
    public TestAdapter contentAdapter;
    public ArrayList<String> test_names, test_ids, lives;
    public ArrayList<List<String>> question_ids;
    public HTTPRequests httpRequests;
    public OkHttpClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_class_test, container, false);
        rv_tests = view.findViewById(R.id.rv_test_test);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        test_ids = new ArrayList<>();
        test_names = new ArrayList<>();
        lives = new ArrayList<>();
        question_ids = new ArrayList<>();
        SharedPreferences mp;
        mp = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = mp.getString("s_token", "");
        String class_id = mp.getString("s_class_id", "");
        client.newCall(httpRequests.getRequest2("http://192.168.43.38:8080/users/class/test/mcq/", "token", token, "classid", class_id))
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
                            Log.e("res", jsonObject.toString());
                            JSONArray ja = jsonObject.getJSONArray("test_ids");
                            test_ids = new ArrayList<>();
                            for(int i = 0; i < ja.length(); i++)
                            {
                                test_ids.add((ja.get(i).toString()));
                            }
                            ja = jsonObject.getJSONArray("names");
                            test_names = new ArrayList<>();
                            for(int i = 0; i < ja.length(); i++)
                            {
                                test_names.add((ja.get(i).toString()));
                            }
                            ja = jsonObject.getJSONArray("lives");
                            lives = new ArrayList<>();
                            for(int i = 0; i < ja.length(); i++)
                            {
                                lives.add((ja.get(i).toString()));
                            }
                            ja = jsonObject.getJSONArray("question_ids");
                            question_ids = new ArrayList<>();
                            for(int i = 0; i < ja.length(); i++)
                            {
                                question_ids.add(Arrays.asList(ja.get(i).toString().split(",")));
                            }
                            Log.e("tidss", test_ids.toString());
                            Log.e("tttttt", test_names.toString());
                            Log.e("tea  gerg dates", lives.toString());
                            Log.e("tes", question_ids.toString());
                            contentAdapter = new TestAdapter(getActivity(), test_ids, test_names, lives, question_ids);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rv_tests.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    rv_tests.setAdapter(contentAdapter);
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