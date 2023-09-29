package com.example.ghelmory.gameInstanceLocation;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ghelmory.R;
import com.example.ghelmory.databinding.FragmentMapBinding;
import com.example.ghelmory.utilities.SharedDataAcrossActivitiesManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.List;

public class LocationFragment extends Fragment  implements OnMapReadyCallback{
    private FragmentMapBinding binding;

    private static final String TAG = "LOCATION_FRAGMENT";
    private static final int DEFAULT_ZOOM = 15;

    private GoogleMap mMap = null;

    private PlacesClient mPlacesClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Location mLastKnownLocation = null;
    private Double selectedLatitude = null;
    private Double selectedLongitude = null;
    private String selectedAddress = null;

    @SuppressLint("MissingPermission")
    private final ActivityResultLauncher<String> requestLocationPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            mMap.setMyLocationEnabled(true);
            pickCurrentPlace();
        } else {
            Toast.makeText(requireContext(), "Location Permission Required", Toast.LENGTH_SHORT).show();
        }
    });

    public static LocationFragment newInstance() {
        return new LocationFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        binding.doneLl.setVisibility(View.GONE);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if(mapFragment == null){
            Log.d(TAG, "onCreateView: mapFragment is null");
            return binding.getRoot();
        }
        mapFragment.getMapAsync(this);

        Places.initialize(requireContext(), getString(R.string.google_maps_key));

        mPlacesClient = Places.createClient(requireContext());
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        autoCompleteSetup();
        toolbarSetup();
        return binding.getRoot();
    }

    private void toolbarSetup() {
        binding.toolbarBackButton.setOnClickListener(v -> requireActivity().onBackPressed());
        binding.currentLocationButton.setOnClickListener(v -> {
            if (isGpsEnabled()) {
                requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                pickCurrentPlace();
            } else {
                Toast.makeText(requireContext(), "GPS is not enabled", Toast.LENGTH_SHORT).show();
            }
        });
        binding.doneButton.setOnClickListener(v -> {
            if (selectedLatitude != null && selectedLongitude != null && selectedAddress != null) {
                SharedDataAcrossActivitiesManager.getInstance().setSharedData(selectedAddress);
                requireActivity().onBackPressed();
            } else {
                Toast.makeText(requireContext(), "Please select a location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void autoCompleteSetup() {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autoCompleteFragment);
        if(autocompleteFragment == null){
            Log.d(TAG, "autoCompleteSetup: autocompleteFragment is null");
            return;
        }

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onError(@NonNull Status status) {
                Log.d(TAG, "onError: status"+status);
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                String id = place.getId();
                String title = place.getName();
                LatLng latLng = place.getLatLng();
                if(latLng == null){
                    Log.d(TAG, "onPlaceSelected: latLng is null");
                    return;
                }
                selectedLatitude = latLng.latitude;
                selectedLongitude = latLng.longitude;
                selectedAddress = place.getAddress();

                Log.d(TAG, "onPlaceSelected: ID" + id);
                Log.d(TAG, "onPlaceSelected: Title" + title);
                Log.d(TAG, "onPlaceSelected: Latitude" + selectedLatitude);
                Log.d(TAG, "onPlaceSelected: Longitude" + selectedLongitude);
                Log.d(TAG, "onPlaceSelected: Address" + selectedAddress);

                addMarker(latLng, title, selectedAddress);

            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: ");

        mMap = googleMap;

        requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        mMap.setOnMapClickListener(latLng -> {
            Log.d(TAG, "onMapReady: Latitude" + latLng.latitude);
            Log.d(TAG, "onMapReady: Longitude" + latLng.longitude);

            selectedLatitude = latLng.latitude;
            selectedLongitude = latLng.longitude;

            addressFromLatLng(latLng);
        });
    }



    private void pickCurrentPlace(){
        Log.d(TAG, "pickCurrentPlace: ");
        if (mMap == null) {
            return;
        }
        detectAndShowLocation();
    }

    private void detectAndShowLocation(){
        try{
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnSuccessListener(requireActivity(), location -> {
                if (location == null) {
                    Log.d(TAG, "detectAndShowLocation: onSuccess : location is null");
                    return;
                }
                mLastKnownLocation = location;

                selectedLatitude = location.getLatitude();
                selectedLongitude = location.getLongitude();

                Log.d(TAG, "detectAndShowLocation: Latitude" + selectedLatitude);
                Log.d(TAG, "detectAndShowLocation: Longitude" + selectedLongitude);

                LatLng latLng = new LatLng(selectedLatitude, selectedLongitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));
                addressFromLatLng(latLng);
            })

                    .addOnFailureListener(e -> Log.d(TAG, "detectAndShowLocation: " + e));

        }catch (SecurityException e) {
            Log.d(TAG, "detectAndShowLocation: " + e);
        }
    }
    private boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);

        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try{
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        }catch (Exception e){
            Log.d(TAG, "isGpsEnabled: " + e);
        }
        try{
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            Log.d(TAG, "isGpsEnabled: " + e);
        }

        return gpsEnabled || networkEnabled;
    }
    private void addMarker(LatLng latLng, String title, String address) {
        Log.d(TAG, "addMarker: latitude" + latLng.latitude);
        Log.d(TAG, "addMarker: longitude" + latLng.longitude);
        Log.d(TAG, "addMarker: title" + title);
        Log.d(TAG, "addMarker: address" + address);

        mMap.clear();

        try{
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(title);
            markerOptions.snippet(address);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));

            binding.doneLl.setVisibility(View.VISIBLE);
            binding.selectedPlace.setText(address);
        }catch (Exception e){
            Log.d(TAG, "addMarker: " + e);
        }

    }
    private void addressFromLatLng(LatLng latLng) {
        Log.d(TAG, "addressFromLatLng: ");

        Geocoder geocoder = new Geocoder(requireContext());
        try{
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            Address address = addressList.get(0);

            String addressLine = address.getAddressLine(0);
            String locality = address.getLocality();


            selectedAddress = addressLine;
            addMarker(latLng, locality, addressLine);

        }catch (Exception e){
            Log.d(TAG, "addressFromLatLng: " + e);
        }
    }



}


