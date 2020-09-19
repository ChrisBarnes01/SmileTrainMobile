package com.aletify.smiletrainmobiletwo;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.text.Spannable;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LinearLayout layoutCheckInIndicators;
    String totalUserName;
    List<CalendarObject> myAppointments;
    List<Boolean> listOfPreviousCheckins;
    ImageView avatar;
    CardView todayIsDayCard;
    CardView dayReminderCard;
    int DayOfWeek;
    HashMap<String, CheckInSet> myCheckins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        boolean loggedIn = prefs.getBoolean("LoggedIn", false);

        String avatarName = prefs.getString("avatarId", "icon_1");
        avatar = findViewById(R.id.circleImage);
        int resourceId = getApplicationContext().getResources().getIdentifier(avatarName, "drawable", getApplicationContext().getPackageName());
        avatar.setImageDrawable(getResources().getDrawable(resourceId));


        //avatar.setImageDrawable(getResources().getDrawable(R.drawable.icon_1));
        //R.id.

        todayIsDayCard = (CardView) findViewById(R.id.today_is_day_card);
        dayReminderCard = (CardView) findViewById(R.id.home_day_reminder_card);

        //JUMP TO CREATE_ACCOUNT PAGE
        //DELETE THIS LATER
        //runCreateAccount();


        if (firstStart){
            runIntroSequence();
        }
        if (!loggedIn){
            runIntroSequence();
        }
        else{
            Locale colombiaLocale=new Locale("es", "CO");

            Calendar calendar = Calendar.getInstance();

            DayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) -1;


            final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            final TextView numberOfDaysNotification = findViewById(R.id.home_day_reminder_card_text);
            final Button checkInButton = findViewById(R.id.home_check_in_card_button);
            final TextView dayOfWeekLabel = findViewById(R.id.homepage_day_of_week_label);
            final TextView monthYearLabel = findViewById(R.id.homepage_month_and_year_label);
            final TextView dateLabel = findViewById(R.id.homepage_date_label);
            final TextView nameLabel = findViewById(R.id.main_activity_title);
            layoutCheckInIndicators = findViewById(R.id.home_check_in_record_view);

            Calendar myCalendar = Calendar.getInstance();
            dayOfWeekLabel.setText(myCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, colombiaLocale));//myCalendar.get(Calendar.DAY_OF_WEEK));
            monthYearLabel.setText(myCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, colombiaLocale) + " " + myCalendar.get(Calendar.YEAR));
            Date date = new Date();
            dateLabel.setText((String) DateFormat.format("dd",   date));


            String username = prefs.getString("username", "userId");

            //Now Get User Data from Server!
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("patients");
            mDatabase.child(username).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    //Toast.makeText(getApplicationContext(), user.firstName, Toast.LENGTH_SHORT).show();
                    totalUserName = user.firstName + " " + user.lastName;
                    myAppointments = user.calendarObjectList;
                    myCheckins = user.checkInObjectList;

                    //SET NUMBER OF DAYS UNTIL PICTURE SENT IN APPOINTMENT
                    Calendar calendar = Calendar.getInstance();
                    int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

                    int nextPhotosDueDate = getNextPhotoDueDate();
                    int numberOfDaysUntilCheckIn = nextPhotosDueDate - dayOfYear;

                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("numberOfDaysUntilCheckIn", numberOfDaysUntilCheckIn);
                    editor.apply();


                    if (numberOfDaysUntilCheckIn > 0){
                        dayReminderCard.setVisibility(View.VISIBLE);
                        todayIsDayCard.setVisibility(View.GONE);
                        String first = Integer.valueOf(numberOfDaysUntilCheckIn) + " Días";
                        String next = ", para enviar sus nuevas fotos con alineadores. ";

                        numberOfDaysNotification.setText(first + next, TextView.BufferType.SPANNABLE);
                        Spannable s = (Spannable)numberOfDaysNotification.getText();
                        int start = 0;
                        int end = first.length();
                        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    //Change this to zero
                    else if(numberOfDaysUntilCheckIn == 0){
                        dayReminderCard.setVisibility(View.GONE);
                        todayIsDayCard.setVisibility(View.VISIBLE);

                        Button todayIsDayButton = (Button) findViewById(R.id.today_is_day_button);
                        todayIsDayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                runCamera();
                            }
                        });
                    }
                    else{
                        dayReminderCard.setVisibility(View.VISIBLE);
                        todayIsDayCard.setVisibility(View.GONE);
                        numberOfDaysNotification.setText("La imagen está atrasada. Por favor, tome una foto ahora.");
                    }


                    //SET NUMBER OF DAYS UNTIL PHYSICAL APPOINTMENT
                    TextView physicalAppointmentLabel = findViewById(R.id.home_calendar_appointment_label);
                    int nextAppointmentDate = getNextAppointmentDate();
                    int numberOfDaysUntilAppointment = nextAppointmentDate - dayOfYear;

                    if (numberOfDaysUntilAppointment == 0){
                        String firstAppointmentString = "Tu proxima cita es ";
                        String nextAppointmentString = "hoy.";

                        physicalAppointmentLabel.setText(firstAppointmentString + nextAppointmentString, TextView.BufferType.SPANNABLE);
                        Spannable sAppointment = (Spannable)physicalAppointmentLabel.getText();
                        int startAppointment = firstAppointmentString.length();
                        int endAppointment = firstAppointmentString.length() + nextAppointmentString.length();
                        sAppointment.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), startAppointment, endAppointment, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    else if(numberOfDaysUntilAppointment > 0){
                        physicalAppointmentLabel.setText("su próxima cita es en " + numberOfDaysUntilAppointment + " dias.");
                    }
                    else{
                        physicalAppointmentLabel.setText("No tienes citas próximas");
                    }

                    nameLabel.setText(totalUserName);


                    //Next, set the previous checkinRecord


                    //dayOfYear;



                    //myCheckins

                    //listOfPreviousCheckins = new ArrayList<>(Arrays.asList(false, false, false, false, false, false, false));
                    listOfPreviousCheckins = new ArrayList<>();

                    if (DayOfWeek == 7){
                        DayOfWeek = 0;
                    }

                    //getCheckinsBefore;
                    for (int i = dayOfYear-DayOfWeek; i < dayOfYear; i++ ){

                        if (myCheckins.containsKey(String.valueOf(i))){
                            listOfPreviousCheckins.add(true);
                        }
                        else{
                            listOfPreviousCheckins.add(false);
                        }
                    }

                    //getCheckinsAfter and During
                    for (int i = dayOfYear; i <= (dayOfYear + (6 - DayOfWeek)); i++ ){
                        String intKey = String.valueOf(i);
                        if (myCheckins.containsKey(intKey)){
                            listOfPreviousCheckins.add(true);
                        }
                        else{
                            listOfPreviousCheckins.add(false);
                        }
                    }

                    setupCheckInRecordIndicators();





                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });





            getPermissions();
            //setupCheckInRecordIndicators();


            checkInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runHealthCheck();
                }
            });

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setCheckable(true);

                    switch (item.getItemId()) {
                        case R.id.home_page_nav: {
                            Toast.makeText(getApplicationContext(), "Home!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case R.id.camera_page_nav: {
                            runCamera();
                        }
                        break;
                        case R.id.info_page_nav: {
                            runFAQ();
                        }
                        break;
                        case R.id.settings_page_nav: {
                            runSettings();
                        }
                        break;
                    }
                    return true;
                }
            });

        }



        /*
        final CardView camera = findViewById(R.id.camera);
        final CardView cameraFlow = findViewById(R.id.camera_flow);
        final RatingBar ratingLink = findViewById(R.id.ratingBarLink);
        final CardView seeIntroduction = findViewById(R.id.introduction);


        //Set the Main Customized Titles
        String username = prefs.getString("firstName", "user")+ " " + prefs.getString("lastName", "name");
        String profilePicUrl = prefs.getString("profilePic", "");

        if (username == " "){
            username = "Undefined User";
        }
        TextView subtitleText = findViewById(R.id.subtitleText);
        subtitleText.setText(username);
        String urlImage = prefs.getString("profilePic", "");
        ImageView userProfile = findViewById(R.id.userProfilePic);

        if (urlImage != ""){
            Bitmap myBitmap = BitmapFactory.decodeFile(urlImage);
            userProfile.setImageBitmap(myBitmap);
        }






        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity4.class);
                startActivity(intent);

            }
        });

        cameraFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runMainSequence();
            }
        });

        ratingLink.setRating((float) 0.0);
        ratingLink.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingLink.setRating((float) 0.0);
                runHealthCheck();
            }
        });

        seeIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runIntroSequence();
            }
        });




        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // here, Permission is not granted
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, 50);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // here, Permission is not granted
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 50);
        }

         */


        //Set The Text for number of days, and the right card:







        //Also, used SharedPreferences to do login flow!!




    }


    private int getNextAppointmentDate(){
        int earliestApointmentDate = Integer.MAX_VALUE;

        for (int i = 0; i < myAppointments.size(); i++){
            int date = Integer.valueOf(myAppointments.get(i).appointment_date);
            if (myAppointments.get(i).appointment_type == CalendarObject.PHYSICAL_APPOINTMENT){
                if (date < earliestApointmentDate){
                    earliestApointmentDate = date;
                }
            }
        };
        return earliestApointmentDate;
    };

    private int getNextPhotoDueDate(){
        int earliestApointmentDate = Integer.MAX_VALUE;

        for (int i = 0; i < myAppointments.size(); i++){
            int date = Integer.valueOf(myAppointments.get(i).appointment_date);
            if (myAppointments.get(i).appointment_type == CalendarObject.PICTURES_DUE){
                if (date < earliestApointmentDate){
                    earliestApointmentDate = date;
                }
            }
        };
        return earliestApointmentDate;
    };



    private boolean permissionsGranted(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        return true;
    }

    private void getPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // here, Permission is not granted
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, 50);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // here, Permission is not granted
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 50);
        }
    }

    private void runCreateAccount(){
        Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
        startActivity(intent);
    }

    private void runCamera(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        int numberOfDaysUntilCheckIn = prefs.getInt("numberOfDaysUntilCheckIn", 1);
        getPermissions();
        if (permissionsGranted() == false) {
            Toast.makeText(getApplicationContext(), "You Must Grant Permissions to use Camera", Toast.LENGTH_SHORT);
        }
        if (numberOfDaysUntilCheckIn == 0){
                Intent intent = new Intent(getApplicationContext(), PrePhotoAlignerCheckin.class);

                startActivity(intent);
            }
        else{
            Intent intent = new Intent(getApplicationContext(), TooEarlyForPictureNotification.class);
            startActivity(intent);
        }
    }

    private void runMainSequence(){
        Intent intent = new Intent(getApplicationContext(), MainSequence.class);
        startActivity(intent);
    }

    private void runFAQ(){

        Class<FAQ> myClass = FAQ.class;
        Intent intent = new Intent(getApplicationContext(), myClass);
        startActivity(intent);

    }

    private void runSettings(){
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
    }

    private void runIntroSequence() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), FirstPage.class);
        overridePendingTransition(0, 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void runHealthCheck(){
        //What preference should I use?
        Intent intent = new Intent(getApplicationContext(), CheckIn3.class);
        startActivity(intent);
    }






    //Indicators
    /*
    private void setupCheckInRecordIndicators(){
        ImageView[] indicators = new ImageView[7];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        layoutParams.weight = 1;


        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutCheckInIndicators.addView(indicators[i]);
        }
    }
     */


    private void setupCheckInRecordIndicators(){
        String[] daysOfWeek = {"D","L", "M", "M", "J", "V", "S" };
        LinearLayout[] indicators = new LinearLayout[daysOfWeek.length];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        layoutParams.weight = 1;

        //ImageView[] indicators = new ImageView[7];

        for (int i = 0; i < daysOfWeek.length; i++){
            //Create New Layout, then add a new ImageView and TextView
            LinearLayout layoutEntry = new LinearLayout(getApplicationContext());
            layoutEntry.setPadding(5,5,5,5);
            layoutEntry.setOrientation(LinearLayout.VERTICAL);


            //Now, add the appropriate Drawable Image
            ImageView imageToBeAdded = new ImageView(getApplicationContext());

            if (listOfPreviousCheckins.get(i) == true){
                imageToBeAdded.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.circle_with_checkmark
                ));
                imageToBeAdded.setPadding(5, 10, 5, 0);
                imageToBeAdded.setMaxHeight(10);
                imageToBeAdded.setMaxWidth(10);
            }
            else{
                imageToBeAdded.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.open_circle_indicator
                ));
                imageToBeAdded.setPadding(5, 10, 5, 0);
                imageToBeAdded.setMaxHeight(10);
                imageToBeAdded.setMaxWidth(10);
            }

            layoutEntry.addView(imageToBeAdded);

            //Get Text To Add, and Add it to the New Linear Layout
            TextView dayOfWeek = new TextView(getApplicationContext());
            dayOfWeek.setText(daysOfWeek[i]);
            dayOfWeek.setGravity(Gravity.CENTER);
            layoutEntry.addView(dayOfWeek);


            if (i == DayOfWeek){
                layoutEntry.setBackgroundResource(R.drawable.border_background);
            }
            //Add New Linear View to Overall View
            layoutEntry.setLayoutParams(layoutParams);
            layoutCheckInIndicators.addView(layoutEntry);
        }
    }

}

