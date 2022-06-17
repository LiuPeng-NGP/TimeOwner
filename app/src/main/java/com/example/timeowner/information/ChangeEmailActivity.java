package com.example.timeowner.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timeowner.R;
import com.example.timeowner.login.ForgetPasswordActivity;
import com.smailnet.emailkit.Draft;
import com.smailnet.emailkit.EmailKit;

import java.util.Random;

public class ChangeEmailActivity extends AppCompatActivity {

    public static String NEW_EMAIL_EXTRA = "new_email";
    private String emailPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

    private TextView mEmailText;
    private EditText mCaptchaEntry;
    private Button mGetCaptchaButton;
    private EditText mEmailEntry;
    private Button mConfirmButton;

    private String mEmail;
    private String mNewEmail;
    private String mCode;
    private String code_send = "";
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        mEmailText = findViewById(R.id.change_email_text);
        mCaptchaEntry = findViewById(R.id.change_captcha_entry);
        mGetCaptchaButton = findViewById(R.id.change_send_captcha);
        mEmailEntry = findViewById(R.id.change_email_new);
        mConfirmButton = findViewById(R.id.confirm_change);
        mIntent = getIntent();
        mEmail = mIntent.getStringExtra(ChangeInformationActivity.EMAIL_EXTRA);
        mEmailText.setText(mEmail);
        mGetCaptchaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailKit.initialize(ChangeEmailActivity.this);
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
                        .setSubject("Time Owner Verification Code")
                        .setText("【Time Owner】You are changing the email binding, verification code： " + code_send +".（Do not disclose the verification code to others!）");
                EmailKit.useSMTPService(config).send(draft, new EmailKit.GetSendCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ChangeEmailActivity.this, "The verification code has been sent, please check in your email!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String errMsg) {
                        Toast.makeText(ChangeEmailActivity.this, "Failed to send verification code!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewEmail = mEmailEntry.getText().toString();
                mCode = mCaptchaEntry.getText().toString();
                if (mCode == null || mCode.equals("")) {
                    Toast.makeText(ChangeEmailActivity.this, "verification code cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (!mCode.equals(code_send)) {
                    Toast.makeText(ChangeEmailActivity.this, "wrong verification code！", Toast.LENGTH_SHORT).show();
                } else if (mNewEmail == null || mNewEmail.equals("")) {
                    Toast.makeText(ChangeEmailActivity.this, "E-mail can not be empty！", Toast.LENGTH_SHORT).show();
                } else if (!mNewEmail.matches(emailPattern)) {
                    Toast.makeText(ChangeEmailActivity.this, "Email format is illegal！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(NEW_EMAIL_EXTRA, mNewEmail);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(ChangeEmailActivity.this, "Email binding change completed！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}