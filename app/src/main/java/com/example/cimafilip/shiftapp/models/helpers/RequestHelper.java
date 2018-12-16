package com.example.cimafilip.shiftapp.models.helpers;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestHelper implements Serializable {
    @SerializedName("_id")
    private String _id;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("for_shift")
    private String forShift;

    @SerializedName("request_type")
    private String requestType;

    @SerializedName("accepted")
    private boolean accepted;

    @SerializedName("resolved")
    private boolean resolved;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getForShift() {
        return forShift;
    }

    public void setForShift(String forShift) {
        this.forShift = forShift;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
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
