package com.example.admin_classnet;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class TeacherAdapter extends  RecyclerView.Adapter<TeacherAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<String> teacher_ids, teacher_names, emails, phones;

    public TeacherAdapter(Context context, ArrayList<String> teacher_ids, ArrayList<String> teacher_names, ArrayList<String> emails, ArrayList<String> phones)
    {
        this.context = context;
        this.emails = emails;
        this.phones = phones;
        this.teacher_ids = teacher_ids;
        this.teacher_names = teacher_names;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_teacher_classroom, parent,  false);
        TeacherAdapter.ViewHolder viewHolder = new TeacherAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TeacherAdapter.ViewHolder holder, int position)
    {
        holder.tv_student_name.setText(teacher_names.get(position));
        holder.tv_student_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_teacher_details.class);
                intent.putExtra("name", teacher_names.get(position));
                intent.putExtra("id", teacher_ids.get(position));
                intent.putExtra("email", emails.get(position));
                intent.putExtra("phone", phones.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacher_ids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tv_student_name;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_student_name = itemView.findViewById(R.id.tv_teacher_name_classroom);
        }
    }
}
