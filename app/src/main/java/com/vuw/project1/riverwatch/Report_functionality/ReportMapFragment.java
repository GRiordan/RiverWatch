package com.vuw.project1.riverwatch.Report_functionality;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vuw.project1.riverwatch.R;

/**
 * Created by Alex on 10/10/2016.
 */
public class ReportMapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap map;
    private double longitude;
    private double latitude;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_map, container, false);


        SupportMapFragment mapFragment
                = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(rootView.getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        longitude = getArguments().getDouble("Lon");
        latitude = getArguments().getDouble("Lat");

        return rootView;
    }



    @Override
    public void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        LatLng loc = new LatLng(latitude, longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 3.0f));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14), 5000, null);
        map.addMarker(new MarkerOptions()
                .position(loc)
                .title("Nitrate test taken"));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){
        Toast.makeText(this.getContext(), "Connection failed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i){
        Toast.makeText(this.getContext(), "Connection suspended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
    }
}
