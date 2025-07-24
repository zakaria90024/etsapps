package com.sasoftbd.ets.activites.attendance;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasoftbd.ets.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class TrackingGoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback, TrackingView, AHInfoView {

    FloatingActionButton floatingActionButton;
    GoogleMap gMap;
    EditText searchtv;
    private final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    Button date_selection;
    private Calendar calendar;
    // Store marker data
    private HashMap<Marker, String> markerInfoMap = new HashMap<>();

    String[] locationNames, addresses, endTime;
    List<LatLng> routePoints = new ArrayList<>();

    TrackingPresenter trackingPresenter;
    AHInfomPresenter ahInfomPresenter;
    String selectedDate;
    Spinner spinner_mpo_list;
    SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_tracking_google_map);
        spinner_mpo_list = findViewById(R.id.spinner_mpo_list);


        date_selection = findViewById(R.id.date_selection);
        calendar = Calendar.getInstance();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            // Delay the map initialization to avoid white screen issues
            mapFragment.getView().post(() -> mapFragment.getMapAsync(this));
        }

        trackingPresenter = new TrackingPresenter(this);
        ahInfomPresenter = new AHInfomPresenter(this);

        //trackingPresenter.getNoticeList("2025-02-13", "M-12382");
        ahInfomPresenter.getAhINfo(new AuthPrefsDataClass(this).getUserId(), "");

        searchtv = findViewById(R.id.searchTv);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.INVISIBLE);

        floatingActionButton.setOnClickListener(view -> getCurrentLocation());

        searchtv.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                String location = searchtv.getText().toString().trim();
                if (!location.isEmpty()) {
                    searchLocation(location);
                }
            }
            return false;
        });

        if (checkPermissions()) {
            //initMap();
        }


        //openDatePickerDialog();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(new Date());

        date_selection.setText(formattedDate);
        date_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog();
            }
        });


        // Create a Handler attached to the main Looper
        Handler handler = new Handler(Looper.getMainLooper());
        // Delay execution by 1 minute (60,000 milliseconds)
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                //onStop();
//                Toast.makeText(TrackingGoogleMapActivity.this, "Called5", Toast.LENGTH_SHORT).show();
//
//                  moveTaskToBack(true);
//                onRestart();
//                //finish();


//                moveTaskToBack(true); // Minimize the app
//
//                // Schedule app relaunch after 10 seconds
//                Intent intent = new Intent(TrackingGoogleMapActivity.this, TrackingGoogleMapActivity.class);
//                PendingIntent pendingIntent = PendingIntent.getActivity(TrackingGoogleMapActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//
//                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
                //initMap();

//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME); // CATEGORY_HOME is used for home screen
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Open the home screen in a new task
//                startActivity(intent);


