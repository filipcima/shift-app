package com.example.cimafilip.shiftapp.models.helpers;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShiftHelper implements Serializable{
    @SerializedName("workers")
    private ArrayList<String> workers;

    public ArrayList<String> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<String> workers) {
        this.workers = workers;
    }
}
