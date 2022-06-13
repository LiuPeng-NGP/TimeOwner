package com.example.timeowner.information;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectUser;
import com.example.timeowner.login.LoginActivity;
import com.example.timeowner.object.User;
import com.example.timeowner.ui.notifications.NotificationsFragment;

public class ChangeInformationActivity extends AppCompatActivity {

    private TextView mAccountText;
    private EditText mNameText;
    private TextView mEmailText;
    private Button mSaveButton;

    private String mAccount;
    private String mName;
    private String mEmail;
    private User mUser;

    private Intent mIntent;

    private static int QUERY_USER = 1;
    private static int UPDATE_USER = 2;
    public static String EMAIL_EXTRA = "my_email";

    private ActivityResultLauncher mChangeEmailLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                mEmail = data.getStringExtra(ChangeEmailActivity.NEW_EMAIL_EXTRA);
                mEmailText.setText(mEmail);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);
        mAccountText = findViewById(R.id.information_account_label);
        mNameText = findViewById(R.id.information_name_label);
        mEmailText = findViewById(R.id.information_email_label);
        mSaveButton = findViewById(R.id.save_information);
        mIntent = getIntent();

        mAccount = mIntent.getStringExtra(NotificationsFragment.ACCOUNT_EXTRA);
        mAccountText.setText(mAccount);
        queryUser();
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName = mNameText.getText().toString();
                mEmail = mEmailText.getText().toString();
                mUser.setUserEmail(mEmail);
                mUser.setUserName(mName);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBConnectUser dbConnectUser = new DBConnectUser();
                        dbConnectUser.update(mUser);
                    }
                }).start();
                Intent intent = new Intent();
                intent.putExtra(NotificationsFragment.NAME_EXTRA, mName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void queryUser(){
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == QUERY_USER) {
                    User user = new User();
                    user = (User) msg.obj;
                    mUser = user;
                    mName = user.getUserName();
                    mNameText.setText(mName);
                    mEmail = user.getUserEmail();
                    mEmailText.setText(mEmail);
                    mEmailText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(ChangeInformationActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage("邮箱与安全验证相关！");
                            //设置按钮：确定或取消
                            builder.setPositiveButton("更换绑定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent= new Intent(ChangeInformationActivity.this, ChangeEmailActivity.class);
                                    intent.putExtra(EMAIL_EXTRA, mEmail);
                                    mChangeEmailLauncher.launch(intent);
                                }
                            });
                            builder.setNegativeButton("取消",null);
                            builder.show();

                        }
                    });

                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectUser dbConnectUser = new DBConnectUser();
                User user = dbConnectUser.select(Integer.parseInt(mAccount));

                Message msg = new Message();
                msg.what = QUERY_USER;
                msg.obj = user;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}