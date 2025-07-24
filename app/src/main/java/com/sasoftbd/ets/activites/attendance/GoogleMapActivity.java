package com.sasoftbd.ets.activites.attendance;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasoftbd.ets.R;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {


    FloatingActionButton floatingActionButton;
    GoogleMap gMap;
    TextView showaddress;
    EditText searchtv;
    private final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    //    InterstitialAd interstitialAd = null;
    private String type;

    Intent in = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);



        searchtv = findViewById(R.id.searchTv);
        showaddress = findViewById(R.id.show_address);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.INVISIBLE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestureLocationalAddress();
            }
        });


        searchtv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66 || keyCode == 62) {
//                    searchtv.setFocusable(false);
                    String location = searchtv.getText().toString().trim();
                    List<Address> addressList = null;
                    if (location != null || !location.equals("")) {
                        Geocoder geocoder = new Geocoder(GoogleMapActivity.this);
                        try {
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (addressList != null) {
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            gMap.addMarker(new MarkerOptions().position(latLng).title(location));
                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        }
                    }

                }
                return false;
            }
        });

        mappermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private boolean mappermission() {
        boolean result = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0);
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }

    private void getCurrentLocation() {


        double lat = Double.parseDouble(in.getStringExtra("lat"));
        double lang = Double.parseDouble(in.getStringExtra("long"));


        //LatLng bd = new LatLng(23.733330, 90.417458);
        LatLng bd = new LatLng(lat, lang);

        gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        gMap.addMarker(new MarkerOptions().position(bd).title("Dhaka").snippet("Hear you go"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(bd));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bd, 15));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(false);

    }

    private void gestureLocationalAddress() {
        mappermission();
        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(this);
        final Task location;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0);
                return;
            } else {
                location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Location curentlocation1 = (Location) location.getResult();
                        Double let = null;
                        Double lon = null;
                        if (curentlocation1 == null) {
                            let =   Double.parseDouble(getIntent().getStringExtra("lat"));
                            lon = Double.parseDouble(getIntent().getStringExtra("long"));
                        } else {
                            let =   Double.parseDouble(getIntent().getStringExtra("lat"));
                            lon = Double.parseDouble(getIntent().getStringExtra("long"));
                        }

                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(let, lon), 15));
                        gMap.addMarker(new MarkerOptions().position(new LatLng(let, lon)));
                        gMap.getUiSettings().setMyLocationButtonEnabled(false);
                        if (ActivityCompat.checkSelfPermission(GoogleMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GoogleMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        gMap.setMyLocationEnabled(true);
                        showaddress.setVisibility(View.VISIBLE);


                    }
                });
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        if (mappermission()) {
            gestureLocationalAddress();

        } else {
            getCurrentLocation();

        }
        getIconLocation(gMap);
    }

    private String getAddress(double lat, double lon) {

        String address = "";
        List<Address> addresses;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return address;
    }

    private void getIconLocation(final GoogleMap googleMap) {
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng iconLocation = googleMap.getCameraPosition().target;
                showaddress.setVisibility(View.INVISIBLE);
                showaddress.setText(getAddress(iconLocation.latitude, iconLocation.longitude));

            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
