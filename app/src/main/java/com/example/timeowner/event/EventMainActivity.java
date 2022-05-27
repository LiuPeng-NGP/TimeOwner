package com.example.timeowner.event;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.timeowner.R;

public class EventMainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_main);
    }
}
