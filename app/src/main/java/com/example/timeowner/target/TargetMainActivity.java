package com.example.timeowner.target;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.timeowner.R;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;


public class TargetMainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_main);
        /*
         *
         */
        MaterialCalendarView TgCalendar = (MaterialCalendarView) findViewById(R.id.tg_calendar);
        //初始 周模式
        TgCalendar.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
        TgCalendar.setTopbarVisible(false);//隐藏标题栏
        Calendar calendar = Calendar.getInstance();
        TgCalendar.setSelectedDate(calendar.getTime());//选中单日

    }
}