//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.map, new SupportMapFragment());
//                transaction.commitNow();
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

                //mapFragment.getView().post(() -> mapFragment.getMapAsync(TrackingGoogleMapActivity.this));
                //date_selection.performClick();

                //tab_CL.performClick();

                spinner_mpo_list.performClick();

            }
        }, 2000);

    }





    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }
    }


    private void initMap() {

        if (mapFragment != null) {
            //mapFragment.getMapAsync(this);
            mapFragment.getView().post(() -> mapFragment.getMapAsync(this));
        }
    }


    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(location, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                gMap.addMarker(new MarkerOptions().position(latLng).title(location));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Location currentLocation = task.getResult();
                LatLng userLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                gMap.addMarker(new MarkerOptions().position(userLatLng).title("Your Location"));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        if (checkPermissions()) {
            getCurrentLocation();
        }

        //drawRoute();
        setMarkerClickListener();
    }


    private void drawRoute() {


        for (int i = 0; i < routePoints.size(); i++) {
            String markerInfo = addresses[i];
            BitmapDescriptor icon = createNumberedMarker(i + 1);

            Marker marker = gMap.addMarker(new MarkerOptions()
                    .position(routePoints.get(i))
                    .title(locationNames[i] + "(" + endTime[i] + ")")
                    .snippet(markerInfo)
                    .icon(icon));

            markerInfoMap.put(marker, markerInfo);
        }

        // Draw polyline
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(routePoints)
                .width(10)
                .color(Color.BLUE)
                .geodesic(true);
        gMap.addPolyline(polylineOptions);

        // Adjust camera to fit the full route
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(routePoints.get(0), 12));
    }

    private BitmapDescriptor createNumberedMarker(int number) {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawCircle(50, 50, 45, paint);

        paint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(number), 50, 65, paint);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void setMarkerClickListener() {
        gMap.setOnMarkerClickListener(marker -> {
            if (markerInfoMap.containsKey(marker)) {
                marker.showInfoWindow(); // Show text when clicking marker
            }
            return true; // Prevent default behavior (moves the camera)
        });
    }



    @Override
    public void onTrackingList(List<ResponseStatusModel> trackingListModel) {


        if (trackingListModel == null || trackingListModel == null) {
            Log.e("onTrackingList", "Received null trackingListModel or result!");
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            return;
        }

        List<ResponseStatusModel> result = trackingListModel;
        int size = result.size();

        if (size == 0) {
            Log.e("onTrackingList", "Empty result list! Nothing to process.");
            return;
        }

        // Initialize arrays with correct size
        locationNames = new String[size];
        addresses = new String[size];
        endTime = new String[size];

        // Ensure routePoints is initialized
        routePoints = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ResponseStatusModel data = result.get(i);

            try {

                double lat = Double.parseDouble(data.getStrLATITUDE());
                double lng = Double.parseDouble(data.getStrLONGITUDE());
                routePoints.add(new LatLng(lat, lng));

                locationNames[i] = data.getStrUSERNAME() != null ? data.getStrUSERNAME() : "Unknown";
                addresses[i] = data.getStrADDRESS() != null ? data.getStrADDRESS() : "No Address";
                endTime[i] = data.getStrENDTIME() != null ? data.getStrENDTIME() : "N/A";

                Log.d("onTrackingList", "Added: " + locationNames[i] + " (" + lat + ", " + lng + ")");
            } catch (NumberFormatException e) {
                Log.e("onTrackingList", "Invalid coordinates: " + data.getStrLATITUDE() + ", " + data.getStrLONGITUDE());
            }
        }

        //Toast.makeText(this, "--" + size, Toast.LENGTH_SHORT).show();
        drawRoute();


    }


    @Override
    public void onErrorTourType(String error) {

    }

    @Override
    public void onAHinfoStatus(List<AHinfoModel> allAHinfoList) {

        // Extract employee names from the list
        List<String> employeeNames = new ArrayList<>();
        for (AHinfoModel model : allAHinfoList) {
            employeeNames.add(model.getStrParticulars());
        }

        // Set up Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, employeeNames);
        spinner_mpo_list.setAdapter(adapter);

        // Handle Spinner Selection
        spinner_mpo_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Get selected EmployeeName
                String selectedEmployee = employeeNames.get(position);
                // Find the corresponding CardNo
                String selectedCardNo = allAHinfoList.get(position).getStrCardNO();

                // Show CardNo in Toast (or use as needed)
                trackingPresenter.getNoticeList(date_selection.getText().toString(), selectedCardNo);
                //Toast.makeText(getApplicationContext(), "CardNo: " + selectedCardNo + " date " + date_selection.getText().toString(), Toast.LENGTH_SHORT).show();

                Log.d("fromtracking", "CardNo: " + selectedCardNo + " date " + date_selection.getText().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


    }

    @Override
    public void onErrorAH(String error) {

    }


    private void openDatePickerDialog() {
        // Get the current date
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(TrackingGoogleMapActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Set the selected date in Calendar
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    // Format the date in "dd-MM-yyyy"
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    selectedDate = dateFormat.format(calendar.getTime());
                    Toast.makeText(this, "" + selectedDate, Toast.LENGTH_SHORT).show();
                    date_selection.setText(selectedDate);

                }, year, month, day);

        Calendar currentDate = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMaxDate(currentDate.getTimeInMillis());
        datePickerDialog.show();

    }


}