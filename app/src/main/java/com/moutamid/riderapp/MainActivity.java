package com.moutamid.riderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.navigation.NavigationBarView;
import com.moutamid.riderapp.database.RoomDB;
import com.moutamid.riderapp.databinding.ActivityMainBinding;
import com.moutamid.riderapp.fragments.JourneyFragment;
import com.moutamid.riderapp.fragments.MapsFragment;
import com.moutamid.riderapp.models.UserModel;
import com.moutamid.riderapp.utlis.Constants;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    RoomDB database;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants.checkApp(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new MapsFragment()).addToBackStack(null).commit();

        username = Stash.getString(Constants.USERNAME);

        database = RoomDB.getInstance(this);

        List<UserModel> user = database.userDAO().getUser(username.trim());
        int coins = user.get(user.size()-1).getCoins();

        binding.coins.setText("" + coins);

        binding.coins.setOnClickListener(v -> {
            startActivity(new Intent(this, BuyCoinActivity.class));
        });

        binding.logout.setOnClickListener(v -> {
            Stash.put(Constants.isUserLogin, false);
            //Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, SplashScreenActivity.class));
            finish();
        });

        binding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.map_nav:
                    getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new MapsFragment()).addToBackStack(null).commit();
                    break;
                case R.id.journey_nav:
                    getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new JourneyFragment()).addToBackStack(null).commit();
                    break;
                default:
                    break;
            }
            return true;
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        username = Stash.getString(Constants.USERNAME);
        database = RoomDB.getInstance(this);
        List<UserModel> user = database.userDAO().getUser(username.trim());
        int coins = user.get(user.size()-1).getCoins();
        binding.coins.setText("" + coins);
    }
}