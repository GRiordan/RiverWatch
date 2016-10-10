package com.vuw.project1.riverwatch.colour_algorithm;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vuw.project1.riverwatch.R;

/**
 * Created by George on 17/09/2016.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private TextView text;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LatLng loc;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private GoogleMap map;

    public MapFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MapFragment newInstance(int sectionNumber) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nitrate_map, container, false);

        //TextView text = (TextView) rootView.findViewById(R.id.section_label);
        //String s = getArguments().getString("info_string");
        //text.setText(s);
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
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            loc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
        else{
            loc = new LatLng(-41.5, 174);
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 3.0f));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14), 5000, null);
        map.addMarker(new MarkerOptions()
                .position(loc)
                .title("Nitrate test taken"));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){

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

    public double getLat(){
        return mLastLocation.getLatitude();
    }

    public double getLng(){
        return mLastLocation.getLongitude();
    }

}
