package com.example.timeowner.target;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.timeowner.R;
import com.example.timeowner.target.behavior.CalendarBehavior;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;


import java.util.Calendar;



public class TargetMainActivity extends Activity {

    private CalendarBehavior calendarBehavior;
    private int dayOfWeek;
    private int dayOfMonth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_main);
        /*
         *
         */
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
        title_month.setText(date.getMonth()+"月 周"+getWeekOfDate(date));
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

                }
            });
        }

        //点击事件
        TgCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Calendar calendar1 = date.getCalendar();
                calendarBehavior.setWeekOfMonth(calendar.get(Calendar.WEEK_OF_MONTH));
                title_month.setText(date.getMonth()+"月 周"+getWeekOfDate(date));

            }
        });
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
