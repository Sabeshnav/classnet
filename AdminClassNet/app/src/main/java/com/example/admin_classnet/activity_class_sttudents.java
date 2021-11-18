package com.example.admin_classnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class activity_class_sttudents extends AppCompatActivity {

    public RecyclerView rv_students;
    public StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_sttudents);
        Log.e("st", getIntent().getStringArrayListExtra("student_ids").toString());
        Log.e("stw", getIntent().getStringArrayListExtra("student_names").toString());
        rv_students = findViewById(R.id.rv_students_classroom);
        studentAdapter = new StudentAdapter(getApplicationContext(), getIntent().getStringArrayListExtra("student_ids"), getIntent().getStringArrayListExtra("student_names"));
        rv_students.setLayoutManager(new LinearLayoutManager(this));
        rv_students.setAdapter(studentAdapter);
    }
}