package com.moutamid.riderapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user")
public class UserModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int ID = 0;
    @ColumnInfo(name = "username")
    String username = "";
    @ColumnInfo(name = "password")
    String password = "";
    @ColumnInfo(name = "coins")
    int coins = 0;

    public UserModel() {
    }

    public UserModel(String username, String password, int coins) {
        this.username = username;
        this.password = password;
        this.coins = coins;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
