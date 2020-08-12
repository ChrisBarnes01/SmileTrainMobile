package com.example.smiletrainmobiletwo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class CameraActivity4 extends AppCompatActivity implements SurfaceHolder.Callback{

    //Interface Overlay
    ImageView imageView;
    ImageView originalImage;
    Button btnCamera;
    Button btnContinue;

    //Camera Overlay
    private Camera mCamera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Button capture_image;
    private byte[] currentPicture;
    private ArrayList<byte[]> imageCollection;
    private ArrayList<OnboardingItem> idealPictures;

    //Other
    private LinearLayout layoutOnboardingIndicators;
    private int indexOfPictures;
    private TextView startingTitle;
    private TextView startingSubtitle;
    private ImageView idealImageView;
    private Button buttonStartAction;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        idealPictures = new ArrayList<>();
        imageCollection = new ArrayList<>();

        //Starting Content Interface View
        //startInterface();
        indexOfPictures = 0;
        setupPictures();
        startGetStarted();

    }




    private void setupPictures(){
        OnboardingItem item1 = new OnboardingItem();
        item1.setTitle("Front of Mouth");
        item1.setImage(R.drawable.front);
        item1.setOverlayImage(R.drawable.front);

        OnboardingItem item2 = new OnboardingItem();
        item2.setTitle("Right of Mouth");
        item2.setImage(R.drawable.right);

        item2.setOverlayImage(R.drawable.right);

        OnboardingItem item3 = new OnboardingItem();
        item3.setTitle("Left of Mouth");
        item3.setImage(R.drawable.left);
        item3.setOverlayImage(R.drawable.left);

        OnboardingItem item4 = new OnboardingItem();
        item4.setTitle("Top of Mouth");
        item4.setImage(R.drawable.top);
        item4.setOverlayImage(R.drawable.top);

        OnboardingItem item5 = new OnboardingItem();
        item5.setTitle("Bottom of Mouth");
        item5.setImage(R.drawable.bottom);
        item5.setOverlayImage(R.drawable.bottom);

        idealPictures.add(item1);
        idealPictures.add(item2);
        idealPictures.add(item3);
        idealPictures.add(item4);
        idealPictures.add(item5);

    }

    private void startGetStarted(){
        setContentView(R.layout.time_to_get_started);
        layoutOnboardingIndicators = findViewById(R.id.startedMainIndicators);
        startingTitle = findViewById(R.id.startingTextMain);
        startingSubtitle = findViewById(R.id.startingTextSubtitle);
        idealImageView = findViewById(R.id.startingImage);

        idealImageView.setImageResource(R.drawable.camera_capture);
        buttonStartAction = findViewById(R.id.buttonStartAction);
        buttonStartAction.setText("Next");
        buttonStartAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIdealImage();
            }
        });
        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(indexOfPictures);


    }

    @Override
    public void onBackPressed() {
        final String[] options = {"Yes", "No"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Go back to Main Menu?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Yes")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else if (options[which].equals("No")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void showIdealImage(){
        if (indexOfPictures < idealPictures.size()){
            OnboardingItem nextImage = idealPictures.get(indexOfPictures);
            startingTitle.setText(nextImage.getTitle());
            startingSubtitle.setText("");
            idealImageView.setImageResource(nextImage.getImage());


            buttonStartAction.setText("Take Photo");
            buttonStartAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startCamera();
                }
            });
        }
        else{
            startingTitle.setText("We're done!");
            startingSubtitle.setText("Now, you can submit your images to you doctor via WhatsApp");
            buttonStartAction.setText("Submit to WhatsApp");
            buttonStartAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openwhatsapp("Hello We are getting sent");
                }
            });
        }


    }

    protected void openwhatsapp(String message){


        /*
        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }*/



        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        //PT 1
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(imageCollection.get(0), 0, imageCollection.get(0).length);
        String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),imgBitmap,idealPictures.get(0).getTitle(),null);
        Uri imgBitmapUri = Uri.parse(imgBitmapPath);
        //shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
        //PT 2
        Bitmap img2Bitmap = BitmapFactory.decodeByteArray(imageCollection.get(1), 0, imageCollection.get(1).length);
        String img2BitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),img2Bitmap,idealPictures.get(1).getTitle(),null);
        Uri img2BitmapUri = Uri.parse(img2BitmapPath);
        //shareIntent.putExtra(Intent.EXTRA_STREAM,img2BitmapUri);
        //PT 3
        Bitmap img3Bitmap = BitmapFactory.decodeByteArray(imageCollection.get(2), 0, imageCollection.get(2).length);
        String img3BitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),img3Bitmap,idealPictures.get(2).getTitle(),null);
        Uri img3BitmapUri = Uri.parse(img3BitmapPath);

        //PT 4
        Bitmap img4Bitmap = BitmapFactory.decodeByteArray(imageCollection.get(3), 0, imageCollection.get(3).length);
        String img4BitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),img4Bitmap,idealPictures.get(3).getTitle(),null);
        Uri img4BitmapUri = Uri.parse(img4BitmapPath);

        //PT 3
        Bitmap img5Bitmap = BitmapFactory.decodeByteArray(imageCollection.get(4), 0, imageCollection.get(4).length);
        String img5BitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),img5Bitmap,idealPictures.get(4).getTitle(),null);
        Uri img5BitmapUri = Uri.parse(img5BitmapPath);


