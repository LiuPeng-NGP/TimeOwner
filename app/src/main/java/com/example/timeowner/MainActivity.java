package com.example.timeowner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.timeowner.dbconnect.DBConnectHabit;
import com.example.timeowner.ui.dashboard.DashboardFragment;
import com.example.timeowner.ui.home.HomeFragment;
import com.example.timeowner.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.example.timeowner.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("userID",null);


            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            int lastTimeStarted = settings.getInt("last_time_started", -1);
            Calendar calendar = Calendar.getInstance();
            int today = calendar.get(Calendar.DAY_OF_YEAR);

            if (today != lastTimeStarted) {
                //startSomethingOnce();

                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("last_time_started", today);
                editor.commit();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBConnectHabit dbConnectHabit = new DBConnectHabit();
                        dbConnectHabit.UpdateEveryDayHabit(userID);

                    }
                }).start();






                SharedPreferences preferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
                preferences.edit()
                        .putBoolean("NewDay", true)
                        .apply();
            }else{
                SharedPreferences preferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
                preferences.edit()
                        .putBoolean("NewDay", false)
                        .apply();
            }


    }

}