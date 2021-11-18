package com.example.admin_classnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

public class activity_class_teachers extends AppCompatActivity {

    public RecyclerView rv_students;
    public TeacherAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teachers);
        Log.e("st1", getIntent().getStringArrayListExtra("teacher_ids").toString());
        Log.e("stw1", getIntent().getStringArrayListExtra("teacher_names").toString());
        rv_students = findViewById(R.id.rv_teachers_classroom);rv_students.setLayoutManager(new LinearLayoutManager(this));
        rv_students.setAdapter(studentAdapter);
    }
}