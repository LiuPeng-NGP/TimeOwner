package com.example.timeowner.habit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectHabit;
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

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mCheckBox.isChecked()==true){
                    habitArrayList.get(holder.getAdapterPosition()).setHabitTodayIsCompleted(1);
                    habitArrayList.get(holder.getAdapterPosition()).setHabitCount(habitArrayList.get(holder.getAdapterPosition()).getHabitCount()+1);
                    holder.mHabitCountTextView.setText(String.valueOf(Integer.valueOf(holder.mHabitCountTextView.getText().toString())+1));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DBConnectHabit dbConnectHabit = new DBConnectHabit();
                            dbConnectHabit.update(habitArrayList.get(holder.getAdapterPosition()));

                        }
                    }).start();
                }else {
                    habitArrayList.get(holder.getAdapterPosition()).setHabitTodayIsCompleted(0);
                    habitArrayList.get(holder.getAdapterPosition()).setHabitCount(habitArrayList.get(holder.getAdapterPosition()).getHabitCount()-1);
                    holder.mHabitCountTextView.setText(String.valueOf(Integer.valueOf(holder.mHabitCountTextView.getText().toString())-1));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DBConnectHabit dbConnectHabit = new DBConnectHabit();
                            dbConnectHabit.update(habitArrayList.get(holder.getAdapterPosition()));

                        }
                    }).start();

                }


            }
        });

        holder.mCheckBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });




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


//    //自定义viewhodler
//    class myViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
//        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
//
//        private final CheckBox mCheckBox;
//        private final TextView mHabitCountTextView;
//
//
//
//        public myViewHolder(View itemView) {
//            super(itemView);
//            mCheckBox = itemView.findViewById(R.id.habit_checkbox);
//            mHabitCountTextView = itemView.findViewById(R.id.habit_text_view);
//            itemView.setOnCreateContextMenuListener(this);
//
//
//
//
//        }
//
//
//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.add(0, v.getId(), 0, "Change");
//        }
//    }
}
