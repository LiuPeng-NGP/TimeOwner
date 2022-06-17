package com.example.timeowner.target;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectHabit;
import com.example.timeowner.dbconnect.DBConnectTarget;
import com.example.timeowner.habit.HabitAdapter;
import com.example.timeowner.habit.HabitMainActivity;
import com.example.timeowner.object.Habit;
import com.example.timeowner.object.Target;
import com.example.timeowner.target.behavior.CalendarBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

//import org.threeten.bp.LocalDate;
//import org.threeten.bp.temporal.WeekFields;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 目标主函数
 */
public class TargetMainActivity extends AppCompatActivity {

    private CalendarBehavior calendarBehavior;
    private int dayOfWeek;
    private int dayOfMonth;
    private CalendarDay date1;
    private String userID;
    private String id = "191001";
    private FloatingActionButton mFloatingActionButton;
//    List<Target> list = new ArrayList<Target>();
    ArrayList<Target> targetArrayList = new ArrayList<Target>();
    public static final int UPDATE_TEXT = 1;


    private TargetAdapter targetAdapter;
    private RecyclerView recyclerView;


    Date startTime;
    Date endTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_main);

        initCalendarView();
        initRecyclerView();

        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("userID",null);


        id=userID;
        mFloatingActionButton=findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.target_add_things, null);

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


                EditText mAddThings = (EditText) popupView.findViewById(R.id.target_add_things_edit_text);
                Button mSureButton = (Button) popupView.findViewById(R.id.target_sure_button);
                Button mCancelButton = (Button) popupView.findViewById(R.id.target_cancel_button);
                Button mTargetStartTime=(Button)popupView.findViewById(R.id.target_start_time_button);
                Button mTargetEndTime=(Button)popupView.findViewById(R.id.target_start_time_button);
                CalendarView calendarView=(CalendarView)popupView.findViewById(R.id.calendarView1);
                TextView mStartTextView=(TextView)popupView.findViewById(R.id.target_start_time_text_view);
                TextView mEndTextView=(TextView)popupView.findViewById(R.id.target_end_time_text_view);




                //需要获取当前选中的日历时间显示到textview文本
                //还有往数据库存的字符串格式是什么，也转化一下吧

                //开始时间
                mTargetStartTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                        startTime=calendarView.();
//                        Log.i(TAG, "onClick: "+);





                    }
                });

                //结束时间
                mTargetEndTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });



                mSureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Habit addNewHabit =new Habit(0,
//                                mAddThings.getText().toString(),
//                                0,
//                                0,
//                                userID);
//
//
//
//
//                        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
//
//                            @Override
//                            public void handleMessage(@NonNull Message msg) {
//                                switch (msg.what) {
//                                    case UPDATE_TEXT:
//                                        popupWindow.dismiss();
////                                        HabitShow();
//                                        targetArrayList.add(addNewHabit);
//                                        targetAdapter.notifyDataSetChanged();
//
//                                        super.handleMessage(msg);
//                                }
//
//                            }
//
//
//                        };
//
//
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                DBConnectHabit dbConnectHabit = new DBConnectHabit();
//
//                                Habit target=new Habit(0,
//                                        mAddThings.getText().toString(),
//                                        0,
//                                        0,
//                                        userID);
//
//
//                                dbConnectHabit.insert(target);
//
//
//
//                                Message msg = new Message();
//                                msg.what = UPDATE_TEXT;
//                                handler.sendMessage(msg);
//                                try {
//                                    Thread.sleep(100);
//                                } catch (InterruptedException e) {
//                                    Thread.currentThread().interrupt();
//                                }
//                            }
//                        }).start();
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



    private void initCalendarView() {
        MaterialCalendarView TgCalendar = (MaterialCalendarView) findViewById(R.id.tg_calendar);
        TextView title_month = (TextView) findViewById(R.id.title_month);
        //获取behavior
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) TgCalendar.getLayoutParams()).getBehavior();
        if (behavior instanceof CalendarBehavior){
            calendarBehavior = (CalendarBehavior) behavior;
        }
        //初始 周模式
        //TgCalendar.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();

        TgCalendar.setTopbarVisible(false);//隐藏标题栏
        Calendar calendar = Calendar.getInstance();
        TgCalendar.setSelectedDate(calendar.getTime());//选中单日
        //title显示单日信息
        CalendarDay date = TgCalendar.getSelectedDate();
        date1 = date;
        title_month.setText(date.getMonth()+ 1 +"月 周"+getWeekOfDate(date));
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        //
        if(calendarBehavior != null){
            TgCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget,
                                           @NonNull CalendarDay calendarDay,
                                           boolean selected) {

                }
            });
            TgCalendar.setOnMonthChangedListener(new OnMonthChangedListener() {
                @Override
                public void onMonthChanged(MaterialCalendarView widget, CalendarDay calendarDay) {
                    if (calendarBehavior.getCalendarMode() == null) {
                        return;
                    }
                    if (calendarBehavior.getCalendarMode() == CalendarMode.WEEKS) {

                    } else {

                    }
                }
            });
        }

        //点击事件
        TgCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Calendar calendar1 = date.getCalendar();
                calendarBehavior.setWeekOfMonth(calendar.get(Calendar.WEEK_OF_MONTH));
                date1 = date;
                title_month.setText(date.getMonth()+ 1 +"月 周"+getWeekOfDate(date));
                //
            }
        });
    }

    private void initRecyclerView() {
//        RecyclerView rv = findViewById(R.id.Tg_recyclerview);
//        rv.setLayoutManager(new LinearLayoutManager(TargetMainActivity.this));
        //获取当日时间



        //New get data


        Context context=this;
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {

            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXT:

                        recyclerView = findViewById(R.id.Tg_recyclerview);
                        // recyclerview的适配器
                        targetAdapter = new TargetAdapter(context, targetArrayList);
                        recyclerView.setLayoutManager(
                                new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        // 配置RecyclerView的分割线
                        recyclerView.addItemDecoration(
                                new DividerItemDecoration(context,0));
                        recyclerView.setAdapter(targetAdapter);



                        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                            @Override
                            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                                return false;
                            }

                            @Override
                            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                                if (direction == ItemTouchHelper.LEFT) {    //if swipe left

                                    AlertDialog.Builder builder = new AlertDialog.Builder(TargetMainActivity.this); //alert for confirm to delete
                                    builder.setMessage("Are you sure to delete?");    //set message

                                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            targetAdapter.notifyItemRemoved(position);    //item removed from recylcerview
//                            sqldatabase.execSQL("delete from " + TABLE_NAME + " where _id='" + (position + 1) + "'"); //query for delete
                                            int targetID = targetArrayList.get(position).getTargetID();
                                            new Thread(new Runnable() {
                                                @Override

                                                public void run() {
                                                    DBConnectHabit dbConnectHabit = new DBConnectHabit();
                                                    dbConnectHabit.delete(targetID);
                                                }


                                            }).start();

                                            targetArrayList.remove(position);  //then remove item

                                            return;
                                        }
                                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            targetAdapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                                            targetAdapter.notifyItemRangeChanged(position, targetAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                                            return;
                                        }
                                    }).show();  //show alert dialog
                                }
                            }
                        };
                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                        itemTouchHelper.attachToRecyclerView(recyclerView); //set swipe to recylcerview

