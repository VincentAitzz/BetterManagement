package com.vincentaitzz.bettermanagement.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Schedule {

    private String ID;
    private String DATE;
    private String START;
    private String FINISH;
    private String NAME;
    private String DESCRIPTION;

    private DatabaseReference dbReference;

    public Schedule(){}

    public Schedule(String ID, String DATE, String START, String FINISH, String NAME, String DESCRIPTION) {
        this.ID = ID;
        this.DATE = DATE;
        this.START = START;
        this.FINISH = FINISH;
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
    }

    private DatabaseReference getUserSchedulesRef(String userID){
        return FirebaseDatabase.getInstance().getReference("users").child(userID).child("schedules");
    }

    public void createSchedule(String userID){
        DatabaseReference schedulesRef = getUserSchedulesRef(userID);
        String id = schedulesRef.push().getKey();
        this.ID = id;
        schedulesRef.child(id).setValue(this);
    }

    public void readSchedules(String userId, ValueEventListener listener) {
        DatabaseReference schedulesRef = getUserSchedulesRef(userId);
        schedulesRef.addValueEventListener(listener);
    }

    public void updateSchedule(String userId) {
        DatabaseReference schedulesRef = getUserSchedulesRef(userId);
        schedulesRef.child(this.ID).setValue(this);
    }

    public void deleteSchedule(String userId) {
        DatabaseReference schedulesRef = getUserSchedulesRef(userId);
        schedulesRef.child(this.ID).removeValue();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getSTART() {
        return START;
    }

    public void setSTART(String START) {
        this.START = START;
    }

    public String getFINISH() {
        return FINISH;
    }

    public void setFINISH(String FINISH) {
        this.FINISH = FINISH;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
}
