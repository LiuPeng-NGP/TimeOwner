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
    private EditText mAccountEntry;
    private EditText mNameEntry;
    private EditText mEmailEntry;
    private EditText mPswEntry;
    private EditText mPswConfirmEntry;
    private EditText mCaptchaEntry;
    private Button mRegisterButton;
    private Button mReturnLoginButton;
    private VerifyCaptcha mCaptcha;


    private String mAccount = "";
    private String mName = "";
    private String mEmail = "";
    private String mPsw = "";
    private String mPswConfirm = "";
    private String mCaptchaInput = "";


    private String emailPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
    private String accountPattern = "^\\d{1,10}$";


    private static final int REGISTER_USER = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAccountEntry = findViewById(R.id.re_account_entry);
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
                mAccount = mAccountEntry.getText().toString();
                mName = mNameEntry.getText().toString();
                mEmail = mEmailEntry.getText().toString();
                mPsw = mPswEntry.getText().toString();
                mPswConfirm = mPswConfirmEntry.getText().toString();
                mCaptchaInput = mCaptchaEntry.getText().toString();
                registerUser();
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
    private void registerUser() {
        if (mAccount == null || mAccount.equals("")) {
            Toast.makeText(this, "账号不能为空！", Toast.LENGTH_SHORT).show();
        } else if (!mAccount.matches(accountPattern)) {
            Toast.makeText(this, "账号格式不合法，请输入1-10位数字！", Toast.LENGTH_SHORT).show();
        } else if (mName == null || mName.equals("")) {
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (mName.length() > 30) {
            Toast.makeText(this, "用户名长度不能超过30！", Toast.LENGTH_SHORT).show();
        } else if (mEmail == null || mEmail.equals("")) {
            Toast.makeText(this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
        } else if (!mEmail.matches(emailPattern)) {
            Toast.makeText(this, "邮箱格式不合法！", Toast.LENGTH_SHORT).show();
        } else if (mPsw == null || mPsw.equals("")) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (mPsw.length() > 30) {
            Toast.makeText(this, "密码长度不能超过30！", Toast.LENGTH_SHORT).show();
        } else if (!mPsw.equals(mPswConfirm)) {
            Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
        } else if (mCaptchaInput == null || mCaptchaInput.equals("")) {
            Toast.makeText(this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (!mCaptcha.isEquals(mCaptchaInput)) {
            Toast.makeText(this, "验证码不正确！", Toast.LENGTH_SHORT).show();
            mCaptcha.refresh();
        } else {
            insertUser();
        }
    }
    private void insertUser() {
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == REGISTER_USER) {
                    User user = new User();
                    user = (User) msg.obj;
                    if (user.getUserID() == null) {
                        User newUser = new User();
                        newUser.setUserID(mAccount);
                        newUser.setUserName(mName);
                        newUser.setUserEmail(mEmail);
                        newUser.setUserPassword(mPsw);
                        //设置默认头像
                        Resources r = RegisterActivity.this.getResources();
                        @SuppressLint("ResourceType")
                        InputStream is = r.openRawResource(R.drawable.robot);
                        BitmapDrawable bmpDraw = new BitmapDrawable(is);
                        Bitmap bmp = bmpDraw.getBitmap();
                        newUser.setUserPicture(bmp);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DBConnectUser dbConnectUser = new DBConnectUser();
                                dbConnectUser.insert(newUser);
                            }
                        }).start();

                        Intent resultData = new Intent();
                        resultData.putExtra(LoginActivity.ACCOUNT_EXTRA_KEY, newUser.getUserID());
                        setResult(RESULT_OK, resultData);
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "该账号已存在！", Toast.LENGTH_SHORT).show();
                    }
                    super.handleMessage(msg);
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectUser dbConnectUser = new DBConnectUser();
                User user = dbConnectUser.select(Integer.parseInt(mAccount));
                Message msg = Message.obtain();
                msg.what = REGISTER_USER;
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