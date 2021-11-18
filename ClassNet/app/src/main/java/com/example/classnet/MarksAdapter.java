package com.example.classnet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.ViewHolder> {

    public ArrayList<String> test_names, marks;
    public Context context;

    public MarksAdapter(Context context, ArrayList<String> test_names, ArrayList<String> marks)
    {
        this.test_names = test_names;
        this.marks = marks;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MarksAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_class_marks, parent,  false);
        MarksAdapter.ViewHolder viewHolder = new MarksAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MarksAdapter.ViewHolder holder, int position)
    {
        holder.tv_marks.setText(test_names.get(position) + " : " + marks.get(position));
    }

    @Override
    public int getItemCount() {
        return test_names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView tv_marks;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_marks = itemView.findViewById(R.id.tv_marks);
        }
    }
}
