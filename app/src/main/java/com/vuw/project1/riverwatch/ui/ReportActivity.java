package com.vuw.project1.riverwatch.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.hardware.Camera;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.Report_functionality.Preview;
import com.vuw.project1.riverwatch.Report_functionality.ReportFormActivity;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Button captureButton;
    private Preview camPreview;
    private SurfaceHolder holder;
    private Camera camera;
    private String imagePath;


    private OrientationEventListener mOrientationListener;
    private int prevRotation;
    private int currentRotation = 90;
    private String phoneOrientation;

    private GoogleApiClient mGoogleApiClient;
    private android.location.Location lastLocation;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        //setup the Camera and preview
        camPreview = (Preview) findViewById(R.id.preview);
        safeCameraOpen();
        holder = camPreview.getHolder();
        holder.addCallback(camPreview);
        camPreview.setCamera(camera);
        camPreview.setKeepScreenOn(true);
        //setup the button
        captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        //Setup orientation listener
        final FrameLayout layout = (FrameLayout)findViewById(R.id.activity_report);

        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                initOrientationListener();
            }
        });

        //setup the google map api
        createLocationRequest();
        createGoogleMapAPI();
        mGoogleApiClient.connect();
    }


    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onStop(){
        super.onStop();
        // remove the instance of this Activity once it's moved on to the SubmissionDetails Activity
        finish();
    }
    //------------------------------------------Google API methods for location services----------------------------------------------
    //------------------------------------------Google API methods for location services----------------------------------------------
    //------------------------------------------Google API methods for location services----------------------------------------------

    private void createGoogleMapAPI() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    protected void startLocationUpdates() {
        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, locationRequest, this);
        }catch(SecurityException e){
            Toast.makeText(this, "Location services not enabled", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        ensureLocationServicesOn();
        Toast.makeText(this, "Connected to Location services", Toast.LENGTH_SHORT).show();
        try{
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }catch(SecurityException e){
            Toast.makeText(this, "Location services not enabled", Toast.LENGTH_SHORT).show();
        }


        //starts checking for location updates
        startLocationUpdates();
    }

    private boolean ensureLocationServicesOn() {
        if (!((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        } else if (lastLocation == null) {
            Toast.makeText(this, "No Location please wait", Toast.LENGTH_SHORT).show();
        }
        else{
            return true;
        }
        return false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult conRes){
        Toast.makeText(this, "Connection failed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int something){
        Toast.makeText(this, "Connection suspended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location){
        lastLocation = location;
    }


    //--------------------------------End of Google API services methods--------------------------------
    //--------------------------------End of Google API services methods--------------------------------
    //--------------------------------End of Google API services methods--------------------------------
    /**
     * Safely creates a new Camera instance and assigns it to the camera field
     * @return
     */
    private boolean safeCameraOpen() {
        boolean qOpened = false;
        try {
            releaseCameraAndPreview();
            camera = Camera.open();
            qOpened = (camera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }
        return qOpened;
    }

    /**
     * resets the camera TO NULL in both this activity and the preview object
     */
    private void releaseCameraAndPreview() {
        camPreview.setCamera(null);
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    /**
     * Called when the take picture button has been pressed
     */
    public void takePicture(){
        if( lastLocation == null){
            Toast.makeText(this, "No Location please wait", Toast.LENGTH_SHORT).show();
            return;
        }

        mOrientationListener.disable();
        camera.takePicture(null,null,jpegCallback);
    }

    /**
     * After the image has been saved this method will startm the reportFormActivity with the image and other information
     */
    public void pictureTaken(){
        Intent intent = new Intent(ReportActivity.this,ReportFormActivity.class );
        intent.putExtra("IMAGE_PATH", imagePath);
        intent.putExtra("LATITUDE",String.valueOf(lastLocation.getLatitude()));
        intent.putExtra("LONGITUDE",String.valueOf(lastLocation.getLongitude()));
        startActivity(intent);
    }
    /*
        Anonymous inner class used in takePicture to control the callback when the image has been succesfully saved and the application can move onto the ReportFormActivity
     */
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            SaveImageTask st = new SaveImageTask();
            st.execute(data);
            camPreview.setCamera(camera);
        }
    };

    //not completely sure what this is doing
    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);

        //safeToTakePic = true;
        this.imagePath = file.getAbsolutePath();
    }

    private void initOrientationListener(){
        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                setOrientation(orientation);
            }
        };

        if (mOrientationListener.canDetectOrientation() == true) {
            mOrientationListener.enable();
        } else {
            mOrientationListener.disable();
        }
    }

    private void setOrientation(int orientationDegrees) {
        int tempOrientation = currentRotation;

        int sensitivity = 10;//degrees of orientation to start change

        //natural device orientation
        if(orientationDegrees > 360-sensitivity || orientationDegrees <= sensitivity ){
            currentRotation = 90;
            phoneOrientation = "Portrait";
        }
        //on its side to the right
        else if(orientationDegrees > 90 - sensitivity && orientationDegrees <= 90 + sensitivity){
            currentRotation = 180;
            phoneOrientation = "Landscape_Right";
        }
        //upside down
        else if(orientationDegrees > 180 - sensitivity && orientationDegrees <= 180 + sensitivity){
            currentRotation = 270;
            phoneOrientation = "Portrait_Down";
        }
        //on its side to the left
        else if(orientationDegrees > 270 - sensitivity && orientationDegrees <= 270 + sensitivity){
            currentRotation = 0;
            phoneOrientation = "Landscape";
        }

        //if the orientation has changed update the camera preview and animate button
        if(tempOrientation != currentRotation){
            prevRotation = tempOrientation;
            camPreview.rotated(phoneOrientation);
            animate();
        }
    }

    private void animate() {
        if(prevRotation == currentRotation)return;
        final Button camButton = (Button) findViewById(R.id.button_capture);

        RotateAnimation rotate =  getButtonOrientation(prevRotation, currentRotation);
        rotate.setDuration(150);

        camButton.startAnimation(rotate);
        rotate.setFillAfter(true);
    }

    public RotateAnimation getButtonOrientation(int oldOr, int newOr){
        boolean reverse = false;

        int from = 0;
        int to = 0;

        //portrait to landscape
        if(oldOr == 90 && newOr == 0){
            from = 0;
            to = 90;
        }
        //landscape to portrait
        if(oldOr == 0  && newOr == 90){
            from = 0;
            to = 90;
            reverse = true;

        }
        //portrait to right landscape
        if(oldOr == 90 && newOr == 180){
            from = 270;
            to = 360;
            reverse = true;
        }
        //right landscape to portrait
        if(oldOr == 180 && newOr == 90){
            from = 270;
            to = 360;
        }

        //right landscape to upside down
        if(oldOr == 180 && newOr == 270){
            from = 270;
            to = 180;
        }

        //upside down to right landscape
        if(oldOr == 270 && newOr == 180){
            from = 270;
            to = 180;
            reverse = true;
        }

        //upside down to right landscape
        if(oldOr == 270 && newOr == 0){
            from = 180;
            to = 90;
        }

        //upside down to right landscape
        if(oldOr == 0 && newOr == 270){
            from = 180;
            to = 90;
            reverse = true;
        }

        RotateAnimation rotate = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);

        if(reverse){
            rotate.setInterpolator(new ReverseInterpolator());
        }
        return rotate;
    }

    class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float paramFloat) {
            return Math.abs(paramFloat -1f);
        }
    }
    /**
     * Class to save image data to the SD card in the background
     **/
    private class SaveImageTask extends AsyncTask<byte[], Void, Void> {

        @Override
        protected Void doInBackground(byte[]... data) {
            FileOutputStream outStream;

            // Write to SD Card
            try {
                File sdCard = Environment.getExternalStorageDirectory();

                File dir = new File(sdCard.getAbsolutePath() + "/RiverWatch Images");
                dir.mkdirs();

                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);

                outStream = new FileOutputStream(outFile);
                outStream.write(data[0]);
                outStream.flush();
                outStream.close();

                refreshGallery(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(Void result) {

            pictureTaken();
        }
    }
}