//                recyclerView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//                    @Override
//                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                        menu.add("change");
////                        SpannableString spanString = new SpannableString(menu.getItem(0).getTitle().toString());
////                        int end = spanString.length();
////                        spanString.setSpan(new RelativeSizeSpan(0.75f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////
////                        menu.getItem(0).setTitle(spanString);
//
//                        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//
//
//
//
//
//
//                                return true;
//                            }
//                        });
//                    }
//                });


                        super.handleMessage(msg);

                }
            }


        };


        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectTarget dbConnectTarget = new DBConnectTarget();
                targetArrayList = dbConnectTarget.selectAll(userID);
//                Collections.sort(targetArrayList,(habit1,habit2)->Integer.compare(habit1.getHabitTodayIsCompleted(), habit2.getHabitTodayIsCompleted()));

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






        //原来对list操作的部分
//        //获取数据源
//        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                if (msg.what == UPDATE_TEXT){
//                    list = (List<Target>) msg.obj;
//
//                    List<Map<String,Object>> list1;
//                    list1 = new ArrayList<Map<String,Object>>();
//                    Map<String,Object> map;
//                    for (int i = 0; i < list.size();i++){
//                        String temp1 = list.get(i).getTargetName();
//                        String temp2 = list.get(i).getTargetStartTime();
//                        String temp3 = list.get(i).getTargetEndTime();
//                        int temp4 = list.get(i).getTargetIsCompleted();
//                        map = new HashMap<String,Object>();
//                        map.put("TargetName",temp1);
//                        map.put("Start",temp2);
//                        map.put("End",temp3);
//                        map.put("Is",temp4);
//                        list1.add(map);
//                    }
//
//                    rv.setAdapter(new TargetListAdapter(TargetMainActivity.this,list1,date1));
//                    RecyclerView.ItemDecoration itemDecoration =
//                            new DividerItemDecoration(TargetMainActivity.this, DividerItemDecoration.VERTICAL);
//                    rv.addItemDecoration(itemDecoration);
//                }
//            }
//        };
//
//        //操作部分
////        String[] names = getResources().getStringArray(R.array.target_items);
////        List<String> mList = new ArrayList<>();
////        Collections.addAll(mList, names);
////        rv.setAdapter(new TargetListAdapter(this,list));
////        RecyclerView.ItemDecoration itemDecoration =
////                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
////        rv.addItemDecoration(itemDecoration);
//
//        //线程获取数据
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                DBConnectTarget dbConnectTarget = new DBConnectTarget();
//                list = dbConnectTarget.selectAll(id);
//
//                Message msg = new Message();
//                msg.what = UPDATE_TEXT;
//                msg.obj = list;
//                handler.sendMessage(msg);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//
//            }
//        }).start();
// }

    //得到星期
    public static String getWeekOfDate(CalendarDay date){
        String[] weekDays = {"日","一","二","三","四","五","六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date.getDate());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    //Date 转 LocalDate

}
