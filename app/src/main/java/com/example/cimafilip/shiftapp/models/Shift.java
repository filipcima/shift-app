package com.example.cimafilip.shiftapp.models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Shift {
    @SerializedName("_etag")
    private String _etag;

    @SerializedName("_id")
    private String _id;

    @SerializedName("number_of_workers")
    private int workersCount;

    @SerializedName("workers")
    private ArrayList<User> workers;

    @SerializedName("superior_plan")
    private String superiorPlan;

    @SerializedName("date_from")
    private String dateFrom;

    @SerializedName("date_to")
    private String dateTo;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<User> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<User> workers) {
        this.workers = workers;
    }

    public int getWorkersCount() {
        return workersCount;
    }

    public void setWorkersCount(int workersCount) {
        this.workersCount = workersCount;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getSuperiorPlan() {
        return superiorPlan;
    }

    public void setSuperiorPlan(String superiorPlan) {
        this.superiorPlan = superiorPlan;
    }

    public String get_etag() {
        return _etag;
    }

    public void set_etag(String _etag) {
        this._etag = _etag;
    }
}
