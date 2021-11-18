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
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public EditText et_username, et_email;
    public Button btn_signup;
    public OkHttpClient client;
    public HTTPRequests httpRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences mp;
        mp = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        if(mp.getBoolean("signed_up", false))
        {
            startActivity(new Intent(getApplicationContext(), activity_enter_classroom.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_email = findViewById(R.id.et_signup_email);
        et_username = findViewById(R.id.et_signup_username);
        btn_signup = findViewById(R.id.btn_signup);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String j = et_username.getText().toString();
                String k = et_email.getText().toString();
                String json = "{\"username\": \"" + j +  "\", \"email\": \"" + k +  "\"}";
                Log.e("sdds", json);
                client.newCall(httpRequests.post("http://192.168.43.38:8080/users/signup/", json))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("stu 1", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor;
                                prefsEditor = myPrefs.edit();
                                prefsEditor.putString("s_user_id", response.body().string());
                                prefsEditor.putBoolean("signed_up", true);
                                prefsEditor.commit();
                                progressDialog.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, " Registered as " + et_username.getText().toString() + " successfully!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), activity_enter_classroom.class));
                                    }
                                });
                            }
                        });
            }
        });
    }
}