package com.moutamid.riderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.riderapp.database.RoomDB;
import com.moutamid.riderapp.databinding.ActivitySignupBinding;
import com.moutamid.riderapp.models.UserModel;
import com.moutamid.riderapp.utlis.Constants;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        database = RoomDB.getInstance(this);

        binding.login.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        binding.Signup.setOnClickListener(v -> {
            if (valid()){
                List<UserModel> user = database.userDAO().getUser(binding.username.getText().toString().trim());
                Log.d("Checking1", "Size : " + user.size() + "\n Name : ");
                if(user.size() > 0) {
                    Toast.makeText(this, "Username already exist", Toast.LENGTH_SHORT).show();
                } else {
                    UserModel userModel = new UserModel(
                            binding.username.getText().toString().trim(),
                            binding.password.getText().toString().trim(),
                            0
                    );
                    database.userDAO().insert(userModel);
                    new Handler().postDelayed(() -> {
                        //Stash.put(Constants.isUserLogin, true);
                        Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    }, 1000);
                }
            }
        });

    }

    private boolean valid() {

        if (binding.username.getText().toString().isEmpty()){
            binding.username.setError("Add Username");
            Toast.makeText(this, "Please add username", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.password.getText().toString().isEmpty()){
            binding.password.setError("Add Password");
            Toast.makeText(this, "Please add Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}