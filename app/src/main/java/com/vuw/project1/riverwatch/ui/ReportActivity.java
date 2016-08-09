package com.vuw.project1.riverwatch.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.hardware.Camera;
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

        camera.takePicture(null,null,jpegCallback);
    }

    /**
     * After the image has been saved this method will startm the reportFormActivity with the image and other information
     */
    public void pictureTaken(){
        Intent intent = new Intent(ReportActivity.this,ReportFormActivity.class );
        intent.putExtra("IMAGE_PATH", imagePath);
        com.vuw.project1.riverwatch.Report_functionality.BasicLocation location = new com.vuw.project1.riverwatch.Report_functionality.BasicLocation(this.lastLocation.getLatitude(),this.lastLocation.getLongitude());;
        intent.putExtra("LOCATION",location.toJson());
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
