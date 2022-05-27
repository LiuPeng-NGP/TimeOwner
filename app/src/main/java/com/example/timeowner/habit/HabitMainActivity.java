package com.example.timeowner.habit;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.timeowner.R;

public class HabitMainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_main);
    }
}
