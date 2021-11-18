package com.example.admin_classnet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_teacher_details extends AppCompatActivity {

    public TextView tv_name, tv_email, tv_phone;
    public Button btn_delete;
    public OkHttpClient client;
    public HTTPRequests httpRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        tv_email = findViewById(R.id.tv_email);
        tv_name = findViewById(R.id.tv_teacher_name);
        tv_phone = findViewById(R.id.tv_phone);
        btn_delete = findViewById(R.id.btn_delete_teacher);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        tv_name.setText(getIntent().getStringExtra("name"));
        tv_email.setText(getIntent().getStringExtra("email"));
        tv_phone.setText(getIntent().getStringExtra("phone"));
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.newCall(httpRequests.delete("http://192.168.43.38:8080/admin/teacher/"+getIntent().getStringExtra("id")))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("dddf", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("dfdd", String.valueOf(response.code()));
                            }
                        });
            }
        });
    }
}