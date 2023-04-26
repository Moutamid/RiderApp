package com.moutamid.riderapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.moutamid.riderapp.models.JourneysModel;

import java.util.List;

@Dao
public interface JourneyDAO {
    @Insert(onConflict = REPLACE)
    void insert(JourneysModel journeys);

    @Query("SELECT * FROM journeys ORDER BY ID DESC")
    List<JourneysModel> getAll();

    @Query("SELECT * FROM journeys WHERE userID= :userID")
    List<JourneysModel> getAll(int userID);

    @Query("UPDATE journeys SET userID = :userID, startJourney = :startJourney, endJourney= :endJourney, spent= :spent where ID = :ID")
    void update(int ID, int userID, String startJourney, String endJourney, int spent);

    @Delete
    void Delete(JourneysModel journeys);
}
