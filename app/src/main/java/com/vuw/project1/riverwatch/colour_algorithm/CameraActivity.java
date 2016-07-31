package com.vuw.project1.riverwatch.colour_algorithm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.ui.NitrateActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private Button captureButton;
    private StripOverlay stripOverlay;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        camera = getCameraInstance();
        //Camera.Parameters parameters = camera.getParameters();
        //parameters.set("orientation", "portrait");
        //camera.setParameters(parameters);
        //camera.setDisplayOrientation(270);

        stripOverlay = (StripOverlay) findViewById(R.id.stripOverlay);
        relativeLayout=(RelativeLayout) findViewById(R.id.containerImg);
        relativeLayout.setDrawingCacheEnabled(true);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        stripOverlay = (StripOverlay) findViewById(R.id.stripOverlay);
        holder = surfaceView.getHolder();
        holder.addCallback(this);

        // Add a listener to the Capture button
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        camera.takePicture(null, null, picture);
                    }
                }
        );
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

    private Camera.PictureCallback picture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //TODO: setup async task for saving to SD card

            //create new analysis


            Bitmap cameraBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            cameraBitmap = RotateBitmap(cameraBitmap,-90f);

            int wid = cameraBitmap.getWidth();
            int hgt = cameraBitmap.getHeight();

            //need to resize as the camera bitmap is three times two big
            Bitmap resizedBitmap = resizeBitmap(getScreenDimensionsX(), getScreenDimensionsY(), cameraBitmap);
            HashMap<String, Rect> captureRectangles = stripOverlay.getCaptureRectangles();
            Rect leftR = captureRectangles.get("leftCaptureRectangle");
            Rect midR = captureRectangles.get("middleCaptureRectangle");
            Rect rightR = captureRectangles.get("rightCaptureRectangle");

            /*Bitmap left = Bitmap.createBitmap(resizedBitmap, (leftR.left+(leftR.width()/2)), leftR.top+(leftR.height()/2), leftR.width(), leftR.height());
            Bitmap middle = Bitmap.createBitmap(resizedBitmap, (midR.left+(leftR.width()/2)), midR.top+(midR.height()/2), midR.width(), midR.height());
            Bitmap right = Bitmap.createBitmap(resizedBitmap, (rightR.left+(rightR.width()/2)), rightR.top+(rightR.height()/2), rightR.width(), rightR.height());*/

            Bitmap left = Bitmap.createBitmap(resizedBitmap, leftR.left+(leftR.width()/2), leftR.top+(leftR.width()/2), leftR.width(), leftR.height());
            Bitmap middle = Bitmap.createBitmap(resizedBitmap, midR.left+(leftR.width()/2), midR.top+(leftR.width()/2), midR.width(), midR.height());
            Bitmap right = Bitmap.createBitmap(resizedBitmap, rightR.left+(leftR.width()/2), rightR.top+(leftR.width()/2), rightR.width(), rightR.height());

            Analysis analysis = new Analysis();

            //process images
            analysis.processImages(left, middle, right, getApplicationContext());

            //create intent with analysis object

            File pictureFile = getOutputMediaFile(1);
            /*  if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions: " +
                        e.getMessage());
                return;
            }*/
            //write the picture to memory
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
               // Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                //Log.d(TAG, "Error accessing file: " + e.getMessage());
            }

            //start a new activity
            Intent intent = new Intent(CameraActivity.this, NitrateResultsActivity.class);
            intent.putExtra("nitrate", analysis.getNitrate());
            intent.putExtra("nitrite", analysis.getNitrite());
            intent.putExtra("left", left);
            intent.putExtra("middle", middle);
            intent.putExtra("right", right);
            intent.putExtra("stripBitmap", right);
            startActivity(intent);
        }
    };

    private Bitmap resizeBitmap(int newWidth, int newHeight, Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = (float) newWidth/width;
        float scaleHeight = (float) newHeight/height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "NitrateTestPictures");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("NitrateTestPictures", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            // Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null){
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }
        Camera.Parameters parameters = camera.getParameters();
        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        parameters.setPreviewSize(height, width);
        camera.setDisplayOrientation(270);
        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch (Exception e){
            Log.d("temp", "cannot start preview");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public int getScreenDimensionsY(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        System.out.println("screen size: "+ size.y);
        return size.y;
    }

    public int getScreenDimensionsX(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
