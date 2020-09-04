package com.aletify.smiletrainmobiletwo;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String identifier;
    public String firstName;
    public String lastName;
    public String whatsAppNumber;
    public List<CalendarObject> calendarObjectList;
    public List<CheckInSet> checkInObjectList;
    public List<PhotoSet> photoSetList;

    public User(){

    }

    public User(String identifier, String firstName, String lastName, String whatsAppNumber){
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.whatsAppNumber = whatsAppNumber;
        this.calendarObjectList = new ArrayList<>();
        this.checkInObjectList = new ArrayList<>();
        this.photoSetList = new ArrayList<>();
    }

}
