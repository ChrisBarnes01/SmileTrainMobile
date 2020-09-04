package com.aletify.smiletrainmobiletwo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PhotoSet {
    public final int STANDARD_PHOTOS = 0;
    public final int RETAINER_CHECKIN = 1;
    public final int EMERGENCY = 2;
    public final int EXTRA = 3;


    public String date;
    public List<String> photoReferences;
    public int photoSetType;

    PhotoSet(List<String> photoReferences, int photoSetType){
        this.photoReferences = photoReferences;
        this.photoSetType = photoSetType;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        this.date = strDate;
    }




}
