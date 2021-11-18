package com.example.teacher_classnet;

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
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public EditText et_username, et_password;
    public Button btn_login;
    public OkHttpClient client;
    public HTTPRequests httpRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String j = et_username.getText().toString();
                String k = et_password.getText().toString();
                String json = "{\"username\": \"" + j +  "\", \"password\": \"" + k +  "\"}";
                Log.e("sdds", json);
                client.newCall(httpRequests.post("http://192.168.43.38:8080/teacher/login/", json))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("sdfregefbgegw", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor;
                                prefsEditor = myPrefs.edit();
                                prefsEditor.putString("token", response.body().string());
                                prefsEditor.commit();
                                progressDialog.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, " Logged in as " + et_username.getText().toString() + " successfully!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), activity_class_list.class));
                                    }
                                });
                            }
                        });
            }
        });
    }
}