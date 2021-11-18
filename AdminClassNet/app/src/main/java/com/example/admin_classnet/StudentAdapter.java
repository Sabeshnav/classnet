package com.example.admin_classnet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    public Context context;
    public List<String> student_ids, student_names;

    public StudentAdapter(Context context, ArrayList<String> student_ids, ArrayList<String> student_names)
    {
        this.context = context;
        this.student_ids = student_ids;
        this.student_names = student_names;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_student_classroom, parent,  false);
        StudentAdapter.ViewHolder viewHolder = new StudentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentAdapter.ViewHolder holder, int position) {
        holder.tv_student_name.setText(student_names.get(position));
        holder.btn_remove_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                HTTPRequests httpRequests = new HTTPRequests();
                client.newCall(httpRequests.delete1("http://192.168.43.38:8080/admin/student/k", "userid", student_ids.get(position)))
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("wer", e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.e("donoo", String.valueOf(response.code()));
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return student_ids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_student_name;
        public Button btn_remove_student;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_student_name = itemView.findViewById(R.id.tv_student_name_classroom);
            btn_remove_student = itemView.findViewById(R.id.btn_remove_student);
        }
    }
}
