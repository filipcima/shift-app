package com.example.cimafilip.shiftapp.models.helpers;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NotificationHelper implements Serializable {
    @SerializedName("show")
    private boolean show;

    @SerializedName("from_user")
    private String fromUser;

    @SerializedName("for_users")
    private List<String> userList;

    @SerializedName("shift")
    private String shift;

    @SerializedName("message")
    private String message;

    @SerializedName("notification_type")
    private String notificationType;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
