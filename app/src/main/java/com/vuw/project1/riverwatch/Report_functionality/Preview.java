package com.vuw.project1.riverwatch.Report_functionality;


import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

/**
 * Created by Alex on 17/07/2016.
 */
public class Preview extends SurfaceView implements SurfaceHolder.Callback {

    Camera camera;
    //Constructors
    public Preview(Context context) {
        super(context);
    }
    //As far as I know this is the most commonly used constructor
    public Preview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Preview(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    /**
     * Set the Camera that this view is using to display the image preview
     * @param camera
     */
    public void setCamera(Camera camera){
        getHolder().addCallback(this);
        this.camera = camera;
        requestLayout();
    }

    /**
     * get the best size for the preview to be set to by finding the biggest size the camera
     * can show
     * @param parameters
     * @return
     */
    private Camera.Size getBestPreviewSize(Camera.Parameters parameters) {
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


    //SurfaceHolder callback methods
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        if (getHolder().getSurface() == null) {
            return;
        }
        if (camera != null) {
            try {
                camera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }

            final Camera.Parameters parameters = camera.getParameters();
            final Camera.Size previewSize = getBestPreviewSize(parameters);
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            parameters.setPictureSize(previewSize.width, previewSize.height);
            Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

            if (display.getRotation() == Surface.ROTATION_0) {
                camera.setDisplayOrientation(90);
                parameters.setRotation(90);
            }

            if (display.getRotation() == Surface.ROTATION_270) {
                camera.setDisplayOrientation(180);
                parameters.setRotation(180);
            }
            try{
                camera.setParameters(parameters);
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            }catch(IOException e){
                Log.d("cameraPreview", "Error starting camera preview: " + e.getMessage());
            }

        }
    }


    /**
     * This method is called by camera activity when the phone
     * changes orientation.
     * */
    public void rotated(String orientation ) {
        if(camera == null ){
            return;
        }
        final Camera.Parameters parameters = camera.getParameters();
        final Camera.Size previewSize = getBestPreviewSize(parameters);
        parameters.setPreviewSize(previewSize.width, previewSize.height);

        //phone is landscape left
        if (orientation.equals("Landscape") ) {
            parameters.setRotation(0);
        }

        //phone is upright
        if (orientation.equals("Portrait")) {
            parameters.setRotation(90);
        }

        //phone is landscape right
        if (orientation.equals("Landscape_Right") ) {
            parameters.setRotation(180);
        }

        //phone is upside down
        if (orientation.equals("Portrait_Down") ) {
            parameters.setRotation(270);
        }

        camera.setParameters(parameters);
        camera.startPreview();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (camera != null) {
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
        }
    }

    public void onPause() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            getHolder().removeCallback(this);
            camera = null;
        }
    }
}
