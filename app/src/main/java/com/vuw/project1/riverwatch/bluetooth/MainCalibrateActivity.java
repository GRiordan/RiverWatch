package com.vuw.project1.riverwatch.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.GregorianCalendar;
import java.util.List;

public class MainCalibrateActivity extends BlunoLibrary {

    private Button buttonScan;
	private Button buttonUpdate;
	private Button buttonDebug;

    private TextView sampleValue;

    private String allData = "";


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calibrate);

		findViewById(R.id.status_bar).setVisibility(View.GONE);



		onCreateProcess();														//onCreate Process by BlunoLibrary

        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
            }
        });

		sampleValue = (TextView) findViewById(R.id.sample_value) ;

		buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
		buttonUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!sampleValue.getText().equals("")) {
					updateDevice();
				}

			}
		});

		buttonDebug = (Button) findViewById(R.id.buttonDebug);
		buttonDebug.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				serialSend("Debug");


				// TODO WAIT FOR RESPONSE

			}
		});

    }

	private void updateDevice() {

		serialSend("Sint");

		findViewById(R.id.status_bar).setVisibility(View.VISIBLE);

		//TODO WAIT FOR RESPONSE

	}

	protected void onResume(){
		super.onResume();
		onResumeProcess();														//onResume Process by BlunoLibrary
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
    }

	protected void onStop() {
		super.onStop();
		onStopProcess();														//onStop Process by BlunoLibrary
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

        findViewById(R.id.status_bar).setVisibility(View.GONE);

        //serialReceivedText.append(data);	//append the text into the EditText

        allData += data;


        if (allData.contains("Cal,")){

            // TODO send the data that's in the numerical box

			System.out.println("CALIBRATIONS!!!!");

        }

		else if (allData.contains("EC_mode")){

			serialSend("Cal,?");

		}

		else{

			System.out.println("OTHER:" + allData);
		}
    }

}