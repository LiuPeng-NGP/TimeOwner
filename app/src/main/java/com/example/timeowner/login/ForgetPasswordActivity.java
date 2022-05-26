package com.example.timeowner.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.timeowner.R;
import com.smailnet.emailkit.Draft;
import com.smailnet.emailkit.EmailKit;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button mGetResetCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mGetResetCodeButton = findViewById(R.id.button_to_get_reset_code);
        mGetResetCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                Draft draft = new Draft()
                        .setNickname("Time owner")
                        .setTo("806438912@qq.com")
                        .setSubject("这是一封测试邮件")
                        .setText("Hello World");
                EmailKit.useSMTPService(config).send(draft, new EmailKit.GetSendCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("EMAIL", "发送成功");
                    }

                    @Override
                    public void onFailure(String errMsg) {
                        Log.i("EMAIL", "发送失败，错误" + errMsg);
                    }
                });
            }
        });

    }
}