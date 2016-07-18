package com.vuw.project1.riverwatch.ui;

import android.content.Intent;
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

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.Report_functionality.Preview;
import com.vuw.project1.riverwatch.Report_functionality.ReportFormActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportActivity extends AppCompatActivity {
    private Button captureButton;
    private Preview camPreview;
    private SurfaceHolder holder;
    private Camera camera;
    private String imagePath;

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

    }
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
     * Called when the take picture button has been called
     */
    public void takePicture(){
        camera.takePicture(null,null,jpegCallback);
    }

    /**
     * After the image has been saved this method will startm the reportFormActivity with the image and other information
     */
    public void pictureTaken(){
        Intent intent = new Intent(ReportActivity.this,ReportFormActivity.class );
        intent.putExtra("IMAGE_PATH", imagePath);
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
