package com.vuw.project1.riverwatch.colour_algorithm;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.vision.CameraSource;
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
    private CameraHelper helper;

    private static final String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        camera = getCameraInstance();

        // setup camera helper
        helper = new CameraHelper(this.getWindowManager());

        // Add a listener to the Capture button
        captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        camera.autoFocus(new Camera.AutoFocusCallback() {
                            @Override
                            public void onAutoFocus(boolean success, Camera camera) {
                                camera.takePicture(null, null, picture);
                            }
                        });

                    }
                }
        );
    }

    @Override
    protected void onResume(){
        super.onResume();
        helper.hideBars(getActionBar(), getWindow());
        stripOverlay = (StripOverlay) findViewById(R.id.stripOverlay);
        relativeLayout=(RelativeLayout) findViewById(R.id.containerImg);
        relativeLayout.setDrawingCacheEnabled(true);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        stripOverlay = (StripOverlay) findViewById(R.id.stripOverlay);
        holder = surfaceView.getHolder();
        holder.addCallback(this);
    }



    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
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

    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
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
            e.printStackTrace();
        }

        // get info about camera
        Camera.Parameters parameters = camera.getParameters();
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);

        // set preview size to the optimal dimension
        Camera.Size previewSize = helper.getOptimalPreviewSize(parameters);
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        parameters.setPictureSize(previewSize.width, previewSize.height);

        // handle rotation
        int rotation = helper.getCorrectCameraOrientation(camera, info);
        camera.setDisplayOrientation(rotation);
        parameters.setRotation(rotation);

        // handle camera focusing
        //TODO: this
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        // start preview with new settings
        try {
            camera.setParameters(parameters);
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    public boolean onTouchEvent(final MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_UP){
            // when the user touches focus the middle strip
            focusMiddleArea();
        }

        return true;
    }


    /**
     * called to fouc the middle strip
     */
    private void focusMiddleArea(){
        Camera.Parameters parameters = camera.getParameters();

        // create the rectangle and add it to the list of focus areas
        ArrayList<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
        Rect rect1 = new Rect(-150, -400, 150, 400);
        focusAreas.add(new Camera.Area(rect1, 1000));

        parameters.setFocusAreas(focusAreas);
        camera.setParameters(parameters);

        // focus the middle strip
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
            }
        });
    }


    /**
     *  method to capture the image and process the algorithm
     */
    private Camera.PictureCallback picture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //TODO: setup async task for saving to SD card

            //create new analysis
            Bitmap cameraBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            HashMap<String, Rect> captureRectangles = stripOverlay.getCaptureRectangles();
            Rect leftR = captureRectangles.get("leftCaptureRectangle");
            Rect midR = captureRectangles.get("middleCaptureRectangle");
            Rect rightR = captureRectangles.get("rightCaptureRectangle");

            int statusBarHeight = getStatusBarHeight();

            Bitmap left = Bitmap.createBitmap(cameraBitmap, leftR.left, leftR.top+statusBarHeight, leftR.width(), leftR.height());
            Bitmap middle = Bitmap.createBitmap(cameraBitmap, midR.left, midR.top+statusBarHeight, midR.width(), midR.height());
            Bitmap right = Bitmap.createBitmap(cameraBitmap, rightR.left, rightR.top+statusBarHeight, rightR.width(), rightR.height());

            Analysis analysis = new Analysis();

            //process images
            analysis.processImages(left, middle, right, getApplicationContext());

            //create intent with analysis object
            File pictureFile = helper.getOutputMediaFile(1);
            String imagePath = helper.getImagePath();

            //write the picture to memory
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }

            //start a new activity
            Intent intent = new Intent(CameraActivity.this, ResultsTabbedActivity.class);
            intent.putExtra("image_path", imagePath);
            intent.putExtra("nitrate", analysis.getNitrate());
            intent.putExtra("nitrite", analysis.getNitrite());
            intent.putExtra("left", left);
            intent.putExtra("middle", middle);
            intent.putExtra("right", right);
            startActivity(intent);
        }

        /**
         *  Helper method for picture method
         * @return size of the status bar
         */
        private int getStatusBarHeight() {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }
    };
}
