package com.example.cimafilip.shiftapp.models;

import com.google.gson.annotations.SerializedName;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SuperiorPlan implements Serializable {
    @SerializedName("_id")
    private String _id;

    @SerializedName("owner")
    private User owner;

    @SerializedName("status")
    private String status;

    @SerializedName("_created")
    private String created;

    @SerializedName("shifts")
    private List<Shift> shifts;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
