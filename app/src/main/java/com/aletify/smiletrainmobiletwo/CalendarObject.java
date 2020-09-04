package com.aletify.smiletrainmobiletwo;

public class CalendarObject {
    final public int PHYSICAL_APPOINTMENT = 0;
    final public int PICTURES_DUE = 0;

    public int appointment_type;
    public String appointment_date;

    public CalendarObject(int appointment_type, String appointment_date){
        this.appointment_date = appointment_date;
        this.appointment_type = appointment_type;
    }
}
