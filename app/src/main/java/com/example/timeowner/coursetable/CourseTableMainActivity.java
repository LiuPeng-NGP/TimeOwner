package com.example.timeowner.coursetable;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alamkanak.weekview.WeekView;
import com.example.timeowner.R;

public class CourseTableMainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concentration_main);

        WeekView mWeekView = (WeekView) findViewById(R.id.weekView);
    }
}
