package com.example.classnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class fragment_class_my_marks extends Fragment {

    public RecyclerView rv_tests;
    public MarksAdapter contentAdapter;
    public ArrayList<String> test_names, marks;
    public HTTPRequests httpRequests;
    public OkHttpClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_class_my_marks, container, false);
        rv_tests = view.findViewById(R.id.rv_marks);
        rv_tests.setLayoutManager(new LinearLayoutManager(getActivity()));
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        test_names = new ArrayList<>();
        SharedPreferences mp;
        mp = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = mp.getString("s_token", "");
        String class_id = mp.getString("s_user_id", "");
        client.newCall(httpRequests.getRequest1("http://192.168.43.38:8080/users/test/my/marks/", "userid", class_id))
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
                            JSONArray ja = jsonObject.getJSONArray("names");
                            test_names = new ArrayList<>();
                            for(int i = 0; i < ja.length(); i++)
                            {
                                test_names.add((ja.get(i).toString()));
                            }
                            ja = jsonObject.getJSONArray("marks");
                            marks = new ArrayList<>();
                            for(int i = 0; i < ja.length(); i++)
                            {
                                marks.add((ja.get(i).toString()));
                            }
                            Log.e("tidss", marks.toString());
                            Log.e("tttttt", test_names.toString());
                            contentAdapter = new MarksAdapter(getActivity(), test_names, marks);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
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