package com.example.admin_classnet;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_create_new_teacher extends AppCompatActivity {

    public EditText et_name, et_email, et_phone, et_password;
    public Button btn_add;
    public OkHttpClient client;
    public HTTPRequests httpRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_teacher);
        et_name = findViewById(R.id.et_teacher_name);
        et_email = findViewById(R.id.et_teacher_email);
        et_phone = findViewById(R.id.et_teacher_phone);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();

        // String json = "{\"username\": \"" + name +  "\", \"password\": \"" + password +  "\", \"user_id\": \"" + id +  "\", \"class_id\": \"" + getIntent().getStringExtra("class_id") +  "\"}";
        et_password = findViewById(R.id.et_teacher_password);
        btn_add = findViewById(R.id.btn_add_new_teacher_create);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String json = "{\"username\": \"" + et_name.getText().toString() +  "\", \"password\": \"" + et_password.getText().toString() +  "\", \"phone\": \"" + et_phone.getText().toString() +  "\", \"email\": \"" + et_email.getText().toString() +  "\"}";
                client.newCall(httpRequests.post("http://192.168.43.38:8080/admin/teacher/0", json))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("sdfregefbgegw", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("dffrgrgr", response.body().string());
                                progressDialog.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity_create_new_teacher.this, et_name.getText().toString() + " added successfully!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
            }
        });
    }
}