package reen.com.mapapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                getLocationPermission();
                fetchGPS();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng currentlocation=new LatLng(latitude,longitude);


        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.common_google_signin_btn_icon_dark))

                .position(currentlocation).title("i am here"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocation,15f
        ));




          //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
          // Add a marker in Sydney and move the camera
          //  LatLng sydney = new LatLng(-34, 151);
          // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
          //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    //bit.ly/2WIKPgz
    double latitude = 0.0;
    double longitude = 0.0;

    private FusedLocationProviderClient fusedLocationClient;
    //        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    public void fetchGPS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            double lat = location.getLatitude();
                            double lon = location.getLongitude();
                              //txtLati.setText(lat + "");
                             //txtLongi.setText(lon + "");

                            latitude = lat;
                            longitude = lon;

                            if (mMap!=null)
                            {
                                LatLng currentlocation=new LatLng(latitude,longitude);

                                mMap.addMarker(new MarkerOptions().position(currentlocation).title("i am here"));

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocation,15f
                                ));


                            }

                        }
                    }
                });
    }


    static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2000;
    boolean mLocationPermissionGranted = false;

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    //getDeviceLocation();
                    fetchGPS();
                }
            }
        }
        //  updateLocationUI();
    }


}
