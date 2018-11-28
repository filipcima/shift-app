package com.example.cimafilip.shiftapp.models;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class CancelRequest {
    private ObjectId objectId;
    private LocalDateTime created;
    private ObjectId forShiftId;
    private Shift forShift;
    private boolean accepted;
    private boolean resolved;

    public CancelRequest(ObjectId objectId, LocalDateTime created, ObjectId forShiftId, boolean accepted, boolean resolved) {
        this.objectId = objectId;
        this.created = created;
        this.forShiftId = forShiftId;
        this.accepted = accepted;
        this.resolved = resolved;
    }
    public CancelRequest(ObjectId objectId) {
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
}
