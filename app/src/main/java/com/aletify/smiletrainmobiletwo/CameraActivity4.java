package com.aletify.smiletrainmobiletwo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

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
    private Bitmap currentPictureInImageView;
    private ArrayList<byte[]> imageCollection;
    private ArrayList<OnboardingItem> idealPictures;

    //Other
    private LinearLayout layoutOnboardingIndicators;
    private int indexOfPictures;
    private TextView startingTitle;
    private TextView startingSubtitle;
    private ImageView idealImageView;
    private Button buttonStartAction;
    ArrayList<Uri> files;

    //ImageManipulation:
    private ImageView largeImage;
    private ImageView smallImage;

    private TextView imageLabel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        idealPictures = new ArrayList<>();
        imageCollection = new ArrayList<>();
        files = new ArrayList<Uri>();

        //Starting Content Interface View
        //startInterface();
        indexOfPictures = 0;
        setupPictures();
        startGetStarted();
    }

    protected void onDestroy(){
        super.onDestroy();
        clearAll();
        Toast.makeText(getApplicationContext(), "Clearing Images from Aletify", Toast.LENGTH_SHORT);
    }


    //From https://stackoverflow.com/questions/30340591/changing-an-imageview-to-black-and-white
    private void setBW(ImageView iv){

        float brightness = 10; // change values to suite your need

        float[] colorMatrix = {
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0, 0, 0, 1, 0
        };

        ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        iv.setColorFilter(colorFilter);
    }


    private void setupPictures(){
        OnboardingItem item1 = new OnboardingItem();
        item1.setTitle("Front of Mouth");
        item1.setImage(R.drawable.reference_1);

        OnboardingItem item2 = new OnboardingItem();
        item2.setTitle("Image 2");
        item2.setImage(R.drawable.reference_2);


        OnboardingItem item3 = new OnboardingItem();
        item3.setTitle("Left of Mouth");
        item3.setImage(R.drawable.reference_3);

        OnboardingItem item4 = new OnboardingItem();
        item4.setTitle("Top of Mouth");;
        item4.setImage(R.drawable.reference_4);

        OnboardingItem item5 = new OnboardingItem();
        item5.setTitle("Bottom of Mouth");
        item5.setImage(R.drawable.reference_5);

        OnboardingItem item6 = new OnboardingItem();
        item6.setTitle("Front of Face");
        item6.setImage(R.drawable.reference_6);

        OnboardingItem item7 = new OnboardingItem();
        item7.setTitle("Front of Face");
        item7.setImage(R.drawable.reference_7);


        idealPictures.add(item1);
        idealPictures.add(item2);
        idealPictures.add(item3);
        idealPictures.add(item4);
        idealPictures.add(item5);
        idealPictures.add(item6);
        idealPictures.add(item7);

    }

    private void startGetStarted(){
        setContentView(R.layout.picture_full_view);

        ImageView reference2 = (ImageView) findViewById(R.id.reference2);
        ImageView reference3 = (ImageView) findViewById(R.id.reference3);
        ImageView reference4 = (ImageView) findViewById(R.id.reference4);
        ImageView reference5 = (ImageView) findViewById(R.id.reference5);
        ImageView reference6 = (ImageView) findViewById(R.id.reference6);
        ImageView reference7 = (ImageView) findViewById(R.id.reference7);

        setBW(reference2);
        setBW(reference3);
        setBW(reference4);
        setBW(reference5);
        setBW(reference6);
        setBW(reference7);

        buttonStartAction = findViewById(R.id.buttonStartAction);
        buttonStartAction.setText("siguiente");
        buttonStartAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }
        });
    }

    @Override
    public void onBackPressed() {
        final String[] options = {"Si", "No"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vuelve al menú principal?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Si")) {
                    clearAll();
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
            startingSubtitle.setText("Now, you can upload your images to you doctor via the app");
            buttonStartAction.setText("Upload to Doctor");
            buttonStartAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        uploadToFirebase();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Upload Failed to Run", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    protected void storeImageAndReturnUrl(int imageNumber){
        //Bitmap imgBitmap = BitmapFactory.decodeByteArray(currentPicture, 0, currentPicture.length);
        Random generator = new Random();
        int n = generator.nextInt();
        String randomTitle = Integer.toString(n);
        String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),currentPictureInImageView,idealPictures.get(imageNumber).getTitle() + randomTitle,null);
        Uri imgBitmapUri = Uri.parse(imgBitmapPath);
        files.add(imgBitmapUri);
    }

    private void uploadImage(Uri filePath) {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image

            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(
                        UploadTask.TaskSnapshot taskSnapshot) {

                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(
                        UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }


    protected void uploadToFirebase() throws FileNotFoundException {
        //Part 1, upload files
        Toast.makeText(getApplicationContext(),"Starting Upload Process", Toast.LENGTH_SHORT).show();
        //Uri file = Uri.fromFile(new File(String.valueOf(files.get(0))));
        //uploadImage(file);



        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        //Part 2, create the reference in this user's account
        Uri fileURI = Uri.fromFile(new File(String.valueOf(files.get(0))));

        Toast.makeText(getApplicationContext(), "File Path is: " + fileURI.getPath(), Toast.LENGTH_SHORT).show();
        StorageReference riversRef = storageRef.child("images/"+fileURI.getLastPathSegment());
        File fileFile = new  File(fileURI.getPath());
        InputStream stream = new FileInputStream(fileFile);
        if (stream.equals(null)){
            Toast.makeText(getApplicationContext(), "STREAM IS NULL", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "STREAM IS NOT NULL", Toast.LENGTH_SHORT).show();
        }
        /*
        UploadTask uploadTask = riversRef.putStream(stream);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                clearAll();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
         */

    }


    protected void openwhatsapp(String message){

        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);

        ///
        shareIntent.setType("image/png");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "My Images");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Images of Teeth");
        startActivity(Intent.createChooser(shareIntent, "Share this"));
        clearAll();
    }

    protected void clearAll(){
        for (int i = 0; i < files.size(); i++){
            File file = new File(String.valueOf(files.get(i)));
            boolean deleted = file.delete();
        }
        Toast.makeText(getApplicationContext(), "Images Deleted from your Machine", Toast.LENGTH_SHORT).show();
    }


    private void startInterface(){
        setContentView(R.layout.activity_camera4);
        //Initialize the Interface Variables & Listeners
        btnCamera = (Button)findViewById(R.id.btnCamera);
        btnContinue = (Button)findViewById(R.id.btnConfirmImage);
        imageView = (ImageView)findViewById(R.id.imageView);
        originalImage = (ImageView)findViewById(R.id.originalImage);

        setNewImage();

        OnboardingItem nextImage = idealPictures.get(indexOfPictures);
        originalImage.setImageResource(nextImage.getImage());

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
                //imageCollection.add(currentPicture);
                storeImageAndReturnUrl(indexOfPictures);
                indexOfPictures += 1;

                if (indexOfPictures < idealPictures.size()){
                    setContentView(R.layout.time_to_get_started);
                    currentPictureInImageView.recycle();
                    currentPictureInImageView = null;
                    startGetStarted();
                    startCamera();
                }
                else{
                    setContentView(R.layout.time_to_get_started);
                    Button finishButton = findViewById(R.id.buttonStartAction);
                    finishButton.setText("Submit Images!");
                    finishButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "Images Uploaded", Toast.LENGTH_SHORT).show();
                            Intent goHome = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(goHome);
                        }
                    });

                }

                //showIdealImage();

            }
        });
    }

    private void startCamera(){
        setContentView(R.layout.activity_camera__capture);
        //Initialize the Camera Variables & Listeners
        largeImage = (ImageView) findViewById(R.id.camera_large_overlay);
        smallImage = (ImageView) findViewById(R.id.camera_small_overlay);

        //Set Images Here Specifically

        //Now Set Visibility
        largeImage.setVisibility(View.GONE);
        smallImage.setVisibility(View.VISIBLE);

        //Now Set Title of Image
        OnboardingItem nextImage = idealPictures.get(indexOfPictures);
        imageLabel = (TextView) findViewById(R.id.camera_top_bar);
        String label = "Imagen " + Integer.valueOf(indexOfPictures + 1) + " de " + Integer.valueOf(idealPictures.size());
        imageLabel.setText(label);

        largeImage.setImageResource(nextImage.getImage());
        smallImage.setImageResource(nextImage.getImage());

        largeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smallImage.setVisibility(View.VISIBLE);
                largeImage.setVisibility(View.GONE);
            }
        });
        smallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                largeImage.setVisibility(View.VISIBLE);
                smallImage.setVisibility(View.GONE);
            }
        });

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
            //Jump to Home
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Could Not Open Camera", Toast.LENGTH_LONG);
            e.printStackTrace();
        }


    }


    protected void setNewImage(){
        Bitmap bitmap = BitmapFactory.decodeByteArray(currentPicture, 0, currentPicture.length);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        currentPictureInImageView = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        imageView.setImageBitmap(currentPictureInImageView);
        //originalImage.setImageBitmap(currentPictureInImageView);
        bitmap.recycle();
        scaledBitmap.recycle();
        bitmap = null;
        scaledBitmap = null;
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        byte[] image = data.getExtras().getByteArray("image_arr");
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0,
                image.length);

        imageView.setImageBitmap(bmp);
    }
     */


    ///////CAMERA ORIGINS////////////
    private void capture() {
        mCamera.takePicture(null, null, null, new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Toast.makeText(getApplicationContext(), "Picture Taken",
                        Toast.LENGTH_SHORT).show();

                currentPicture = data;
                if (camera != null){
                    camera.release();
                    mCamera = null;
                }
                startInterface();
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
}