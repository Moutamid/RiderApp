package com.moutamid.riderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.riderapp.database.RoomDB;
import com.moutamid.riderapp.databinding.ActivityLoginBinding;
import com.moutamid.riderapp.models.UserModel;
import com.moutamid.riderapp.utlis.Constants;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants.checkApp(this);

        database = RoomDB.getInstance(this);

        binding.signup.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
            finish();
        });

        binding.login.setOnClickListener(v -> {
            if (valid()){
                List<UserModel> user = database.userDAO().getUser(binding.username.getText().toString().trim());
                if(user.size() > 0){
                    if(user.get(user.size()-1).getPassword().equals(binding.password.getText().toString().trim())){
                        Stash.put(Constants.isUserLogin, true);
                        Stash.put(Constants.USERNAME, binding.username.getText().toString().trim());
                        Toast.makeText(this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
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