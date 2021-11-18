package com.example.admin_classnet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ViewHolder> {

    public Context context;
    public List<String> class_ids, class_names, mentors;
    public List<ArrayList<String>> teachers, students;

    public ClassroomAdapter(Context context, List<String> class_ids, List<String> class_names, List<String> mentors, List<ArrayList<String>> teachers, List<ArrayList<String>> students)
    {
        this.class_ids = class_ids;
        this.class_names = class_names;
        this.context = context;
        this.mentors = mentors;
        this.teachers = teachers;
        this.students = students;
    }

    @NonNull
    @NotNull
    @Override
    public ClassroomAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_classroom, parent,  false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClassroomAdapter.ViewHolder holder, int position)
    {
        holder.tv_classname.setText(class_names.get(position));
        Intent intent = new Intent(context, activity_classroom.class);
        intent.putStringArrayListExtra("teachers", teachers.get(position));
        intent.putStringArrayListExtra("students", students.get(position));
        intent.putExtra("mentor_id", mentors.get(position));
        intent.putExtra("class_id", class_ids.get(position));
        intent.putExtra("class_name", class_names.get(position));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        holder.cv_classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return class_ids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cv_classroom;
        public TextView tv_classname;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_classname = itemView.findViewById(R.id.tv_item_classroom_classname);
            cv_classroom = itemView.findViewById(R.id.cv_item_classroom);
        }
    }
}
