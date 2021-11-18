package com.example.teacher_classnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_add_question extends AppCompatActivity {

    public Button btn_add_question, btn_finish;
    public EditText et_question, et_c1, et_c2, et_c3, et_c4, cm, wm;
    public HTTPRequests httpRequests;
    public OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        httpRequests = new HTTPRequests();
        client = new OkHttpClient();
        btn_add_question = findViewById(R.id.btn_add_question);
        btn_finish = findViewById(R.id.btn_finish);
        et_question = findViewById(R.id.et_question);
        et_c1 = findViewById(R.id.et_choice_1);
        et_c2 = findViewById(R.id.et_choice_2);
        et_c3 = findViewById(R.id.et_choice_3);
        et_c4 = findViewById(R.id.et_choice_4);
        cm = findViewById(R.id.et_mark_right);
        wm = findViewById(R.id.et_mark_wrong);
        wm.setVisibility(View.GONE);
        btn_add_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String question = et_question.getText().toString();
                ArrayList<String> choices = new ArrayList<>();
                choices.add(et_c1.getText().toString());
                choices.add(et_c2.getText().toString());
                choices.add(et_c3.getText().toString());
                choices.add(et_c4.getText().toString());
                String choice_count = "4";
                String negative_marks = wm.getText().toString();
                String marks = cm.getText().toString();
                String test_id = getIntent().getStringExtra("test_id");
                String correct_choice = "0";
                String reasoning = "0";
                String json = "{\"question\": \"" + question +  "\", \"choice_count\": \"" + choice_count +  "\", \"negative_marks\": 0, \"choices\": \"" + choices +  "\", \"test_id\": \"" + test_id + "\", \"correct_choice\": \"" + correct_choice + "\", \"marks\": \"" + marks + "\", \"reasoning\": \"" + reasoning + "\"}";
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    Log.e("sdafgh", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.newCall(httpRequests.put("http://192.168.43.38:8080/teacher/test/", json))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("wert", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("duck", String.valueOf(response.code()));
                                et_c1.setText("");
                                et_c2.setText("");
                                et_c3.setText("");
                                et_c4.setText("");
                                et_question.setText("");
                                cm.setText("");
                            }
                        });
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity_add_question.this, "Question added Successfully!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                client.newCall(httpRequests.post("http://192.168.43.38:8080/teacher/make/live/", "{\"test_id\": \"" + getIntent().getStringExtra("test_id") +  "\"}"))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("asdfghjkl", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("lived", String.valueOf(response.code()));
                            }
                        });
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity_add_question.this, "Test made live successfully!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}