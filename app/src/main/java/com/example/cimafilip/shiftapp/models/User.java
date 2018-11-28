package com.example.cimafilip.shiftapp.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;

class User {
    private ObjectId objectId;
    private String firstName;
    private String secondName;
    private String email;
    private String passwordHash;
    private String role;
    private ArrayList<User> inferiors;

    User(ObjectId objectId, String firstName, String secondName, String email, String passwordHash, String role, ArrayList<User> inferiors) {
        this.objectId = objectId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.inferiors = inferiors;
    }

    User(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList<User> getInferiors() {
        return inferiors;
    }

    public void setInferiors(ArrayList<User> inferiors) {
        this.inferiors = inferiors;
    }
}
