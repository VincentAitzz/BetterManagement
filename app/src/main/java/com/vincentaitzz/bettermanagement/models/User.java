package com.vincentaitzz.bettermanagement.models;

public class User {
    private String ID;
    private String NAME;
    private String PASSWORD;
    private String EMAIL;

    public User() {
    }

    public User(String ID, String NAME, String PASSWORD, String EMAIL) {
        this.ID = ID;
        this.NAME = NAME;
        this.PASSWORD = PASSWORD;
        this.EMAIL = EMAIL;
    }

    public User(String EMAIL, String PASSWORD) {
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }
}
