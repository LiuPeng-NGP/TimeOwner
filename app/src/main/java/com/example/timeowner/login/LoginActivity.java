package com.example.timeowner.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timeowner.R;

public class LoginActivity extends AppCompatActivity {
    private Button mLoginButton;
    private Button mRegisterButton;
    private TextView mForgetTextView;
    private EditText mAccountEntry;
    private EditText mPswEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mRegisterButton = (Button) findViewById(R.id.button_to_go_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}