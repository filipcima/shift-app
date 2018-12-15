package com.example.cimafilip.shiftapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationHelper implements Serializable {
    @SerializedName("show")
    private boolean show;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
