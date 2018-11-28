package com.example.cimafilip.shiftapp.models;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;

class SuperiorPlan {
    private ObjectId objectId;
    private ObjectId ownerId;
    private User owner;
    private String status;
    private LocalDateTime published;
    private ArrayList<Shift> shifts;
    private ArrayList<LocalDateTime> plannedShifts;


    SuperiorPlan(ObjectId ownerId, User owner, String status, LocalDateTime published, ArrayList<Shift> shifts, ArrayList<LocalDateTime> plannedShifts) {
        this.ownerId = ownerId;
        this.owner = owner;
        this.status = status;
        this.published = published;
        this.shifts = shifts;
        this.plannedShifts = plannedShifts;
    }

    SuperiorPlan(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(ObjectId ownerId) {
        this.ownerId = ownerId;
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

    public LocalDateTime getPublished() {
        return published;
    }

    public void setPublished(LocalDateTime published) {
        this.published = published;
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(ArrayList<Shift> shifts) {
        this.shifts = shifts;
    }

    public ArrayList<LocalDateTime> getPlannedShifts() {
        return plannedShifts;
    }

    public void setPlannedShifts(ArrayList<LocalDateTime> plannedShifts) {
        this.plannedShifts = plannedShifts;
    }
}
