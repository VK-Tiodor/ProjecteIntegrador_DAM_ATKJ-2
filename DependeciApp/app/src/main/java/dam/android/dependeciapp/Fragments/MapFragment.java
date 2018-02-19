package dam.android.dependeciapp.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import dam.android.dependeciapp.R;

/**
 * Created by adria on 11/02/2018.
 */
@SuppressLint("ValidFragment")
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_MAPS = 1;
    private final String[] PERMISSIONS_MAPS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(isFocusOnMapFragment()) {
            Toast.makeText(getContext(), R.string.maps_this_may_take_a_few_seconds, Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        MyLocationListener myLocationListener = new MyLocationListener(lastKnownLocation);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_MAPS, REQUEST_MAPS);
        } else {
            enableMyLocation();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_MAPS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(getContext(), R.string.maps_right_required, Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean isFocusOnMapFragment(){
        FragmentActivity fa = getActivity();
        if(fa == null){
            return false;
        }
        Fragment f = fa.getSupportFragmentManager().getPrimaryNavigationFragment();
        if(f == null){
            return false;
        }
        return (f instanceof MapFragment);
    }

    private class MyLocationListener implements LocationListener {

        private static final float ZOOM_CITY_LEVEL = 10;
        private static final float ZOOM_STREET_LEVEL = 15;
        private static final float ZOOM_BUILDING_LEVEL = 20;

        private Location myLocation;
        private Marker myMarker;

        @SuppressLint("MissingPermission")
        public MyLocationListener(Location lastKnownlocation) {
            if (lastKnownlocation != null) {
                myLocation = lastKnownlocation;
                LatLng myLocationLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                setMarkerOnLatLng(myLocationLatLng);
            }
        }

        @Override
        public void onLocationChanged(Location loc) {
            if (myLocation == null || myLocation.distanceTo(loc) >= 1) {
                myLocation = loc;
                LatLng myLocationLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                setMarkerOnLatLng(myLocationLatLng);
            }
        }

        public void setMarkerOnLatLng(LatLng locationLatLng) {
            if (locationLatLng.latitude != 0.0 && locationLatLng.longitude != 0.0) {
                try {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> list = geocoder.getFromLocation(locationLatLng.latitude, locationLatLng.longitude, 1);
                    if (!list.isEmpty()) {
                        Address address = list.get(0);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, ZOOM_STREET_LEVEL));
                        if (myMarker != null) {
                            myMarker.remove();
                        }
                        myMarker = mMap.addMarker(new MarkerOptions().position(locationLatLng).title(getString(R.string.maps_marker_you_are_here)).snippet(address.getAddressLine(0)));
                        myMarker.showInfoWindow();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            if(isFocusOnMapFragment()) {
                Toast.makeText(getContext(), R.string.gps_disabled, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            if(isFocusOnMapFragment()) {
                Toast.makeText(getContext(), R.string.gps_enabled, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if(isFocusOnMapFragment()){
                switch (status) {
                    case LocationProvider.OUT_OF_SERVICE:
                        Toast.makeText(getContext(), R.string.maps_provider_out_of_service, Toast.LENGTH_LONG).show();
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Toast.makeText(getContext(), R.string.maps_provider_temporarily_unavailable, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    }
}
