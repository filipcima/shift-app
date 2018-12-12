package com.example.cimafilip.shiftapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShiftList {
    @SerializedName("_items")
    private List<Shift> shifts;

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }
}
