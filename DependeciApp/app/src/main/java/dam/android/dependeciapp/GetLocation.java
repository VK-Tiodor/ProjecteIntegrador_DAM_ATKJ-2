package dam.android.dependeciapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class GetLocation extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_MAPS = 1;
    private static String[] PERMISSIONS_MAPS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toast.makeText(this, R.string.maps_this_may_take_a_few_seconds, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("MissingPermission")
    private void setMarkerOnMyLocation(){
        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mylocatioListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mylocatioListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, PERMISSIONS_MAPS, REQUEST_MAPS);

        } else {

            setMarkerOnMyLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_MAPS){

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                setMarkerOnMyLocation();
            } else {
                Toast.makeText(this, R.string.maps_right_required, LENGTH_LONG).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private class MyLocationListener implements LocationListener {

        private static final float ZOOM_CITY_LEVEL = 10;
        private static final float ZOOM_STREET_LEVEL = 15;
        private static final float ZOOM_BUILDING_LEVEL = 20;

        private Location myLocation;

        @Override
        public void onLocationChanged(Location loc) {
            if(myLocation == null || myLocation.distanceTo(loc) >= 100){
                myLocation = loc;
                LatLng myLocLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                if (myLocLatLng.latitude != 0.0 && myLocLatLng.longitude != 0.0) {
                    try {
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        List<Address> list = geocoder.getFromLocation(myLocLatLng.latitude, myLocLatLng.longitude, 1);
                        if (!list.isEmpty()) {
                            Address address = list.get(0);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocLatLng, ZOOM_STREET_LEVEL));
                            mMap.addMarker(new MarkerOptions().position(myLocLatLng).title(getString(R.string.maps_marker_you_are_here)).snippet(address.getAddressLine(0))).showInfoWindow();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), R.string.gps_disabled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), R.string.gps_enabled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch(status){
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(getApplicationContext(), R.string.maps_provider_out_of_service, Toast.LENGTH_LONG).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(getApplicationContext(), R.string.maps_provider_temporarily_unavailable, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
