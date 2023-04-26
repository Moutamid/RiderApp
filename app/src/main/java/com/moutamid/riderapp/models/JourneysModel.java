package com.moutamid.riderapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "journeys")
public class JourneysModel {
    @PrimaryKey(autoGenerate = true)
    int ID = 0;
    @ColumnInfo(name = "userID")
    int userID = 0;
    @ColumnInfo(name = "startJourney")
    String startJourney = "";
    @ColumnInfo(name = "endJourney")
    String endJourney = "";
    @ColumnInfo(name = "spent")
    int spent = 0;

    public JourneysModel() {
    }

    public JourneysModel(int userID, String startJourney, String endJourney, int spent) {
        this.userID = userID;
        this.startJourney = startJourney;
        this.endJourney = endJourney;
        this.spent = spent;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getStartJourney() {
        return startJourney;
    }

    public void setStartJourney(String startJourney) {
        this.startJourney = startJourney;
    }

    public String getEndJourney() {
        return endJourney;
    }

    public void setEndJourney(String endJourney) {
        this.endJourney = endJourney;
    }

    public int getSpent() {
        return spent;
    }

    public void setSpent(int spent) {
        this.spent = spent;
    }
}
