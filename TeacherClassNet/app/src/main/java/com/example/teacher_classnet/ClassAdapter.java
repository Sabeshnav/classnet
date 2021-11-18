package com.example.teacher_classnet;

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

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    public Context context;
    public ArrayList<String> class_ids, class_names;

    public ClassAdapter(Context context, ArrayList<String> class_ids, ArrayList<String> class_names)
    {
        this.class_ids = class_ids;
        this.class_names = class_names;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ClassAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_class, parent,  false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.tv_class_name.setText(class_names.get(position));
        holder.cv_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_class_main.class);
                intent.putExtra("class_name", class_names.get(position));
                intent.putExtra("class_id", class_ids.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return class_ids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cv_class;
        public TextView tv_class_name;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cv_class = itemView.findViewById(R.id.card_class);
            tv_class_name = itemView.findViewById(R.id.tv_card_class_name);
        }
    }
}
