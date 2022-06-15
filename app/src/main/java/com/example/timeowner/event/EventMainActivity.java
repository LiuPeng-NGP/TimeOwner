package com.example.timeowner.event;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectEvent;
import com.example.timeowner.object.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EventMainActivity extends Activity {

    private FloatingActionButton mFloatingActionButton;
    private RecyclerView recyclerView;
    private static final int TEST_USER_SELECT = 1;
    private static final int UPDATE_TEXT = 1;
    ArrayList<Event> eventArrayList = new ArrayList<Event>();
    private String userID;
    private EventAdapter eventAdapter;
    private int mWhereIfCompletedPartition;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_main);
        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("userID",null);


        EventShow();




        mFloatingActionButton=findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.event_add_things, null);

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


                EditText mAddThings = (EditText) popupView.findViewById(R.id.event_add_things_edit_text);
                Button mSureButton = (Button) popupView.findViewById(R.id.event_sure_button);
                Button mCancelButton = (Button) popupView.findViewById(R.id.event_cancel_button);
                mSureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Event addNewEvent =new Event(0,
                                mAddThings.getText().toString(),
                                userID);
                        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                            @Override
                            public void handleMessage(@NonNull Message msg) {
                                switch (msg.what) {
                                    case UPDATE_TEXT:
                                        popupWindow.dismiss();
//                                        EventShow();
                                        eventArrayList.add(addNewEvent);
                                        eventAdapter.notifyDataSetChanged();
                                        super.handleMessage(msg);
                                }
                            }
                        };
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DBConnectEvent dbConnectEvent = new DBConnectEvent();

                                Event event=new Event(0,
                                        mAddThings.getText().toString(),
                                        userID);
                                dbConnectEvent.insert(event);
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
            }
        });
    }
    private void EventShow(){
        Context context=this;
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {

            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXT:

                        recyclerView = findViewById(R.id.event_recycle_view);
                        // recyclerview的适配器
                        eventAdapter = new EventAdapter(context, eventArrayList);
                        recyclerView.setLayoutManager(
                                new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        // 配置RecyclerView的分割线
                        recyclerView.addItemDecoration(
                                new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(eventAdapter);



                        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                            @Override
                            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                                return false;
                            }

                            @Override
                            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                                if (direction == ItemTouchHelper.LEFT) {    //if swipe left

                                    AlertDialog.Builder builder = new AlertDialog.Builder(com.example.timeowner.event.EventMainActivity.this); //alert for confirm to delete
                                    builder.setMessage("Are you sure to complete?");    //set message

                                    builder.setPositiveButton("COMPLETE", new DialogInterface.OnClickListener() { //when click on DELETE
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            eventAdapter.notifyItemRemoved(position);    //item removed from recylcerview
//                            sqldatabase.execSQL("delete from " + TABLE_NAME + " where _id='" + (position + 1) + "'"); //query for delete
                                            int eventID = eventArrayList.get(position).getEventID();
                                            new Thread(new Runnable() {
                                                @Override

                                                public void run() {
                                                    DBConnectEvent dbConnectEvent = new DBConnectEvent();
                                                    dbConnectEvent.delete(eventID);
                                                }


                                            }).start();

                                            eventArrayList.remove(position);  //then remove item

                                            return;
                                        }
                                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            eventAdapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                                            eventAdapter.notifyItemRangeChanged(position, eventAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
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
                DBConnectEvent dbConnectEvent = new DBConnectEvent();
                eventArrayList = dbConnectEvent.selectAll(userID);

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

