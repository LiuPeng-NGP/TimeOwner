package com.example.timeowner.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timeowner.concentration.ConcentrationMainActivity;
import com.example.timeowner.coursetable.CourseTableMainActivity;
import com.example.timeowner.databinding.FragmentHomeBinding;
import com.example.timeowner.event.EventMainActivity;
import com.example.timeowner.habit.HabitMainActivity;
import com.example.timeowner.happiness.HappinessMainActivity;
import com.example.timeowner.login.LoginActivity;
import com.example.timeowner.object.Target;
import com.example.timeowner.target.TargetMainActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ImageButton mImageButtonTarget = binding.imageButton3;
        mImageButtonTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TargetMainActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton mImageButtonHabit = binding.imageButton4;
        mImageButtonHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HabitMainActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton mImageButtonCourseTable = binding.imageButton5;
        mImageButtonCourseTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CourseTableMainActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton mImageButtonEvent = binding.imageButton6;
        mImageButtonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EventMainActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton mImageButtonConcentration = binding.imageButton7;
        mImageButtonConcentration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ConcentrationMainActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton mImageButtonHappiness = binding.imageButton8;
        mImageButtonHappiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HappinessMainActivity.class);
                startActivity(intent);
            }
        });















        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
