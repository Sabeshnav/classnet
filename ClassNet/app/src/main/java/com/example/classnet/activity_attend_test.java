package com.example.classnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_attend_test extends AppCompatActivity {

    public ArrayList<String> question_ids, questions, marks;
    public ArrayList<List<Integer>> randoms;
    public ArrayList<List<String>> choice_names, values;
    public OkHttpClient client;
    public Button btn_submit;
    public HTTPRequests httpRequests;
    public TestQuestionAdapter testQuestionAdapter;
    public RecyclerView rv_question;
    double t_marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_test);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        rv_question = findViewById(R.id.rv_attend_test);
        rv_question.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        btn_submit = findViewById(R.id.btn_submit_test);
        question_ids = new ArrayList<>();
        questions = new ArrayList<>();
        choice_names = new ArrayList<>();
        values = new ArrayList<>();
        randoms = new ArrayList<>();
        marks = new ArrayList<>();
        client.newCall(httpRequests.getRequest1("http://192.168.43.38:8080/users/test/attend/mcq/", "testid", getIntent().getStringExtra("test_id")))
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("test_id", e.getMessage());
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            int num = jsonObject.getInt("numbers");
                            for(int i = 1; i <= num; i++)
                            {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("q"+i);
                                Log.e("jsonobject1", jsonObject1.toString());
                                List<String> choices = new ArrayList<>();
                                choices.add(jsonObject1.getString("c1"));
                                choices.add(jsonObject1.getString("c2"));
                                choices.add(jsonObject1.getString("c3"));
                                choices.add(jsonObject1.getString("c4"));
                                Log.e("prime", choices.toString());
                                choice_names.add(choices);
                                List<String> valuess = new ArrayList<>();
                                valuess.add("1");
                                valuess.add("0");
                                valuess.add("0");
                                valuess.add("0");
                                values.add(valuess);
                                question_ids.add(jsonObject1.getString("id"));
                                questions.add(jsonObject1.getString("question"));
                                marks.add(jsonObject1.getString("marks"));
                                t_marks = t_marks + Double.parseDouble(jsonObject1.getString("marks"));
                                List<Integer> random = new ArrayList<>();
                                random.add(0);
                                random.add(1);
                                random.add(2);
                                random.add(3);
                                Collections.shuffle(random);
                                Log.e("shuffle", random.toString());
                                randoms.add(random);
                            }
                            SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor;
                            prefsEditor = myPrefs.edit();
                            prefsEditor.putString(myPrefs.getString("s_test_name", "")+"_marks", ""+t_marks);
                            prefsEditor.putString(myPrefs.getString("s_test_name", "")+"_score", "0");
                            prefsEditor.commit();
                            testQuestionAdapter = new TestQuestionAdapter(question_ids, questions, marks, choice_names, values, getApplicationContext(), randoms);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rv_question.setAdapter(testQuestionAdapter);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mp;
                mp = getSharedPreferences("myPrefs", MODE_PRIVATE);
                String test_name = mp.getString("s_test_name", "");
                String test_id = mp.getString("s_test_id", "");
                String user_id = mp.getString("s_user_id", "");
                String score = mp.getString(test_name+"_score", "");
                Log.e("werhjmbcxam", score);
                String json = "{\"user_id\": \"" + user_id +  "\", \"test_id\": \"" + test_id +  "\", \"marks\": \"" + score +  "\"}";
                client.newCall(httpRequests.post("http://192.168.43.38:8080/users/test/my/marks/", json))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("me", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("retest", response.body().string());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity_attend_test.this, "Your test has been submitted successfully!!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        });
            }
        });
    }
}