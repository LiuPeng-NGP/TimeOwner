package com.example.timeowner.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectUser;
import com.example.timeowner.object.User;

import java.io.InputStream;


public class RegisterActivity extends AppCompatActivity {
    private EditText mNameEntry;
    private EditText mEmailEntry;
    private EditText mPswEntry;
    private EditText mPswConfirmEntry;
    private EditText mCaptchaEntry;
    private Button mRegisterButton;
    private Button mReturnLoginButton;
    private VerifyCaptcha mCaptcha;

    private String emailPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
    private static final int REGISTER_USER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mNameEntry = findViewById(R.id.re_name_entry);
        mEmailEntry = findViewById(R.id.re_email_entry);
        mPswEntry = findViewById(R.id.register_psw_entry);
        mPswConfirmEntry = findViewById(R.id.confirm_psw_entry);
        mCaptchaEntry = findViewById(R.id.captcha_entry);
        mCaptcha = findViewById(R.id.captcha_image);

        mRegisterButton = findViewById(R.id.button_to_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameEntry.getText().toString();
                String email = mEmailEntry.getText().toString();
                String psw = mPswEntry.getText().toString();
                String pswConfirm = mPswConfirmEntry.getText().toString();
                String captcha = mCaptchaEntry.getText().toString();
                registerUser(name, email, psw, pswConfirm, captcha);
            }
        });

        mReturnLoginButton = findViewById(R.id.button_to_return);
        mReturnLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });

    }
    private void registerUser(String name, String email, String psw, String pswConfirm, String captcha) {
        if (name == null || name.equals("")) {
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (name.length() > 30) {
            Toast.makeText(this, "用户名长度不能超过30！", Toast.LENGTH_SHORT).show();
        } else if (email == null || email.equals("")) {
            Toast.makeText(this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(this, "邮箱格式不合法！", Toast.LENGTH_SHORT).show();
        }else if (psw == null || psw.equals("")) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (psw.length() > 30) {
            Toast.makeText(this, "密码长度不能超过30！", Toast.LENGTH_SHORT).show();
        } else if (!psw.equals(pswConfirm)) {
            Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
        } else if (mCaptcha==null || mCaptcha.equals("")) {
            Toast.makeText(this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
        }else if (!mCaptcha.isEquals(captcha)) {
            Toast.makeText(this, "验证码不正确！", Toast.LENGTH_SHORT).show();
            mCaptcha.refresh();
        } else {
            User user = new User();
            user.setUserID("9999");
            user.setUserName(name);
            user.setUserEmail(email);
            user.setUserPassword(psw);
            Resources r = RegisterActivity.this.getResources();
            @SuppressLint("ResourceType")
            InputStream is = r.openRawResource(R.drawable.robot);
            BitmapDrawable bmpDraw = new BitmapDrawable(is);
            Bitmap bmp = bmpDraw.getBitmap();
            user.setUserPicture(bmp);
            @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if (msg.what == REGISTER_USER) {
                        Intent resultData = new Intent();
                        resultData.putExtra(LoginActivity.ACCOUNT_EXTRA_KEY, user.getUserID());
                        setResult(RESULT_OK, resultData);
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        super.handleMessage(msg);
                        finish();
                    }
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DBConnectUser dbConnectUser = new DBConnectUser();
                    dbConnectUser.insert(user);
                    Message msg = new Message();
                    msg.what = REGISTER_USER;
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

}