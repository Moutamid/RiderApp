package com.moutamid.riderapp.models;

import com.google.android.gms.maps.model.Marker;

public class HistoryModel {
    Marker markerA = null;
    Marker markerB = null;

    public HistoryModel() {
    }

    public HistoryModel(Marker markerA, Marker markerB) {
        this.markerA = markerA;
        this.markerB = markerB;
    }

    public Marker getMarkerA() {
        return markerA;
    }

    public void setMarkerA(Marker markerA) {
        this.markerA = markerA;
    }

    public Marker getMarkerB() {
        return markerB;
    }

    public void setMarkerB(Marker markerB) {
        this.markerB = markerB;
    }
}
