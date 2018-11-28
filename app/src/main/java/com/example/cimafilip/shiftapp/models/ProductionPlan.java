package com.example.cimafilip.shiftapp.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class ProductionPlan {
    private ObjectId objectId;
    private ObjectId basedOnId;
    private SuperiorPlan basedOn;
    private ArrayList<ObjectId> shiftsIds;
    private ArrayList<Shift> shifts;

    public ProductionPlan(ObjectId objectId, ObjectId basedOnId, ArrayList<ObjectId> shiftsIds) {
        this.objectId = objectId;
        this.basedOnId = basedOnId;
        this.shiftsIds = shiftsIds;
    }

    public ProductionPlan(ObjectId objectId) {
        this.objectId = objectId;
    }
}