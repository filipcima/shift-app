package com.example.cimafilip.shiftapp.models;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChangeRequest {
    private ObjectId objectId;
    private LocalDateTime created;
    private ObjectId forShiftId;
    private Shift forShift;
    private boolean accepted;
    private boolean resolved;
    private ArrayList<ObjectId> actorsIds;
    private ArrayList<User> actors;

    public ChangeRequest(ObjectId objectId, LocalDateTime created, ObjectId forShiftId, boolean accepted, boolean resolved, ArrayList<ObjectId> actorsIds) {
        this.objectId = objectId;
        this.created = created;
        this.forShiftId = forShiftId;
        this.accepted = accepted;
        this.resolved = resolved;
        this.actorsIds = actorsIds;
    }

    public ChangeRequest(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public ObjectId getForShiftId() {
        return forShiftId;
    }

    public void setForShiftId(ObjectId forShiftId) {
        this.forShiftId = forShiftId;
    }

    public Shift getForShift() {
        return forShift;
    }

    public void setForShift(Shift forShift) {
        this.forShift = forShift;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public ArrayList<ObjectId> getActorsIds() {
        return actorsIds;
    }

    public void setActorsIds(ArrayList<ObjectId> actorsIds) {
        this.actorsIds = actorsIds;
    }

    public ArrayList<User> getActors() {
        return actors;
    }

    public void setActors(ArrayList<User> actors) {
        this.actors = actors;
    }
}
