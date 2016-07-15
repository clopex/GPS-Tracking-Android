package tiimiss.globalgps.ba.tiimiss.Fragments;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tiimiss.globalgps.ba.tiimiss.R;
import tiimiss.globalgps.ba.tiimiss.Services.GeofenceTransitionsIntentService;
import tiimiss.globalgps.ba.tiimiss.Utillities.GlobalVariables;

/**
 * Created by adismulabdic on 7/4/16.
 */
public class TaskMapFragment extends Fragment implements OnMapReadyCallback, DirectionCallback, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    private String serverKey = "AIzaSyCwQpnuBimy-4GQP1klnuR2Bb-PPpjKBjM";
    private LatLng mCamera, mOrigin, mDesitnation, mLocation;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
    private static final String TAG = TaskMapFragment.class.getSimpleName();
    private final int REQ_PERMISSION = 999;

    public TaskMapFragment () {}

    public static TaskMapFragment newInstance() {
        TaskMapFragment taskMapFragment = new TaskMapFragment();
        return taskMapFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_map_fragment, container, false);
        setRetainInstance(true);

        getCoordinates();
        createGoogleApi();

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        createGoogleDirection();

        return view;
    }

    private boolean checkPermission(){
        Log.d(TAG, "checkPermission()");

        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void askPermission(){
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQ_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //
                } else {
                    permissionsDenied();
                }
                break;
            }
        }
    }

    private void permissionsDenied() {
        Log.v(TAG, "permissionsDenied()");
        // TODO close app and warn user
    }

    private void createGoogleDirection() {
        GoogleDirection.withServerKey(serverKey)
                .from(mOrigin)
                .to(mDesitnation)
                .transitMode(TransportMode.DRIVING)
                .execute(this);
    }

    private void createGoogleApi() {
        Log.d(TAG, "createGoolgeAPI()");
        if (mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void getCoordinates() {
        double latStart = Double.parseDouble(((GlobalVariables)getActivity().getApplication()).getLatitudeStartLocation());
        double lngStart = Double.parseDouble(((GlobalVariables)getActivity().getApplication()).getLongitudeStartLocation());
        double latEnd = Double.parseDouble(((GlobalVariables)getActivity().getApplication()).getLatitudeEndLocation());
        double lngEnd = Double.parseDouble(((GlobalVariables)getActivity().getApplication()).getLongitudeEndLocation());
        mCamera = new LatLng(latStart, lngStart);
        mOrigin = new LatLng(latStart, lngStart);
        mDesitnation = new LatLng(latEnd, lngEnd);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            mMap.addMarker(new MarkerOptions().position(mOrigin).title("StartLocation").icon(BitmapDescriptorFactory.fromResource(R.drawable.startmarker)));
            mMap.addMarker(new MarkerOptions().position(mDesitnation).title("EndLocation").icon(BitmapDescriptorFactory.fromResource(R.drawable.finishmarker)));
            ArrayList<LatLng> directionPath = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            mMap.addPolyline(DirectionConverter.createPolyline(getContext(), directionPath, 3, Color.RED));
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Toast.makeText(getContext(), "No route", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 9));
    }

    public static Intent makeNotificationIntent(Context context, String msg){
        Intent intent = new Intent(context, TaskMapFragment.class);
        intent.putExtra(NOTIFICATION_MSG, msg);
        return intent;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCamera, 9));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE
                }, 10);
                return;
            }
        } else {
            requestLocation();
        }
    }

    public void requestLocation(){
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

}
