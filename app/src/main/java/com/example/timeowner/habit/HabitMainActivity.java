package com.example.timeowner.habit;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectHabit;
import com.example.timeowner.object.Habit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HabitMainActivity extends Activity {

    private FloatingActionButton mFloatingActionButton;
    private RecyclerView recyclerView;
    private static final int TEST_USER_SELECT = 1;
    private static final int UPDATE_TEXT = 1;
    ArrayList<Habit> habitArrayList = new ArrayList<Habit>();
    private String userID;
    private HabitAdapter habitAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_main);
        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("userID",null);


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




                        Habit addNewHabit =new Habit(0,
                                mAddThings.getText().toString(),
                                0,
                                0,
                                userID);




                        @SuppressLint("HandlerLeak") Handler handler = new Handler() {

                            @Override
                            public void handleMessage(@NonNull Message msg) {
                                switch (msg.what) {
                                    case UPDATE_TEXT:
                                        popupWindow.dismiss();
//                                        HabitShow();
                                        habitArrayList.add(addNewHabit);
                                        habitAdapter.notifyDataSetChanged();

                                        super.handleMessage(msg);
                                }

                            }


                        };


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DBConnectHabit dbConnectHabit = new DBConnectHabit();

                                Habit habit=new Habit(0,
                                        mAddThings.getText().toString(),
                                        0,
                                        0,
                                        userID);


                                dbConnectHabit.insert(habit);



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


//                        //Temp
//                        userID="191001";

//                        Log.i(TAG, "UserID has transferred to this activity! ");








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




        Context context=this;
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {

            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXT:

                        recyclerView = findViewById(R.id.habit_recycle_view);
                        // recyclerview的适配器
                        habitAdapter = new HabitAdapter(context, habitArrayList);
                        recyclerView.setLayoutManager(
                                new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        // 配置RecyclerView的分割线
                        recyclerView.addItemDecoration(
                                new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(habitAdapter);

//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                Toast.makeText(HabitMainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                Toast.makeText(HabitMainActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
//                //Remove swiped item from list and notify the RecyclerView
//                int position = viewHolder.getAdapterPosition();
//                habitArrayList.remove(position);
//                habitAdapter.notifyDataSetChanged();
//
//            }
//        };
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);


                    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                            final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                            if (direction == ItemTouchHelper.LEFT) {    //if swipe left

                                AlertDialog.Builder builder = new AlertDialog.Builder(HabitMainActivity.this); //alert for confirm to delete
                                builder.setMessage("Are you sure to delete?");    //set message

                                builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        habitAdapter.notifyItemRemoved(position);    //item removed from recylcerview
    //                            sqldatabase.execSQL("delete from " + TABLE_NAME + " where _id='" + (position + 1) + "'"); //query for delete
                                        int habitID = habitArrayList.get(position).getHabitID();
                                        new Thread(new Runnable() {
                                            @Override

                                            public void run() {
                                                DBConnectHabit dbConnectHabit = new DBConnectHabit();
                                                dbConnectHabit.delete(habitID);
                                            }


                                        }).start();

                                        habitArrayList.remove(position);  //then remove item

                                        return;
                                    }
                                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        habitAdapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                                        habitAdapter.notifyItemRangeChanged(position, habitAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                                        return;
                                    }
                                }).show();  //show alert dialog
                            }
                        }
                    };
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView); //set swipe to recylcerview
                    super.handleMessage(msg);
                }

            }


        };


        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectHabit dbConnectHabit = new DBConnectHabit();
                habitArrayList = dbConnectHabit.selectAll(userID);

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







}

