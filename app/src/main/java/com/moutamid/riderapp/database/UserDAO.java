package com.moutamid.riderapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.moutamid.riderapp.models.UserModel;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = REPLACE)
    void insert(UserModel users);

    @Query("SELECT * FROM user ORDER BY ID DESC")
    List<UserModel> getAll();

    @Query(("SELECT * FROM user WHERE username= :username"))
    List<UserModel> getUser(String username);

    @Query("UPDATE user SET coins= :coins where ID = :ID")
    void update(int ID, int coins);

    @Delete
    void Delete(UserModel users);

}
