package com.example.timeowner.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectUser;
import com.example.timeowner.object.User;
import com.smailnet.emailkit.Draft;
import com.smailnet.emailkit.EmailKit;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText mAccountEntry;
    private EditText mCodeEntry;
    private EditText mPswEntry;
    private EditText mPswConfirmEntry;
    private Button mGetResetCodeButton;
    private Button mSubmitButton;
    private Button mReturnLoginButton;


    private String mAccount;
    private String mEmail;
    private String mCode;
    private String mPsw;
    private String mPswConfirm;
    private String code_send = "";

    private static final int FIND_ACCOUNT = 1;
    private static final int NOT_FIND_ACCOUNT = 2;
    private static final int UPDATE_USER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mAccountEntry = findViewById(R.id.forget_account_entry);
        mCodeEntry = findViewById(R.id.code_entry);
        mPswEntry = findViewById(R.id.reset_psw_entry);
        mPswConfirmEntry = findViewById(R.id.reset_psw_confirm_entry);

        mGetResetCodeButton = findViewById(R.id.button_to_get_reset_code);
        mGetResetCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAccount = mAccountEntry.getText().toString();
                if (mAccount == null || mAccount.equals("")) {
                    Toast.makeText(ForgetPasswordActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    queryUser();
                }
            }
        });
        mSubmitButton = findViewById(R.id.button_to_submit) ;
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCode = mCodeEntry.getText().toString();
                mPsw = mPswEntry.getText().toString();
                mPswConfirm = mPswConfirmEntry.getText().toString();
                if (mCode == null || mCode.equals("")) {
                    Toast.makeText(ForgetPasswordActivity.this, "重置码不能为空！", Toast.LENGTH_SHORT).show();
                } else if (!mCode.equals(code_send)) {
                    Toast.makeText(ForgetPasswordActivity.this, "重置码错误！", Toast.LENGTH_SHORT).show();
                } else if (mPsw == null || mPsw.equals("")) {
                    Toast.makeText(ForgetPasswordActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                } else if (mPsw.length() > 30) {
                    Toast.makeText(ForgetPasswordActivity.this, "密码长度不能大于30！", Toast.LENGTH_SHORT).show();
                } else if (!mPswConfirm.equals(mPsw)) {
                    Toast.makeText(ForgetPasswordActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                } else {
                    updateUser();
                }
            }
        });
        mReturnLoginButton = findViewById(R.id.button_to_return_login);
        mReturnLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });

    }

    private void queryUser() {
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == FIND_ACCOUNT) {
                    User user = new User();
                    user =(User) msg.obj;
                    mEmail = user.getUserEmail();
                    EmailKit.initialize(ForgetPasswordActivity.this);
                    EmailKit.Config config = new EmailKit.Config()
                            .setMailType(EmailKit.MailType.$126)
                            .setAccount("time_owner@126.com")
                            .setPassword("MMWDPNYSWLPXSIIM");
                    EmailKit.auth(config, new EmailKit.GetAuthCallback() {
                        @Override
                        public void onSuccess() {
                            Log.i("EMAIL", "配置成功!");
                        }

                        @Override
                        public void onFailure(String errMsg) {
                            Log.i("EMAIL", errMsg);
                        }
                    });
                    Random random= new Random();
                    for (int i = 0; i < 6; i++) {
                        code_send += String.valueOf(random.nextInt(10));
                    }

                    Draft draft = new Draft()
                            .setNickname("Time owner")
                            .setTo(mEmail)
                            .setSubject("光阴记密码重置")
                            .setText("【光阴记】您正在进行找回密码操作，重置码： " + code_send +"。（重置码告知他人将导致账号被盗，请勿泄露）");
                    EmailKit.useSMTPService(config).send(draft, new EmailKit.GetSendCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ForgetPasswordActivity.this, "重置码已发送，请在绑定邮箱中查看！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String errMsg) {
                            Toast.makeText(ForgetPasswordActivity.this, "重置码发送失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    super.handleMessage(msg);
                } else if (msg.what == NOT_FIND_ACCOUNT) {
                    Toast.makeText(ForgetPasswordActivity.this, "账号不存在！", Toast.LENGTH_SHORT).show();
                    super.handleMessage(msg);
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectUser dbConnectUser = new DBConnectUser();
                User user = dbConnectUser.select(Integer.parseInt(mAccount));
                Message msg = new Message();
                if (user.getUserID() == null) {
                    msg.what = NOT_FIND_ACCOUNT;
                } else {
                    msg.what = FIND_ACCOUNT;
                    msg.obj = user;
                }
                handler.sendMessage(msg);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateUser() {
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == UPDATE_USER) {
                    Intent resultData = new Intent();
                    resultData.putExtra(LoginActivity.ACCOUNT_EXTRA_KEY, mAccount);
                    setResult(RESULT_OK, resultData);
                    Toast.makeText(ForgetPasswordActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                    super.handleMessage(msg);
                    finish();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectUser dbConnectUser = new DBConnectUser();
                User user = dbConnectUser.select(Integer.parseInt(mAccount));
                user.setUserPassword(mPsw);
                dbConnectUser.update(user);
                Message msg = new Message();
                msg.what = UPDATE_USER;
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