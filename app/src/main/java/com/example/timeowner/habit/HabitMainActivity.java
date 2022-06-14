package com.example.timeowner.habit;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectHabit;
import com.example.timeowner.object.Habit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HabitMainActivity extends Activity {

    private FloatingActionButton mFloatingActionButton;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_main);



        HabitShow();


        mFloatingActionButton=findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_things, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.update();
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);


                EditText mAddThings = (EditText) popupView.findViewById(R.id.habit_add_things_edit_text);
                Button mSureButton = (Button) popupView.findViewById(R.id.habit_sure_button);
                Button mCancelButton = (Button) popupView.findViewById(R.id.habit_cancel_button);


                mSureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
                        String userID = sharedPreferences.getString("userID",null);


//                        //Temp
//                        userID="191001";

//                        Log.i(TAG, "UserID has transferred to this activity! ");

                        Habit habit=new Habit(0,
                                mAddThings.getText().toString(),
                                0,
                                0,
                                userID);

                        new Thread(new Runnable() {
                            @Override

                            public void run() {
                                DBConnectHabit dbConnectHabit = new DBConnectHabit();
                                dbConnectHabit.insert(habit);
                            }


                        }).start();





                        popupWindow.dismiss();
                    }
                });

                mCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    popupWindow.dismiss();
                    }
                });


            }
        });


    }
    private void HabitShow(){
        ArrayList<Habit> habitArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Habit habit = new Habit();
            habit.setHabitName(i + " title");
            habit.setHabitTodayIsCompleted(i%2);
            habitArrayList.add(habit);
        }




        RecyclerView recyclerView = findViewById(R.id.habit_recycle_view);
        // recyclerview的适配器
        HabitAdapter habitAdapter = new HabitAdapter(this, habitArrayList);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 配置RecyclerView的分割线
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(habitAdapter);
    }
}
