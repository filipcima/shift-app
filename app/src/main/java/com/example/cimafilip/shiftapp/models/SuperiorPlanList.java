package com.example.cimafilip.shiftapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SuperiorPlanList implements Serializable {
    @SerializedName("_items")
    private List<SuperiorPlan> superiorPlans;

    public List<SuperiorPlan> getSuperiorPlans() {
        return superiorPlans;
    }

    public void setSuperiorPlans(List<SuperiorPlan> superiorPlans) {
        this.superiorPlans = superiorPlans;
    }
}
