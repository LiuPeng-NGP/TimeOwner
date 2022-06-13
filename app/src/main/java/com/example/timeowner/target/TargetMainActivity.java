package com.example.timeowner.target;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectTarget;
import com.example.timeowner.object.Target;
import com.example.timeowner.target.behavior.CalendarBehavior;
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
    private String id = "191001";

    List<Target> list = new ArrayList<Target>();
    public static final int UPDATE_TEXT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_main);

        initCalendarView();
        initRecyclerView();
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
        TgCalendar.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();

        TgCalendar.setTopbarVisible(false);//隐藏标题栏
        Calendar calendar = Calendar.getInstance();
        TgCalendar.setSelectedDate(calendar.getTime());//选中单日
        //title显示单日信息
        CalendarDay date = TgCalendar.getSelectedDate();
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

            }
        });
    }

    private void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.Tg_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(TargetMainActivity.this));
        //获取当日时间

        //获取数据源
        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == UPDATE_TEXT){
                    list = (List<Target>) msg.obj;

                    List<Map<String,Object>> list1;
                    list1 = new ArrayList<Map<String,Object>>();
                    Map<String,Object> map;
                    for (int i = 0; i < list.size();i++){
                        String temp1 = list.get(i).getTargetName();
                        String temp2 = list.get(i).getTargetStartTime();
                        String temp3 = list.get(i).getTargetEndTime();
                        int temp4 = list.get(i).getTargetIsCompleted();
                        map = new HashMap<String,Object>();
                        map.put("TargetName",temp1);
                        map.put("Start",temp2);
                        map.put("End",temp3);
                        map.put("Is",temp4);
                        list1.add(map);
                    }
                    //适配器部分
                    List<String> list2;
                    list2 = new ArrayList<>();
                    for (int i = 0; i < list1.size(); i++){
                        Map<String,Object> map1 = list1.get(i);
                    }
                    rv.setAdapter(new TargetListAdapter(TargetMainActivity.this,list1,date1));
                    RecyclerView.ItemDecoration itemDecoration =
                            new DividerItemDecoration(TargetMainActivity.this, DividerItemDecoration.VERTICAL);
                    rv.addItemDecoration(itemDecoration);
                }
            }
        };

        //操作部分
//        String[] names = getResources().getStringArray(R.array.target_items);
//        List<String> mList = new ArrayList<>();
//        Collections.addAll(mList, names);
//        rv.setAdapter(new TargetListAdapter(this,list));
//        RecyclerView.ItemDecoration itemDecoration =
//                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        rv.addItemDecoration(itemDecoration);

        //线程获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectTarget dbConnectTarget = new DBConnectTarget();
                list = dbConnectTarget.selectAll(id);

                Message msg = new Message();
                msg.what = UPDATE_TEXT;
                msg.obj = list;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }
        }).start();
    }

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
