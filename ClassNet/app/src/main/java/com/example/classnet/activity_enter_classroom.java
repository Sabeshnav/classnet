package com.example.classnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_enter_classroom extends AppCompatActivity {

    public TextView tv_name;
    public EditText et_password;
    public Button btn_enter;
    public HTTPRequests httpRequests;
    public OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_classroom);
        tv_name = findViewById(R.id.tv_class_shower);
        et_password = findViewById(R.id.et_password);
        btn_enter = findViewById(R.id.btn_enter_classroom);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        SharedPreferences mp;
        mp = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String user_id = mp.getString("s_user_id", "");
        client.newCall(httpRequests.getRequest("http://192.168.43.38:8080/users/" + user_id  +  "/get/"))
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("get error", e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String username = jsonObject.getString("username");
                            String email = jsonObject.getString("email");
                            String class_id = jsonObject.getString("class_id");
                            String class_name = jsonObject.getString("class_name");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_name.setText("You have been added to " + class_name);
                                }
                            });
                            SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor;
                            prefsEditor = myPrefs.edit();
                            prefsEditor.putString("s_username", username);
                            prefsEditor.putString("s_email", email);
                            prefsEditor.putString("s_class_id", class_id);
                            prefsEditor.putString("s_class_name", class_name);
                            prefsEditor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String j = et_password.getText().toString();
                SharedPreferences mp;
                mp = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                String user_id = mp.getString("s_user_id", "");
                String class_id = mp.getString("s_class_id", "");
                String json = "{\"password\": \"" + j +  "\", \"user_id\": \"" + user_id +  "\", \"class_id\": \"" + class_id +  "\"}";
                Log.e("sdds", json);
                client.newCall(httpRequests.post("http://192.168.43.38:8080/users/enter/class/", json))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("stu 1 enter", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor;
                                prefsEditor = myPrefs.edit();
                                prefsEditor.putString("s_token", response.body().string());
                                prefsEditor.commit();
                                progressDialog.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity_enter_classroom.this, " Entered the class successfully!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), activity_class_main.class);
                                        SharedPreferences mp;
                                        mp = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                                        String class_id = mp.getString("s_class_id", "");
                                        String class_name = mp.getString("s_class_name", "");
                                        intent.putExtra("class_name", class_name);
                                        intent.putExtra("class_id", class_id);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
            }
        });
    }
}