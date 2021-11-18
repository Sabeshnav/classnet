package com.example.classnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TestQuestionAdapter extends RecyclerView.Adapter<TestQuestionAdapter.ViewHolder> {

    public ArrayList<String> question_ids, questions, marks;
    public ArrayList<List<Integer>> randoms;
    public ArrayList<List<String>> choice_names, values;
    public Context context;

    public TestQuestionAdapter(ArrayList<String> question_ids, ArrayList<String> questions, ArrayList<String> marks, ArrayList<List<String>> choice_names, ArrayList<List<String>> values, Context context, ArrayList<List<Integer>> randoms)
    {
        this.question_ids = question_ids;
        this.questions = questions;
        this.marks = marks;
        this.values = values;
        this.choice_names = choice_names;
        this.context = context;
        this.randoms = randoms;
    }

    @NonNull
    @NotNull
    @Override
    public TestQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_test_question, parent,  false);
        TestQuestionAdapter.ViewHolder viewHolder = new TestQuestionAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.tv_question.setText(questions.get(position));
        List<String> choices = choice_names.get(position);
        holder.rb_c1.setText(choices.get(randoms.get(position).get(0)));
        holder.rb_c2.setText(choices.get(randoms.get(position).get(1)));
        holder.rb_c3.setText(choices.get(randoms.get(position).get(2)));
        holder.rb_c4.setText(choices.get(randoms.get(position).get(3)));
        SharedPreferences mp;
        mp = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String test_id = mp.getString("s_test_id", "");
        String test_name = mp.getString("s_test_name", "");
        holder.rb_c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = randoms.get(position).get(0);
                if(values.get(position).get(i).equals("1"))
                {
                    SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.putString(test_name+"_score", ""+(Double.parseDouble(myPrefs.getString(test_name+"_score", "")) + Double.parseDouble(marks.get(position))));
                    prefsEditor.commit();
                }
            }
        });
        holder.rb_c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = randoms.get(position).get(1);
                if(values.get(position).get(i).equals("1"))
                {
                    SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.putString(test_name+"_score", ""+(Double.parseDouble(myPrefs.getString(test_name+"_score", "")) + Double.parseDouble(marks.get(position))));
                    prefsEditor.commit();
                }
            }
        });
        holder.rb_c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = randoms.get(position).get(2);
                if(values.get(position).get(i).equals("1"))
                {
                    SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.putString(test_name+"_score", ""+(Double.parseDouble(myPrefs.getString(test_name+"_score", "")) + Double.parseDouble(marks.get(position))));
                    prefsEditor.commit();
                }
            }
        });
        holder.rb_c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = randoms.get(position).get(3);
                if(values.get(position).get(i).equals("1"))
                {
                    SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.putString(test_name+"_score", ""+(Double.parseDouble(myPrefs.getString(test_name+"_score", "")) + Double.parseDouble(marks.get(position))));
                    prefsEditor.commit();
                }
            }
        });
        holder.btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("purse", String.valueOf(holder.rg_choices.getCheckedRadioButtonId()));
                holder.rb_c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = randoms.get(position).get(0);
                        if(values.get(position).get(i).equals("1"))
                        {
                            SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor;
                            prefsEditor = myPrefs.edit();
                            prefsEditor.putString(test_name+"_score", ""+(Double.parseDouble(myPrefs.getString(test_name+"_score", "")) - Double.parseDouble(marks.get(position))));
                            prefsEditor.commit();
                        }
                    }
                });
                holder.rb_c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = randoms.get(position).get(1);
                        if(values.get(position).get(i).equals("1"))
                        {
                            SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor;
                            prefsEditor = myPrefs.edit();
                            prefsEditor.putString(test_name+"_score", ""+(Double.parseDouble(myPrefs.getString(test_name+"_score", "")) - Double.parseDouble(marks.get(position))));
                            prefsEditor.commit();
                        }
                    }
                });
                holder.rb_c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = randoms.get(position).get(2);
                        if(values.get(position).get(i).equals("1"))
                        {
                            SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor;
                            prefsEditor = myPrefs.edit();
                            prefsEditor.putString(test_name+"_score", ""+(Double.parseDouble(myPrefs.getString(test_name+"_score", "")) - Double.parseDouble(marks.get(position))));
                            prefsEditor.commit();
                        }
                    }
                });
                holder.rb_c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = randoms.get(position).get(3);
                        if(values.get(position).get(i).equals("1"))
                        {
                            SharedPreferences myPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor;
                            prefsEditor = myPrefs.edit();
                            prefsEditor.putString(test_name+"_score", ""+(Double.parseDouble(myPrefs.getString(test_name+"_score", "")) - Double.parseDouble(marks.get(position))));
                            prefsEditor.commit();
                        }
                    }
                });
                holder.rb_c1.setChecked(false);
                holder.rb_c2.setChecked(false);
                holder.rb_c3.setChecked(false);
                holder.rb_c4.setChecked(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return question_ids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_question;
        public RadioButton rb_c1, rb_c2, rb_c3, rb_c4;
        public Button btn_reset;
        public RadioGroup rg_choices;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            rb_c1 = itemView.findViewById(R.id.rb_c1);
            rb_c2 = itemView.findViewById(R.id.rb_c2);
            rb_c3 = itemView.findViewById(R.id.rb_c3);
            rb_c4 = itemView.findViewById(R.id.rb_c4);
            btn_reset = itemView.findViewById(R.id.btn_reset);
            rg_choices = itemView.findViewById(R.id.rg_choices);
        }
    }
}
