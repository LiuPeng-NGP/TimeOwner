package com.example.timeowner.concentration;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timeowner.R;

public class ConcentrationMainActivity extends AppCompatActivity implements View.OnClickListener,Chronometer.OnChronometerTickListener{

    private Chronometer chronometer;
    private Button start,stop,reset,format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concentration_main);
        initView();
    }

    private void initView() {
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        start = (Button) findViewById(R.id.Start);
        stop = (Button) findViewById(R.id.Stop);
        reset = (Button) findViewById(R.id.Reset);
        format = (Button) findViewById(R.id.format);

        chronometer.setOnChronometerTickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        reset.setOnClickListener(this);
        format.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Start:
                chronometer.start();// 开始计时
                break;
            case R.id.Stop:
                chronometer.stop();// 停止计时
                break;
            case R.id.Reset:
                chronometer.setBase(SystemClock.elapsedRealtime());// 复位
                break;
            case R.id.format:
                chronometer.setFormat("T：%s");// 更改时间显示格式
                break;
        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        String time = chronometer.getText().toString();
        if(time.equals("00:00")){
            Toast.makeText(ConcentrationMainActivity.this,"时间到了~", Toast.LENGTH_SHORT).show();
        }
    }
}