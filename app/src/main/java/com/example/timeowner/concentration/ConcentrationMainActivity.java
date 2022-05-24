package com.example.timeowner.concentration;


import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;

import com.example.timeowner.R;

public class ConcentrationMainActivity extends Activity {
    private Chronometer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concentration_main);
        // 获得计时器对象
        timer = (Chronometer) this.findViewById(R.id.chronometer);
        //长按计时器时，出现上下文菜单
        this.registerForContextMenu(timer);
    }
    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        // ContextMenu的Item不支持Icon，所以不用再资源文件中，为它们设定图标
        if (v.getId() == R.id.chronometer)
        {
            //加载xml菜单布局文件
            this.getMenuInflater().inflate(R.menu.context_menu, menu);
            // 设定头部图标
            menu.setHeaderIcon(R.drawable.ic_launcher_background);
            // 设定头部标题
            menu.setHeaderTitle(" 计时器控制选项 ");
        }
    }
    //选择菜单项后的响应
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.timer_start:
                // 将计时器清零
                timer.setBase(SystemClock.elapsedRealtime());
                //开始计时
                timer.start();
                break;
            case R.id.timer_stop:
                //停止计时
                timer.stop();
                break;
            case R.id.timer_reset:
                //将计时器清零
                timer.setBase(SystemClock.elapsedRealtime());
                break;
        }
        return super.onContextItemSelected(item);
    }

}