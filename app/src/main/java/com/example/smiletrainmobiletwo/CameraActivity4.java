package com.example.smiletrainmobiletwo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class CameraActivity4 extends AppCompatActivity implements SurfaceHolder.Callback{

    //Interface Overlay
    ImageView imageView;
    Button btnCamera;

    //Camera Overlay
    private Camera mCamera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Button capture_image;
    private byte[] currentPicture;
    private ArrayList<Bitmap> imageCollection;
    private ArrayList<OnboardingItem> idealPictures;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idealPictures = new ArrayList<>();


        //Starting Content Interface View
        startInterface();

    }



    private void setupPictures(){
        OnboardingItem item1 = new OnboardingItem();
        item1.setTitle("Para poder tomar fotos alineadas");
        item1.setDescription("Agarra el cellular a la altura de su cara");
        item1.setImage(R.drawable.main5);



        idealPictures.add(item1);
    }






    private void startInterface(){
        setContentView(R.layout.activity_camera4);

        //Initialize the Interface Variables & Listeners
        btnCamera = (Button)findViewById(R.id.btnCamera);
        imageView = (ImageView)findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
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
                setNewImage();
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


}