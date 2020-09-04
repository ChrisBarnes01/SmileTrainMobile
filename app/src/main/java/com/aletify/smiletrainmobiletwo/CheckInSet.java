package com.aletify.smiletrainmobiletwo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckInSet {
    public String date;
    public boolean questionYesNo1;
    public String questionSlider1;
    public int questionNumberPicker1;
    public String questionPicker;

    public CheckInSet(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        this.date = strDate;

    }
}
