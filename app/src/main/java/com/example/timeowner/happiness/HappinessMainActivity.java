package com.example.timeowner.happiness;

import static com.example.timeowner.target.TargetMainActivity.UPDATE_TEXT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectHabit;
import com.example.timeowner.dbconnect.DBConnectHappiness;
import com.example.timeowner.dbconnect.DBConnectUser;
import com.example.timeowner.object.Happiness;
import com.example.timeowner.object.User;

import java.util.Random;

public class HappinessMainActivity extends Activity {
    private TextView mTextView;
    private static Happiness sHappiness;
    private int number;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.happiness_main);


       mTextView=findViewById(R.id.textView);
       
    number=(int)(Math.random() * 122);

        @SuppressLint("HandlerLeak") Handler myHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case UPDATE_TEXT:
                        mTextView.setText(sHappiness.getHappinessText());

                        super.handleMessage(msg);

                }

            }
        };

        new Thread(new Runnable() {

            @Override
            public void run() {


                DBConnectHappiness dbConnectHappiness = new DBConnectHappiness();

                sHappiness =dbConnectHappiness.select(number);

                Message msg=new Message();
                msg.what =UPDATE_TEXT;
                myHandler.sendMessage(msg);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }


            }
        }).start();

    }
}
