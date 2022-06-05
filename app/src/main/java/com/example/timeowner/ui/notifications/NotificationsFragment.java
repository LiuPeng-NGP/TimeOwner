package com.example.timeowner.ui.notifications;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timeowner.databinding.FragmentNotificationsBinding;
import com.example.timeowner.dbconnect.DBConnectUser;
import com.example.timeowner.login.LoginActivity;
import com.example.timeowner.object.User;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private String mAccount = "-1";
    private String mName = "";
    private Bitmap mProfile;
    private String mEmail;
    private String mRecentChannel;
    private TextView mAccountText;
    private TextView mNameText;
    private TextView mExitLabel;
    private Button mTimeRecordButton ;
    private Button mChannelButton;
    private ImageView mProfileImage;
    private ActivityResultLauncher mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent resultData = result.getData();
                        mAccount = resultData.getStringExtra(LoginActivity.ACCOUNT_EXTRA_KEY);
                        if (mAccount != null) {
                            queryUser();
                            mAccountText.setText(mAccount);
                            mNameText.setText(mName);
//                            mProfileImage.setImageBitmap(mProfile);
                            mExitLabel.setVisibility(View.VISIBLE);
                            mChannelButton.setEnabled(true);
                            mTimeRecordButton.setEnabled(true);
                        }
                    }
                }
            });
    private static final int QUERY_USER = 1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);//通过ViewModelProvider实例化ViewModel
        //当页面需要ViewModel时，会向ViewModelProvider索要，而ViewModelProvider会去HashMap中检查该ViewModel是否已经存在缓存中，若存在，则直接返回，否则，则实例化一个

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mAccountText = binding.accountText;
        mNameText = binding.nameText;
        mExitLabel = binding.exitLabel;
        mTimeRecordButton = binding.timeRecord;
        mChannelButton = binding.channelRecord;
        mProfileImage = binding.profileImage;
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (mAccount.equals("-1")) {
                   Intent intent = new Intent(getActivity(), LoginActivity.class);
                   mLauncher.launch(intent);

               } else {
                   //修改头像
               }
            }
        });

        return root;


    }
    private void queryUser(){
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == QUERY_USER) {
                    User user = new User();
                    user = (User) msg.obj;
                    mName = user.getUserName();
                    mProfile = user.getUserPicture();
                    mEmail = user.getUserEmail();
                    mRecentChannel = user.getUserRecentChannel();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}