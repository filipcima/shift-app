package com.example.cimafilip.shiftapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShiftHelper {
    @SerializedName("workers")
    private ArrayList<String> workers;

    public ArrayList<String> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<String> workers) {
        this.workers = workers;
    }
}
