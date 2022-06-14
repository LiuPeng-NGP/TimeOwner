package com.example.timeowner.habit;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;

import com.example.timeowner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HabitMainActivity extends Activity {

    private FloatingActionButton mFloatingActionButton;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_main);


        mFloatingActionButton=findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_things, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.update();
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);


                EditText mAddThings = (EditText) popupView.findViewById(R.id.habit_add_things_edit_text);
                Button mSureButton = (Button) popupView.findViewById(R.id.habit_sure_button);
                Button mCancelButton = (Button) popupView.findViewById(R.id.habit_cancel_button);


                mSureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    popupWindow.dismiss();
                    }
                });

                mCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    popupWindow.dismiss();
                    }
                });


            }
        });


    }
}
