package com.example.cimafilip.shiftapp.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;

class UserPlan {
    private ObjectId objectId;
    private ArrayList<ObjectId> shiftsIds;
    private ArrayList<Shift> shifts;
    private ObjectId superiorPlanId;
    private SuperiorPlan superiorPlan;
}
