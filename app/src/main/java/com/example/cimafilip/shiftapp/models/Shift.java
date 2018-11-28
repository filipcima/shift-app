package com.example.cimafilip.shiftapp.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Shift {
    private ObjectId objectId;
    private ObjectId superiorPlanId;
    private ObjectId userPlanId;
    private ObjectId userId;
    private ArrayList<ObjectId> workersIds;

    private SuperiorPlan superiorPlan;
    private UserPlan userPlan;
    private User user;
    private ArrayList<User> workers;

    public Shift(ObjectId objectId, ObjectId superiorPlanId, ObjectId userPlanId, ObjectId userId, ArrayList<ObjectId> workersIds) {
        this.objectId = objectId;
        this.superiorPlanId = superiorPlanId;
        this.userPlanId = userPlanId;
        this.userId = userId;
        this.workersIds = workersIds;
    }

    public Shift(ObjectId objectId) {

    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getSuperiorPlanId() {
        return superiorPlanId;
    }

    public void setSuperiorPlanId(ObjectId superiorPlanId) {
        this.superiorPlanId = superiorPlanId;
    }

    public ObjectId getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(ObjectId userPlanId) {
        this.userPlanId = userPlanId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public ArrayList<ObjectId> getWorkersIds() {
        return workersIds;
    }

    public void setWorkersIds(ArrayList<ObjectId> workersIds) {
        this.workersIds = workersIds;
    }

    public SuperiorPlan getSuperiorPlan() {
        return superiorPlan;
    }

    public void setSuperiorPlan(SuperiorPlan superiorPlan) {
        this.superiorPlan = superiorPlan;
    }

    public UserPlan getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlan userPlan) {
        this.userPlan = userPlan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<User> workers) {
        this.workers = workers;
    }
}