//
        ArrayList<Uri> files = new ArrayList<Uri>();
        files.add(imgBitmapUri);  // uri of my bitmap image1
        files.add(img2BitmapUri); // uri of my bitmap image2
        files.add(img3BitmapUri); // uri of my bitmap image2
        files.add(img4BitmapUri);
        files.add(img5BitmapUri);

        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);

        ///



        shareIntent.setType("image/png");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "My Custom Text ");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject text");
        startActivity(Intent.createChooser(shareIntent, "Share this"));
    }


    private void startInterface(){
        setContentView(R.layout.activity_camera4);
        //Initialize the Interface Variables & Listeners
        btnCamera = (Button)findViewById(R.id.btnCamera);
        btnContinue = (Button)findViewById(R.id.btnConfirmImage);
        imageView = (ImageView)findViewById(R.id.imageView);
        setNewImage();

        //originalImage = (ImageView)findViewById(R.id.originalImage);
        //originalImage.setImageResource(idealPictures.get(indexOfPictures).getImage());



        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }
        });


        btnContinue.setText("Confirm Picture");
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indexOfPictures += 1;
                imageCollection.add(currentPicture);

                setContentView(R.layout.time_to_get_started);
                startGetStarted();
                showIdealImage();

            }
        });
    }

    private void startCamera(){
        setContentView(R.layout.activity_camera__capture);
        //Initialize the Camera Variables & Listeners
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        capture_image = (Button) findViewById(R.id.capture_image);
        capture_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(CameraActivity4.this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        try {
            mCamera = Camera.open();
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void setNewImage(){
        Bitmap bitmap = BitmapFactory.decodeByteArray(currentPicture, 0, currentPicture.length);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        imageView.setImageBitmap(rotatedBitmap);
        bitmap.recycle();
        scaledBitmap.recycle();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        // imageView.setImageBitmap(bitmap);
        byte[] image = data.getExtras().getByteArray("image_arr");
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0,
                image.length);

        imageView.setImageBitmap(bmp);
    }


    ///////CAMERA ORIGINS////////////
    private void capture() {
        mCamera.takePicture(null, null, null, new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Toast.makeText(getApplicationContext(), "Picture Taken",
                        Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(getApplicationContext(), CameraActivity4.class);
                //intent.putExtra("image_arr", data);
                //setResult(RESULT_OK, intent);

                currentPicture = data;
                if (camera != null){
                    camera.release();
                    mCamera = null;
                }
                startInterface();
                /*
                camera.stopPreview();
                if (camera != null) {
                    camera.release();
                    mCamera = null;
                }
                Toast.makeText(getApplicationContext(), "Picture Complete",
                        Toast.LENGTH_SHORT).show();
                //startActivity(intent);
*/

            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.e("Surface Changed", "format   ==   " + format + ",   width  ===  "
                + width + ", height   ===    " + height);
        mCamera.setDisplayOrientation(90);

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("Surface Created", "");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("Surface Destroyed", "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
        }
    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[idealPictures.size()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView)layoutOnboardingIndicators.getChildAt(i);
            if (i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active)
                );
            }
            else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive)
                );
            }
        }


        /*
        if (index == onboardingAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Start");
        }
        else{
            buttonOnboardingAction.setText("Next");
        }*/
    }







}