package com.example.timeowner.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectUser;
import com.example.timeowner.object.User;

public class LoginActivity extends AppCompatActivity {
    private Button mLoginButton;
    private Button mRegisterButton;
    private TextView mForgetTextView;
    private EditText mAccountEntry;
    private EditText mPswEntry;

    private String mAccount;

    public static final String ACCOUNT_EXTRA_KEY = "login_account";
    private static final int USER_LOGIN = 1;

    private ActivityResultLauncher mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent resultData = result.getData();
                        mAccount = resultData.getStringExtra(ACCOUNT_EXTRA_KEY);
                        if (mAccount != null) {
                            mAccountEntry.setText(mAccount);
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAccountEntry = findViewById(R.id.login_account_entry);
        mPswEntry = findViewById(R.id.login_psw_entry);
        //登录
        mLoginButton = findViewById(R.id.button_to_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = mAccountEntry.getText().toString();
                String psw = mPswEntry.getText().toString();
                if (account == null || account.equals("")) {
                    Toast.makeText(LoginActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    queryUser(account, psw);
                }

            }
        });
        mRegisterButton = findViewById(R.id.button_to_go_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                mLauncher.launch(intent);
            }
        });
        mForgetTextView = findViewById(R.id.forget_psw);
        mForgetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                mLauncher.launch(intent);
            }
        });
    }
    private void queryUser(String account, String psw) {
        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == USER_LOGIN) {
                    User user = new User();
                    user = (User) msg.obj;
                    if (user.getUserID() == null) {
                        Toast.makeText(LoginActivity.this, "账号不存在！", Toast.LENGTH_SHORT).show();
                        super.handleMessage(msg);
                    } else {
                        if (psw == null || psw.equals("")) {
                            Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                            super.handleMessage(msg);
                        } else if (!psw.equals(user.getUserPassword())) {
                            Toast.makeText(LoginActivity.this, "密码错误！密码为 " + user.getUserPassword(), Toast.LENGTH_SHORT).show();
                            super.handleMessage(msg);
                        } else {
                            Intent resultData =new Intent();
                            resultData.putExtra(ACCOUNT_EXTRA_KEY, account);
                            setResult(RESULT_OK,resultData);
                            Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                            super.handleMessage(msg);
                            finish();
                        }
                    }

                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectUser dbConnectUser = new DBConnectUser();
                User user = dbConnectUser.select(Integer.parseInt(account));
                Message msg = Message.obtain();
                msg.what = USER_LOGIN;
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