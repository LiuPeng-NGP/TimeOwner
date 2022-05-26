package com.example.timeowner.coursetable;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alamkanak.weekview.WeekView;
import com.example.timeowner.R;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.IWeekView;

import java.util.List;

/**
 * 课程表主函数
 * @author jiangx
 */
public class CourseTableMainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursetable_main);

        WeekView mWeekView = (WeekView) findViewById(R.id.weekView);
        TimetableView mTimetableView = (TimetableView) findViewById(R.id.timetableView);

        //模拟获取课程数据：自定义格式
        List<MySubject> mySubjects = SubjectRepertory.loadDefaultSubjects();
        //设置数据源并显示
        mTimetableView.source(mySubjects)
                .curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener(){
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        //课表切换周次
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        //设置周次选择属性

    }
}
