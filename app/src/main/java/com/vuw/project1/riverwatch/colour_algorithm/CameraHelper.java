package com.vuw.project1.riverwatch.colour_algorithm;


import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by George on 14/09/2016.
 */
public class CameraHelper {
    private WindowManager wm;
    private String imagePath;

    public CameraHelper(WindowManager wm) {
        imagePath = "";
        this.wm = wm;
    }

    /**
     *  Get the proper orientation of the device, so the preview
     *  is displayed correctly
     * @param camera the instance of the camera
     * @param info the camera info
     * @return the correct rotation to set parameters.setRotation()
     */
    public int getCorrectCameraOrientation(Camera camera, Camera.CameraInfo info) {

        int rotation = wm.getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result = 0;

        if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        }
        else{
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
    }


    /**
     * Hides the action bar and status bar
     * @param ab the action bar to be hidden
     * @param window the window to hide it from
     */
    public void hideBars(ActionBar ab, Window window) {
        // hide the status bar
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            View decorView = window.getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        // hide the action bar
        ActionBar actionBar = ab;
        if (actionBar != null) {
            actionBar.hide();
        }
    }


    /**
     *  Create a file for saving an image or video
     * @param type
     * @return
     */
    public File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.


        File mediaFile;
        if (type == 1){
            imagePath = generateImagePath();
            mediaFile = new File(imagePath);

        } else {
            return null;
        }

        return mediaFile;
    }


    private String generateImagePath(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "NitrateTestPictures");

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("NitrateTestPictures", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imagePath = mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg";
        return imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    /**
     * returns the optimal preview size for the camera
     * @param parameters the camera parametes from Camera.getParameters()
     * @return optimal preview size for the camera
     */
    public Camera.Size getOptimalPreviewSize(Camera.Parameters parameters){
        final List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

        //set initial size
        Camera.Size bestSize = sizeList.get(0);

        //search through sizelist to find biggest size
        for (Camera.Size size : sizeList) {
            if ((size.width * size.height) > (bestSize.width * bestSize.height)) {
                bestSize = size;
            }
        }
        return bestSize;
    }

    //*****************************//
    //  unused methods that might  //
    //  be useful in the future    //
    //*****************************//

    public Bitmap resizeBitmap(int newWidth, int newHeight, Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = (float) newWidth/width;
        float scaleHeight = (float) newHeight/height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public int getScreenDimensionsY(WindowManager wm){
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public int getScreenDimensionsX(WindowManager wm){
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
