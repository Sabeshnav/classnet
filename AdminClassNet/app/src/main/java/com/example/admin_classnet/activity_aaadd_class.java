package com.example.admin_classnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class activity_aaadd_class extends AppCompatActivity {

    public EditText et_class_name;
    public Button btn_add_class;
    public OkHttpClient client;
    public HTTPRequests httpRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aaadd_class);
        et_class_name = findViewById(R.id.et_class_name);
        btn_add_class = findViewById(R.id.btn_add_class_post);
        client = new OkHttpClient();
        httpRequests = new HTTPRequests();
        btn_add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String class_name = et_class_name.getText().toString();
                String json = "{\"class_name\": \"" + class_name +  "\"}";
                client.newCall(httpRequests.post("http://192.168.43.38:8080/admin/class/0", json))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("asddf", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("fg", String.valueOf(response.code()));
                                progressDialog.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity_aaadd_class.this, class_name + " created successfully!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
            }
        });
    }
}