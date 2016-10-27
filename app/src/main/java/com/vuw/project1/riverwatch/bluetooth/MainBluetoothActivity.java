package com.vuw.project1.riverwatch.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.service.App;
import com.vuw.project1.riverwatch.service.NetworkChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.GregorianCalendar;
import java.util.List;

public class MainBluetoothActivity extends BlunoLibrary implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private App app;

    private Button buttonScan;
    private Button buttonTest;
    private Button buttonRetrieve;
    private Button buttonStatus;
    private Button buttonCalibrate;

    private TextView serialReceivedText;

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private String allData = "";

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Log.i(TAG, mLastLocation.toString());
        }
    }

    protected synchronized void buildGoogleApiClient() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:

                Intent intent = new Intent(MainBluetoothActivity.this, MainCalibrateActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(myToolbar);

        onCreateProcess();														//onCreate Process by BlunoLibrary
        app = new App();
        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        buttonTest = (Button) findViewById(R.id.buttonTest);		//initial the button for sending the data
        buttonTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                serialReceivedText.setText("");
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                serialSend("Test");

            }
        });

        buttonRetrieve = (Button) findViewById(R.id.buttonRetrieve);		//initial the button for sending the data
        buttonRetrieve.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                serialReceivedText.setText("");
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                serialSend("RetrieveData");
            }
        });

        buttonStatus = (Button) findViewById(R.id.buttonStatus);		//initial the button for sending the data
        buttonStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                serialReceivedText.setText("");
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                serialSend("Status");
            }
        });

        buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
            }
        });

        serialReceivedText=(TextView) findViewById(R.id.serialReveicedText);	//initial the EditText of the received data

        buildGoogleApiClient();
        mGoogleApiClient.connect();

    }


    protected void onResume(){
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();														//onResume Process by BlunoLibrary
        findViewById(R.id.progressBar).setVisibility(View.GONE);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
        findViewById(R.id.progressBar).setVisibility(View.GONE);

    }

    protected void onStop() {
        super.onStop();
        onStopProcess();														//onStop Process by BlunoLibrary
        findViewById(R.id.progressBar).setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }
    @Override
    public void onConnectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        switch (theConnectionState) {											//Four connection state
            case isConnected:
                buttonScan.setText("Connected");
                buttonStatus.setEnabled(true);
                buttonRetrieve.setEnabled(true);
                buttonTest.setEnabled(true);
                findViewById(R.id.progressBar).setVisibility(View.GONE);

                break;
            case isConnecting:
                buttonScan.setText("Connecting");
                break;
            case isToScan:
                buttonScan.setText("Scan");
                break;
            case isScanning:
                buttonScan.setText("Scanning");
                break;
            case isDisconnecting:
                buttonScan.setText("isDisconnecting");
                break;
            default:
                break;
        }
    }

    public void onSerialReceived(String data, final Context mainContext) {                            //Once connection data received, this function will be called

        findViewById(R.id.progressBar).setVisibility(View.GONE);

        serialReceivedText.append(data);	//append the text into the EditText
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        //((ScrollView)serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);

        allData += data;

        if (!allData.contains("[dataend]")) {
            //Log.i(TAG, "Current Message:  "+message);
        } else if (allData.contains("[dataend]")) {

            final JSONObject json = WaterQualityCommands.formatRetiredData(allData);

            if (json != null) {
                try {
                    //Log.i(TAG, "Formatted message  " + json.toString());

                    String status = json.getString("status");

                    switch (status) {
                        case "complete":
                            //Handles on receiving data.
                            android.location.Location location = ((MainBluetoothActivity) mainContext).getLocation();
                            List<Sample> samples = WaterQualityCommands.makeReportList(json, location);

                            WaterQualityReport report = new WaterQualityReport(new com.vuw.project1.riverwatch.bluetooth.Location(location.getLatitude(), location.getLongitude()),GregorianCalendar.getInstance().getTime(), samples, false);

                            Database database = new Database(this);

                            // Connected
                            if (NetworkChecker.checkNetworkConnected(mainContext)){

                                double count = 0;

                                long id = database.saveWaterReport("Water Report", location.getLatitude(), location.getLongitude(), GregorianCalendar.getInstance().getTime().toString(), 1);
                                for (Sample sample : samples) {
                                    database.saveWaterReportSample(id, sample.getTemperature(), sample.getPh(), sample.getConductivity(), sample.getTurbidity(), count);
                                    count+=(sample.getTsl()/60);
                                }

                                Toast.makeText(mainContext, "   Sending.....   ", Toast.LENGTH_SHORT).show();
                                app.getInstance().getServiceBroker().sendReport(report);
                            }

                            // Not Connected
                            else{
                                double count = 0;
                                long id = database.saveWaterReport("Water Report", location.getLatitude(), location.getLongitude(), GregorianCalendar.getInstance().getTime().toString(), 0);
                                for (Sample sample : samples) {
                                    database.saveWaterReportSample(id, sample.getTemperature(), sample.getPh(), sample.getConductivity(), sample.getTurbidity(), count);
                                    count+=(sample.getTsl()/60);
                                }

                                Toast.makeText(mainContext, " Data saved to Database ", Toast.LENGTH_SHORT).show();
                            }


                            break;
                        default:
                            break;

                    }
                } catch (JSONException exception) {
                    //Log.e(TAG, exception.toString());
                    System.out.println("The Water Quality device is returning poorly formatted data.");
                }

            }
            allData = "";

        }

        else if (allData.contains("Sready")){

            // TODO send the data that's in the numerical box

        }
    }

    public Location getLocation (){
        return mLastLocation;
    }

}