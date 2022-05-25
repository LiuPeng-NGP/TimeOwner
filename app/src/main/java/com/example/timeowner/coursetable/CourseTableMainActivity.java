package com.example.timeowner.coursetable;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.view.WeekView;
import com.example.timeowner.R;

import java.util.List;

/**
 * 课程表主函数
 * @author jiangx
 */

public class CourseTableMainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concentration_main);

        WeekView mWeekView = (WeekView) findViewById(R.id.weekView);
        TimetableView mTimetableView = (TimetableView) findViewById(R.id.mTimetableView);

        //模拟获取课程数据：自定义格式
        List<MySubject> mySubjects = SubjectRepertory.loadDefaultSubjects();
        //设置数据源并显示
        mTimetableView.source(mySubjects)
                .curWeek(1)
                .showView();
    }
}
