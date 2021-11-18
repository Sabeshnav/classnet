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

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    public ArrayList<String> test_ids, names, lives;
    public ArrayList<List<String>> question_ids;
    public Context context;

    public TestAdapter(Context context, ArrayList<String> test_ids, ArrayList<String> names, ArrayList<String> lives, ArrayList<List<String>> question_ids)
    {
        this.question_ids = question_ids;
        this.names = names;
        this.lives = lives;
        this.test_ids = test_ids;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_test, parent,  false);
        TestAdapter.ViewHolder viewHolder = new TestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TestAdapter.ViewHolder holder, int position) {
        holder.tv_test_name.setText(names.get(position));
        Intent intent = new Intent(context, activity_test_details.class);
        intent.putExtra("test_id", test_ids.get(position));
        ArrayList<String> h = new ArrayList<>();
        h.addAll(question_ids.get(position));
        intent.putStringArrayListExtra("question_ids", h);
        intent.putExtra("status", lives.get(position));
        intent.putExtra("name", names.get(position));
        holder.cv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return test_ids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_test_name;
        public CardView cv_test;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_test_name = itemView.findViewById(R.id.tv_item_test_name);
            cv_test = itemView.findViewById(R.id.cv_test);
        }
    }
}

/*public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    public ArrayList<String> test_ids, names, lives;
    public ArrayList<List<String>> question_ids;
    public Context context;

    public TestAdapter(Context context, ArrayList<String> test_ids, ArrayList<String> names, ArrayList<String> lives, ArrayList<List<String>> question_ids)
    {
        this.question_ids = question_ids;
        this.names = names;
        this.lives = lives;
        this.test_ids = test_ids;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_test, parent,  false);
        TestAdapter.ViewHolder viewHolder = new TestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TestAdapter.ViewHolder holder, int position) {
        holder.tv_test_name.setText(names.get(position));
        if(lives.get(position).equals("1"))
        {
            holder.tv_status.setText("The test is live.");
            holder.btn_live.setVisibility(View.GONE);
        }
        else
        {
            holder.tv_status.setText("The test is not live.");
            holder.btn_unlive.setVisibility(View.GONE);
        }
        Intent intent = new Intent(context, activity_test_details.class);
        intent.putExtra("test_id", test_ids.get(position));
        intent.putStringArrayListExtra("question_ids", (ArrayList<String>) question_ids.get(position));
        intent.putExtra("status", lives.get(position));
        intent.putExtra("name", names.get(position));
        holder.cv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return test_ids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_test_name, tv_status;
        public Button btn_live, btn_unlive;
        public CardView cv_test;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_status = itemView.findViewById(R.id.tv_test_details_status);
            tv_test_name = itemView.findViewById(R.id.tv_item_test_name);
            btn_live = itemView.findViewById(R.id.btn_make_live);
            btn_unlive = itemView.findViewById(R.id.btn_take_down);
            cv_test = itemView.findViewById(R.id.cv_test);
        }
    }
}*/
