package com.example.cimafilip.shiftapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notification implements Serializable {
    @SerializedName("_id")
    private String _id;

    @SerializedName("user")
    private User user;

    @SerializedName("message")
    private String message;

    @SerializedName("_created")
    private String created;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
