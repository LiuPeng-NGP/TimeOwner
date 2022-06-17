package com.example.timeowner.concentration;



import static android.content.ContentValues.TAG;
import static com.example.timeowner.target.TargetMainActivity.UPDATE_TEXT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectConcentration;
import com.example.timeowner.dbconnect.DBConnectHappiness;
import com.example.timeowner.object.Concentration;

public class ConcentrationMainActivity extends Activity {

    TextView timerTextView;
    long startTime = 0;
    double mMinutesTotal;


    private String userID;
    TextView mTextViewTotalTime;
    int minutes;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {


        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concentration_main);


        mTextViewTotalTime=(TextView) findViewById(R.id.total_concentration_time_text_view);

        getTotalHours();

        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("userID",null);



        timerTextView = (TextView) findViewById(R.id.timerTextView);

        Button b = (Button) findViewById(R.id.concentration_button);
        b.setText("START");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("STOP")) {
                    timerHandler.removeCallbacks(timerRunnable);



                    @SuppressLint("HandlerLeak") Handler myHandler = new Handler(){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            switch (msg.what){
                                case UPDATE_TEXT:
//                        Log.i(TAG, "handleMessage: "+String.valueOf(Math.round(mMinutesTotal/60)));
                                    String roundOff ="Total: "+Double.toString( (double) Math.round(mMinutesTotal/60 * 10) / 10)+" hours";
//                        mTextViewTotalTime.setText(Double.toString(roundOff));
//                        Log.i(TAG, "handleMessage: "+roundOff);

                                    mTextViewTotalTime.setText(roundOff);
                                    super.handleMessage(msg);

                            }

                        }
                    };

                    new Thread(new Runnable() {

                        @Override
                        public void run() {


                            DBConnectConcentration dbConnectConcentration = new DBConnectConcentration();


                            Concentration concentration=new Concentration(0,minutes,userID);

                            dbConnectConcentration.insert(concentration);

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








                    b.setText("START");
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);

                    b.setText("STOP");
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button)findViewById(R.id.concentration_button);
        b.setText("START");
    }

    public void getTotalHours(){


        @SuppressLint("HandlerLeak") Handler myHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case UPDATE_TEXT:
//                        Log.i(TAG, "handleMessage: "+String.valueOf(Math.round(mMinutesTotal/60)));
                        String roundOff ="Total: "+Double.toString( (double) Math.round(mMinutesTotal/60 * 10) / 10)+" hours";
//                        mTextViewTotalTime.setText(Double.toString(roundOff));
//                        Log.i(TAG, "handleMessage: "+roundOff);

                        mTextViewTotalTime.setText(roundOff);
                        super.handleMessage(msg);

                }

            }
        };

        new Thread(new Runnable() {

            @Override
            public void run() {


                DBConnectConcentration dbConnectConcentration = new DBConnectConcentration();

                mMinutesTotal =dbConnectConcentration.selectSum(userID);

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