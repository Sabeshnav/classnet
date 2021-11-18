package com.example.admin_classnet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class activity_add_student extends AppCompatActivity {

    public EditText et_student_name, et_password;
    public Button btn_check;
    public OkHttpClient client;
    public HTTPRequests httpRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        btn_check = findViewById(R.id.btn_check_student_add);
        et_student_name = findViewById(R.id.et_student_name);
        et_password = findViewById(R.id.et_password);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String st_name = et_student_name.getText().toString();
                client.newCall(httpRequests.getRequest("http://192.168.43.38:8080/admin/student/"+st_name))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("sd", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    if(response.code() == 200)
                                    {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.body().string());
                                            String name = jsonObject.getString("username");
                                            String id = jsonObject.getString("id");
                                            String password = et_password.getText().toString();
                                            String json = "{\"username\": \"" + name +  "\", \"password\": \"" + password +  "\", \"user_id\": \"" + id +  "\", \"class_id\": \"" + getIntent().getStringExtra("class_id") +  "\"}";
                                            client.newCall(httpRequests.post("http://192.168.43.38:8080/admin/student/"+st_name, json))
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
                                                                    Toast.makeText(activity_add_student.this, name + " added to class successfully!", Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                        }
                                                    });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                            }
                        });
            }
        });
    }
}