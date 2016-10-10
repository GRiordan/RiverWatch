package com.vuw.project1.riverwatch.bluetooth;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;



import com.vuw.project1.riverwatch.R;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class MainCalibrateActivity extends BlunoLibrary {

    private Button buttonScan;
	private Button buttonUpdate;
	private Button buttonDebug;
    private Button buttonReset;
    private Button buttonCalibrate;

    private TextView sampleValue;
    private TextView debugStatus;

    private ProgressBar progressBar;

    private TextView serialReceivedText;

    private String interval = "300";
    private String allData = "";


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calibrate);

		progressBar = (ProgressBar)findViewById(R.id.status_bar);
        progressBar.setVisibility(View.GONE);
        // don't show the keyboard instantly
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		onCreateProcess();														//onCreate Process by BlunoLibrary

        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        buttonScan = (Button) findViewById(R.id.buttonScan);                    //initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
            }
        });

		sampleValue = (TextView) findViewById(R.id.sample_value) ;
        sampleValue.setEnabled(false);
        sampleValue.setSelected(false);

        debugStatus = (TextView) findViewById(R.id.debugStatus);
        debugStatus.setText("status: disabled");

		buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonUpdate.setEnabled(false);
		buttonUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(sampleValue.getInputType() == TYPE_CLASS_NUMBER) {

					updateDevice();

				}

			}
		});

		buttonDebug = (Button) findViewById(R.id.buttonDebug);
        buttonDebug.setEnabled(false);
		buttonDebug.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

                serialReceivedText.setText("");
				serialSend("Debug");


				// TODO WAIT FOR RESPONSE

			}
		});

        buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setEnabled(false);
        buttonReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                buttonReset.setEnabled(true);
                buttonUpdate.setEnabled(true);
                buttonDebug.setEnabled(true);
                sampleValue.setEnabled(true);
                buttonCalibrate.setEnabled(false);
                serialReceivedText.setText("");
                serialSend("exit");

            }
        });

        buttonCalibrate = (Button) findViewById(R.id.buttonCalibrate);
        buttonCalibrate.setEnabled(false);
        buttonCalibrate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                serialReceivedText.setText("");
                serialSend("calibrate");

            }
        });

        serialReceivedText=(TextView) findViewById(R.id.receivedText);	//initial the EditText of the received data

    }

	private void updateDevice() {

		serialSend("Sint");

        if(sampleValue.getInputType() == TYPE_CLASS_NUMBER && !interval.isEmpty()) {
            interval = sampleValue.getText().toString();
        }

		//findViewById(R.id.status_bar).setVisibility(View.VISIBLE);

		//TODO WAIT FOR RESPONSE
        progressBar.setVisibility(View.VISIBLE);


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
            buttonReset.setEnabled(true);
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

        serialReceivedText.append(data);	//append the text into the EditText

        allData += data;

        // responses are "?CAL,2" or "?CAL,1" or "?CAL,0"


        if (allData.contains("CAL,")){

            // TODO send the data that's in the numerical box

			System.out.println("CALIBRATIONS!!!!");

        }


        else if(data.contains("Debug_off")){

            buttonDebug.setEnabled(true);
            debugStatus.setText("status: disabled");
            allData = "";
        }

        else if (allData.contains("Debug_on")) {

            buttonDebug.setEnabled(false);
            debugStatus.setText("status: enabled");


            if (allData.contains("EC_mode")) {

                //if (allData.contains("*SL")) {

                //    serialSend("wake");
                    //allData = "";
                //    serialReceivedText.setText("");
                //}

                buttonCalibrate.setEnabled(true);

                //serialSend("blank");
                // TODO make a timer for 3 seconds
                //serialSend("Cal,?");

            } else if (allData.contains("pH_mode")) {

                // S seven T ten F four


                //serialSend("S");

                allData = "";

            }
        }

		// TODO the Bluetooth device has replied with a response to update the data for intervals
		else if(allData.contains("Sready")){


                serialSend(interval+"\n");
                allData = "";




		}
        else if(allData.contains("Sdone")){

            progressBar.setVisibility(View.GONE);

            buttonUpdate.setText("DEVICE UPDATED");
            buttonUpdate.setEnabled(false);
            sampleValue.setEnabled(false);

            allData = "";

        }



		else{
            //serialSend("exit");
			//System.out.println("OTHER:" + allData);

            allData = "";
        }
    }

}