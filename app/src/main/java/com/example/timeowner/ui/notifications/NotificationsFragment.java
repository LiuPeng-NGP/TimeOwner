package com.example.timeowner.ui.notifications;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timeowner.R;
import com.example.timeowner.databinding.FragmentNotificationsBinding;
import com.example.timeowner.dbconnect.DBConnectUser;
import com.example.timeowner.login.LoginActivity;
import com.example.timeowner.object.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    //信息变量
    private String mAccount = "-1";
    private String mName = "";
    private Bitmap mProfile;
    private String mEmail;
    private String mRecentChannel;
    private User mUser = new User();

    //布局控件
    private TextView mAccountText;
    private TextView mNameText;
    private TextView mExitLabel;
    private Button mTimeRecordButton ;
    private Button mChannelButton;
    private Button mChangeInfoButton;
    private ImageView mProfileImage;
    private BottomSheetDialog mBottomSheetDialog;
    private View mBottomView;


    //登录注册器
    private ActivityResultLauncher mRegisterLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent resultData = result.getData();
                        mAccount = resultData.getStringExtra(LoginActivity.ACCOUNT_EXTRA_KEY);
                        if (mAccount != null) {
                            queryUser();
                            mAccountText.setText("账号 : " +  mAccount);
                            mExitLabel.setVisibility(View.VISIBLE);
                            mChannelButton.setEnabled(true);
                            mTimeRecordButton.setEnabled(true);
                            mTimeRecordButton.setText("您已来到光阴记" + getTimeRecord() + "天！" );
                            mChannelButton.setText("最近加入的频道");
                            mChannelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //新Activity，展示频道
                                }
                            });
                            mChangeInfoButton.setVisibility(View.VISIBLE);
                            mChannelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //新Activity，修改信息
                                }
                            });
                        }
                    }
                }
            });
    //调用相机注册器
    private ActivityResultLauncher mTakePhotoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data == null) {
                    return;
                } else {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        Uri uri = bitmapToUri(bitmap);
                        cutPicture(uri);
                    }
                }
            }
        }
    });
    //打开图库注册器
    private ActivityResultLauncher mOpenAlbumLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data == null) {
                    return;
                } else {
                    Uri uri;
                    uri = data.getData();
                    Uri fileUri = contentToFile(uri);
                    cutPicture(fileUri);
                }
            }
        }
    });

    //message.what
    private static final int QUERY_USER = 1;

    private static final String BUNDLE_KEY = "my_cut_bundle";


    //权限请求
    private RxPermissions mRxPermissions;
    //权限组
    private static final String[] mPermissionsGroup = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private boolean mHasPermission = false;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
        mChangeInfoButton = binding.changInformation;
        mProfileImage = binding.profileImage;


        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (mAccount.equals("-1")) {
                   Intent intent = new Intent(getActivity(), LoginActivity.class);
                   mRegisterLauncher.launch(intent);

               } else {
                   //修改头像
                   changeProfile(view);
               }
            }
        });

        return root;

    }

    /**
     * 查询登录用户信息
     */

    private void queryUser(){
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == QUERY_USER) {
                    User user = new User();
                    user = (User) msg.obj;
                    mUser = user;
                    mName = user.getUserName();
                    mNameText.setText("昵称 : " + mName);
                    mProfile = user.getUserPicture();
                    mProfileImage.setImageBitmap(mProfile);
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

    /**
     * 申请权限
     */
    private void requestPermission() {
        //Android6.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRxPermissions = new RxPermissions(getActivity());
            mRxPermissions.request(mPermissionsGroup).subscribe(granted -> {
                if (granted) {
                    Toast.makeText(getActivity(), "权限请求成功！", Toast.LENGTH_SHORT).show();
                    mHasPermission = true;
                } else {
                    Toast.makeText(getActivity(), "权限未开启！", Toast.LENGTH_SHORT).show();
                    mHasPermission = false;
                }
            });

        } else {
            Toast.makeText(getActivity(), "不需要动态获取权限！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 更换头像
     * @param view
     */

    private void changeProfile(View view) {
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        mBottomView = getLayoutInflater().inflate(R.layout.dialogbottom, null);
        mBottomSheetDialog.setContentView(mBottomView);
        mBottomSheetDialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet)
                .setBackgroundColor(Color.TRANSPARENT);
        TextView takePhoto = mBottomView.findViewById(R.id.take_photo);
        TextView openAlbum = mBottomView.findViewById(R.id.open_album);
        TextView cancel = mBottomView.findViewById(R.id.cancel_label);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拍照
                //Toast.makeText(getActivity(), "打开相机", Toast.LENGTH_SHORT).show();
                takePicture();
                mBottomSheetDialog.cancel();
            }
        });
        openAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开相册
                //Toast.makeText(getActivity(), "打开图库", Toast.LENGTH_SHORT).show();
                selectFromAlbum();
                mBottomSheetDialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消
                mBottomSheetDialog.cancel();
            }
        });
        mBottomSheetDialog.show();

    }

    /**
     * 拍照
     */
    private void takePicture() {
        if (!mHasPermission) {
            //Toast.makeText(getActivity(), "未获取到权限！", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mTakePhotoLauncher.launch(intent);
    }

    /**
     * 从图库中选择
     */

    private void selectFromAlbum() {
        if (!mHasPermission) {
            //Toast.makeText(getActivity(), "未获取到权限！", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        mOpenAlbumLauncher.launch(intent);
    }


    /**
     * 将Bitmap暂存到内存卡中，然后返回file类型的Uri
     * @param bitmap
     * @return
     */
    @Nullable
    private Uri bitmapToUri(Bitmap bitmap) {
        File tempDir = new File(Environment.getExternalStorageDirectory() + "/temp_picture");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        File tempPath = new File(tempDir.getAbsolutePath() , "profile.png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(tempPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return Uri.fromFile(tempPath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将content类型的Uri转换成file类型
     * 将content类型的Uri解析成Bitmap，保存到内存卡，再根据路径转换成file类型
     *
     * @param uri
     * @return
     */
    @Nullable
    private Uri contentToFile(Uri uri) {
        InputStream inputStream = null;
        try {
            inputStream = getContext().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmapToUri(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 裁剪图片
     * @param uri
     */
    private void cutPicture(Uri uri) {
        Uri destinationUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/temp_picture" ,"cut_profile.png"));
        UCrop.Options options = new UCrop.Options();
        UCrop uCrop = UCrop.of(uri, destinationUri);
        uCrop.withAspectRatio(1, 1);
        options.setAllowedGestures(com.yalantis.ucrop.UCropActivity.ALL, com.yalantis.ucrop.UCropActivity.NONE, com.yalantis.ucrop.UCropActivity.ALL);
        options.setToolbarTitle("移动和缩放");
        options.setCropGridStrokeWidth(2);
        options.setMaxScaleMultiplier(3);
        options.setShowCropGrid(true);
        options.setShowCropFrame(true);
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));
        options.setDimmedLayerColor(Color.parseColor("#AA000000"));
        options.setToolbarColor(Color.parseColor("#000000"));
        options.setStatusBarColor(Color.parseColor("#000000"));
        options.setCropGridColor(Color.parseColor("#ffffff"));
        options.setCropFrameColor(Color.parseColor("#ffffff"));
        uCrop.withOptions(options);
        uCrop.start(getContext(), this, UCrop.REQUEST_CROP);

    }


    /**
     * 裁剪图片回调函数
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
                final Uri resultUri = UCrop.getOutput(data);
                Luban.with(getContext())
                    .load(resultUri)
                    .ignoreBy(10)
                    .setTargetDir(Environment.getExternalStorageDirectory() + "/temp_picture")
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            Log.e("测试", "开始");
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(file));
                            mProfile = bitmap;
                            mProfileImage.setImageBitmap(bitmap);
                            mUser.setUserPicture(bitmap);
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            Log.e("测试", "失败");
                        }
                    }).launch();

            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBConnectUser dbConnectUser = new DBConnectUser();
                        dbConnectUser.update(mUser);
                    }
                }).start();
            } else if (requestCode == Activity.RESULT_OK && resultCode == UCrop.RESULT_ERROR) {
                final Throwable cropError = UCrop.getError(data);
            }
    }

    /**
     * 来到光阴记的时间
     * @return
     */
    private String getTimeRecord() {
        return null;
    }
}

