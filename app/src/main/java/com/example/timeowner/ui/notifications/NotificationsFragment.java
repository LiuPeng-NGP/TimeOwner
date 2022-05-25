package com.example.timeowner.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timeowner.databinding.FragmentNotificationsBinding;
import com.example.timeowner.login.LoginActivity;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);//通过ViewModelProvider实例化ViewModel
        //当页面需要ViewModel时，会向ViewModelProvider索要，而ViewModelProvider会去HashMap中检查该ViewModel是否已经存在缓存中，若存在，则直接返回，否则，则实例化一个

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ImageView imageView = binding.profileImage;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}