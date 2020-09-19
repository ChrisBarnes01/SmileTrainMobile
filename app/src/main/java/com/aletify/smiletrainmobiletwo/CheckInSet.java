package com.aletify.smiletrainmobiletwo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckInSet {
    public int date;
    public boolean questionYesNo1;
    public String questionSlider1;
    public int questionNumberPicker1;
    public String questionPicker;

    public CheckInSet(){
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        this.date = dayOfYear;
    }
}
