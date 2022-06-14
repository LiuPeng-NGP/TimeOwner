package com.example.timeowner.habit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;
import com.example.timeowner.object.Habit;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.myViewHolder> {

    private final Context context;

    private final ArrayList<Habit> habitArrayList;

    public HabitAdapter(Context context, ArrayList<Habit> habitArrayList) {
        this.context = context;
        this.habitArrayList = habitArrayList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.habit_item, null);
        return new myViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Habit habit = habitArrayList.get(position);
        holder.mCheckBox.setText(habit.getHabitName());
        holder.mHabitCountTextView.setText(String.valueOf(habit.getHabitCount()));
        if(habit.getHabitTodayIsCompleted()>0){
            holder.mCheckBox.setSelected(true);
        }else{
            holder.mCheckBox.setSelected(false);
        }

    }

    @Override
    public int getItemCount() {
        return habitArrayList.size();
    }

    //自定义viewhodler
    class myViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})

        private final CheckBox mCheckBox;
        private final TextView mHabitCountTextView;



        public myViewHolder(View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.habit_checkbox);
            mHabitCountTextView = itemView.findViewById(R.id.habit_text_view);
        }
    }
}
