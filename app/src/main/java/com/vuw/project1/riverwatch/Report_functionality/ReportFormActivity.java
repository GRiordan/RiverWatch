package com.vuw.project1.riverwatch.Report_functionality;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Incident_Report;
import com.vuw.project1.riverwatch.objects.Incident_Report;
import com.vuw.project1.riverwatch.service.App;
import com.vuw.project1.riverwatch.ui.MainActivity;

import java.util.Date;

public class ReportFormActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private EditText description;
    private EditText extraDetails;
    private ImageView image;
    private Button submitButton;
    private String imagePath;
    private BasicLoc location;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap map;

    //public static final String IMAGE_PATH = "extra photo path";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form);
        description = (EditText) findViewById(R.id.submission_details_description);
        extraDetails = (EditText) findViewById(R.id.submission_details_extra_details);
        location = getLocation();
        setupMap();
        buildGoogleApiClient();

        //setup the image
        if ((imagePath = getImagePath()) != null && !imagePath.equals("")) {
            final ImageView imageView = (ImageView) findViewById(R.id.submission_details_photo);
            float imgWeight = ((LinearLayout.LayoutParams) (imageView.getLayoutParams())).weight;
            ScreenDimensions dimensioner = new ScreenDimensions(getBaseContext());
            int imgHeight = (int) (dimensioner.getScreenHeight() * imgWeight);
            imageView.setImageBitmap(BitMapDisplay.decodeSampledBitmapFromPath(getImagePath(), imgHeight));
        } else {
            findViewById(R.id.submission_details_photo).setVisibility(View.GONE);
        }

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                submit();
            }
        });

    }

    public void submit(){

        //Save to history
        String date = new Date(System.currentTimeMillis()).toString();
        String descriptionText = description.getText().toString();
        String extraDetailsText = extraDetails.getText().toString();
        String imagePath = getImagePath();
        BasicLoc loc = getLocation();
        Database database = new Database(this);
        database.saveIncidentReport(descriptionText,"placeHolder location string",location.latitude,location.longitude,date,extraDetailsText,imagePath);
        //TODO attemmpt to submit to website
        final NetworkInfo network = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if(network != null && network.isConnected()){
            App.getServiceBroker().sendReport(new IncidentReport());
        }
        //Finish up activity
        Toast.makeText(getBaseContext(),"thank you for your submission",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ReportFormActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    //------------------------------------------Google API methods for location services----------------------------------------------
    //------------------------------------------Google API methods for location services----------------------------------------------
    //------------------------------------------Google API methods for location services----------------------------------------------

    public void setupMap(){
        MapFragment mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_fragment, mMapFragment);
        fragmentTransaction.commit();

        mMapFragment.getMapAsync(this);
    }


    public void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try{
            android.location.Location lastKnown = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }catch(SecurityException e){
            Toast.makeText(this, "Location services not enabled", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection suspended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection failed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        // enables the button to go to their location
        try{
            map.setMyLocationEnabled(true);
        }catch(SecurityException e){
            Toast.makeText(this, "Location services not enabled", Toast.LENGTH_SHORT).show();
        }
        // set default location and zoom to nz
        LatLng nz = new LatLng(-41.5, 174);

        LatLng imageLocation = new LatLng(location.getLatitude(), location.getLongitude());
        //Toast.makeText(this, imageLocation.toString(), Toast.LENGTH_SHORT).show();
        // animate map camera to where photo taken
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(imageLocation, 3.0f));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(imageLocation, 14), 5000, null);
        map.addMarker(new MarkerOptions()
                .position(imageLocation)
                .title("Photo taken"));
    }
    //--------------------------------End of Google API services methods--------------------------------
    //--------------------------------End of Google API services methods--------------------------------
    //--------------------------------End of Google API services methods--------------------------------


    public String getImagePath(){
        return getIntent().getExtras().getString("IMAGE_PATH");
    }

    public BasicLoc getLocation(){
        double latitude = Double.valueOf(getIntent().getExtras().getString("LATITUDE"));
        double longitude = Double.valueOf(getIntent().getExtras().getString("LONGITUDE"));
        return new BasicLoc(latitude,longitude);
    }
}
