package com.example.timeowner.habit;

import static com.example.timeowner.target.TargetMainActivity.UPDATE_TEXT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
//        if (viewType==)
        View itemView = View.inflate(context, R.layout.habit_item, null);
        return new myViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Habit habit = habitArrayList.get(position);
        holder.mCheckBox.setText(habit.getHabitName());
        holder.mHabitCountTextView.setText(String.valueOf(habit.getHabitCount()));
        if(habit.getHabitTodayIsCompleted()>0){
            holder.mCheckBox.setChecked(true);
        }else{
            holder.mCheckBox.setChecked(false);
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
//                Context context = v.getContext();
//                CharSequence text = "Hello toast!";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();


                holder.mCheckBox.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.add("change");
                        SpannableString spanString = new SpannableString(menu.getItem(0).getTitle().toString());
                        int end = spanString.length();
                        spanString.setSpan(new RelativeSizeSpan(1.15f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        menu.getItem(0).setTitle(spanString);

                        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                // inflate the layout of the popup window
                                LayoutInflater inflater = (LayoutInflater)
                                        context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                                View popupView = inflater.inflate(R.layout.habit_change_things, null);

                                // create the popup window
                                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                boolean focusable = true; // lets taps outside the popup also dismiss it
                                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                                // show the popup window

                                popupWindow.update();
                                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                                popupWindow.setFocusable(true);
                                popupWindow.setTouchable(true);


                                EditText mChangeThingsEditText = (EditText) popupView.findViewById(R.id.habit_change_things_edit_text);
                                Button mSureButton = (Button) popupView.findViewById(R.id.habit_sure_button);
                                Button mCancelButton = (Button) popupView.findViewById(R.id.habit_cancel_button);


                                mSureButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        @SuppressLint("HandlerLeak") Handler handler = new Handler() {

                                            @Override
                                            public void handleMessage(@NonNull Message msg) {
                                                switch (msg.what) {
                                                    case UPDATE_TEXT:
                                                        popupWindow.dismiss();
//                                        HabitShow();
                                                        habitArrayList.get(holder.getPosition()).setHabitName(mChangeThingsEditText.getText().toString());
                                                        holder.mCheckBox.setText(mChangeThingsEditText.getText().toString());

                                                        super.handleMessage(msg);
                                                }

                                            }


                                        };


                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DBConnectHabit dbConnectHabit = new DBConnectHabit();


                                                habit.setHabitName(mChangeThingsEditText.getText().toString());

                                                dbConnectHabit.update(habit);



                                                Message msg = new Message();
                                                msg.what = UPDATE_TEXT;
                                                handler.sendMessage(msg);
                                                try {
                                                    Thread.sleep(100);
                                                } catch (InterruptedException e) {
                                                    Thread.currentThread().interrupt();
                                                }
                                            }
                                        }).start();
                                    }
                                });

                                mCancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popupWindow.dismiss();
                                    }
                                });


                                return true;
                            }
                        });
                    }
                });
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return habitArrayList.size();
    }
    //?????????viewhodler
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
