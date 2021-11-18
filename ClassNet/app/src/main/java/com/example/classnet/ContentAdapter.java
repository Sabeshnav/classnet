package com.example.classnet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    public ArrayList<String> messages, dates, message_ids;

    public ContentAdapter(ArrayList<String> message_ids, ArrayList<String> messages, ArrayList<String> dates)
    {
        this.message_ids = message_ids;
        this.dates = dates;
        this.messages = messages;
    }

    @NonNull
    @NotNull
    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_class_content, parent,  false);
        ContentAdapter.ViewHolder viewHolder = new ContentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ContentAdapter.ViewHolder holder, int position) {
        holder.tv_message.setText(messages.get(position));
        holder.tv_date_time.setText(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return message_ids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_message, tv_date_time;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_date_time = itemView.findViewById(R.id.tv_class_content_date_time);
            tv_message = itemView.findViewById(R.id.tv_class__content_message);
        }
    }
}
