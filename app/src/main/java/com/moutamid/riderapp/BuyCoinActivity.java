package com.moutamid.riderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.riderapp.database.RoomDB;
import com.moutamid.riderapp.databinding.ActivityBuyCoinBinding;
import com.moutamid.riderapp.models.UserModel;
import com.moutamid.riderapp.utlis.Constants;

import java.util.List;

public class BuyCoinActivity extends AppCompatActivity {
    ActivityBuyCoinBinding binding;
    RoomDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyCoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        String username = Stash.getString(Constants.USERNAME);

        database = RoomDB.getInstance(this);

        List<UserModel> user = database.userDAO().getUser(username.trim());
        UserModel userModel = user.get(user.size()-1);
        int coins = userModel.getCoins();

        binding.coin10.setOnClickListener(v -> {
            database.userDAO().update(userModel.getID(), (coins+10) );
            Toast.makeText(this, "10 Coin Bought", Toast.LENGTH_SHORT).show();
        });

        binding.coin20.setOnClickListener(v -> {
            database.userDAO().update(userModel.getID(), (coins+20) );
            Toast.makeText(this, "20 Coin Bought", Toast.LENGTH_SHORT).show();
        });

        binding.coin30.setOnClickListener(v -> {
            database.userDAO().update(userModel.getID(), (coins+30) );
            Toast.makeText(this, "30 Coin Bought", Toast.LENGTH_SHORT).show();
        });

        binding.coin50.setOnClickListener(v -> {
            database.userDAO().update(userModel.getID(), (coins+50) );
            Toast.makeText(this, "50 Coin Bought", Toast.LENGTH_SHORT).show();
        });


        binding.coin100.setOnClickListener(v -> {
            database.userDAO().update(userModel.getID(), (coins+100) );
            Toast.makeText(this, "100 Coin Bought", Toast.LENGTH_SHORT).show();
        });


    }
}