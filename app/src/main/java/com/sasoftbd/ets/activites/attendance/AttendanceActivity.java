package com.sasoftbd.ets.activites.attendance;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sasoftbd.ets.MainActivity;
import com.sasoftbd.ets.R;
import com.sasoftbd.ets.latlong.APIService;
import com.sasoftbd.ets.latlong.LocationService;
import com.sasoftbd.ets.model.AttendanceModel;
import com.sasoftbd.ets.model.CardDate;
import com.sasoftbd.ets.model.LoginModel;
import com.sasoftbd.ets.network.ApiClient;
import com.sasoftbd.ets.utils.AuthPrefsDataClass;
import com.sasoftbd.ets.utils.Distance;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//implements AttendanceView, CardNoView, LeaveListView
public class AttendanceActivity extends AppCompatActivity {


    private static final int FAST_UPDATE_INTERVAL = 100;
    private static final int DEFAULT_UPDATE_INTERVAL = 100;
    private static final int PERMISSION_FINE_LOCATION = 99;

    double dis;
    //LeaveListPresenter leaveListPresenter;
    String todayDateFormated, todayShowDate, todayDateTime;
    String todayDate;
    CardView inCardClicked, outCardClicked;
    Location currentLocation;
    TextView shift;
    Spinner spinner;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    static String imageString = "";
    int REQUEST_IMAGE_CAPTURE = 40;
    Button btnIN, btnOUT;
    static String inORout, strShiftspinner;
    static ImageView imageView;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat;
    String formattedDate, Address;
    final List<String> leaveList = new ArrayList<>();
    //AttendancePresenter attendancePresenter;
    //CardNoPresenter cardNoPresenter;
    String CardNO;
    //SqliteDbHelper dbHelper;
    List<LoginModel> status;
    List<AttendanceModel> attendanceModelsList = new ArrayList<>();
    TextView txt_Default, txt_Status, txt_TimeDate;
    TextView txt_DefaultOUT, txt_StatusOUT, txt_TimeDateOUT;
    TextView btnText_Status, btnText_StatusOUT;
    TextView date, time;
    ConstraintLayout constraintLayoutOut, constraintLayoudftdf7IN;
    ImageView imageView1, imageView2;
    private Uri photoURI;
    String selectedDate;
    String VersionCodeString;
    AuthPrefsDataClass authPrefsDataClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        authPrefsDataClass = new AuthPrefsDataClass(this);


        forceGpsON();//force on gps icon
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Start the foreground Location service ===================================================
        Intent serviceIntent = new Intent(this, LocationService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
        //end the foreground Location service=======================================================


        //dbHelper = new SqliteDbHelper(AttendanceActivity.this);
        imageView1 = findViewById(R.id.imageView22);
        imageView2 = findViewById(R.id.imageView23);



        //for get version ==========================================================================
        //For Android API 33+ (Android 13+):
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.PackageInfoFlags.of(0));
                VersionCodeString = String.valueOf(packageInfo.getLongVersionCode()); // For version code
                //VersionCodeString = packageInfo.versionName;        // For version name


                //Log.d("AppVersion", "Version Code: " + versionCode);
                //Log.d("AppVersion", "Version Name: " + versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            //For Android API < 33:
            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                VersionCodeString = String.valueOf(packageInfo.versionCode);    // Deprecated in API 28+

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        //end get version ==========================================================================



//        if (authPrefsDataClass.getRole().equals("AH") || authPrefsDataClass.getRole().equals("DH") || authPrefsDataClass.getRole().equals("ZH") || authPrefsDataClass.getRole().equals("Admin") || authPrefsDataClass.getRole().equals("TEAM")) {
//            imageView1.setVisibility(View.VISIBLE);
//            imageView2.setVisibility(View.VISIBLE);
//
//        } else {

            imageView1.setVisibility(View.GONE);
            imageView2.setVisibility(View.GONE);

//        }


        date = findViewById(R.id.textView46);
        time = findViewById(R.id.textView48);

        status = new ArrayList<>();
        //status = dbHelper.getloginfo();
        //status.get(0).setIntMpoType(0);
        //status.get(0).getIntMpoType();

        dateFormat = new SimpleDateFormat("ddMMMyyyy");
        formattedDate = dateFormat.format(calendar.getTime());
        todayDate = formattedDate;

        DateFormat df = new SimpleDateFormat("EEEE d MMM yyyy hh:mm a");
        todayDateTime = df.format(Calendar.getInstance().getTime());
        DateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dff1 = new SimpleDateFormat("dd-MM-yyyy");
        todayDateFormated = dff.format(Calendar.getInstance().getTime());
        todayShowDate = dff1.format(Calendar.getInstance().getTime());

        try {
            String[] t = todayDateTime.split(" ");
            date.setText(t[0] + "\n" + todayShowDate);
            time.setText("" + t[4] + " " + t[5]);
        } catch (Exception e) {
            Log.d("From Split", "From Split");
        }

        initt();
        btnRefressh();


        inCardClicked.setOnClickListener(view -> {

            Intent i = new Intent(AttendanceActivity.this, AttendanceDetailsActivity.class);
            i.putExtra("strATTEN_SHIFT", attendanceModelsList.get(0).getStrATTENSHIFT());
            i.putExtra("strATTEN_TIMEIN", attendanceModelsList.get(0).getStrATTENTIMEIN());
            i.putExtra("strUSER_NAME", attendanceModelsList.get(0).getStrUSERNAME());
            i.putExtra("strROLE", attendanceModelsList.get(0).getStrROLE());
            i.putExtra("strEMP_CARD_NO", attendanceModelsList.get(0).getStrEMPCARDNO());
            i.putExtra("strLATITUDE", attendanceModelsList.get(0).getStrLATITUDE());
            i.putExtra("strLONGITUDE", attendanceModelsList.get(0).getStrLONGITUDE());
            i.putExtra("strADDRESS", attendanceModelsList.get(0).getStrADDRESS());
            i.putExtra("strACTION", attendanceModelsList.get(0).getStrACTION());
            i.putExtra("strEMP_IMAGE", attendanceModelsList.get(0).getStrEMPIMAGE());
            i.putExtra("strATTEN_COMMENTS", attendanceModelsList.get(0).getStrATTENCOMMENTS());
            i.putExtra("strATTEN_STATUS", attendanceModelsList.get(0).getStrATTENSTATUS());
            i.putExtra("strATTEN_STATUS", attendanceModelsList.get(0).getStrATTENSTATUS());
            i.putExtra("strINSERT_DATE", attendanceModelsList.get(0).getstrINSERT_DATE());
            startActivity(i);

        });

        outCardClicked.setOnClickListener(view -> {
            Intent i = new Intent(AttendanceActivity.this, AttendanceDetailsActivity.class);
            i.putExtra("strATTEN_SHIFT", attendanceModelsList.get(1).getStrATTENSHIFT());
            i.putExtra("strATTEN_TIMEIN", attendanceModelsList.get(1).getStrATTENTIMEIN());
            i.putExtra("strUSER_NAME", attendanceModelsList.get(1).getStrUSERNAME());
            i.putExtra("strROLE", attendanceModelsList.get(1).getStrROLE());
            i.putExtra("strEMP_CARD_NO", attendanceModelsList.get(1).getStrEMPCARDNO());
            i.putExtra("strLATITUDE", attendanceModelsList.get(1).getStrLATITUDE());
            i.putExtra("strLONGITUDE", attendanceModelsList.get(1).getStrLONGITUDE());
            i.putExtra("strADDRESS", attendanceModelsList.get(1).getStrADDRESS());
            i.putExtra("strACTION", attendanceModelsList.get(1).getStrACTION());
            i.putExtra("strEMP_IMAGE", attendanceModelsList.get(1).getStrEMPIMAGE());
            i.putExtra("strATTEN_COMMENTS", attendanceModelsList.get(1).getStrATTENCOMMENTS());
            i.putExtra("strATTEN_STATUS", attendanceModelsList.get(1).getStrATTENSTATUS());
            i.putExtra("strINSERT_DATE", attendanceModelsList.get(1).getstrINSERT_DATE());
            startActivity(i);

        });


        shift.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        forceGpsON();

        btnOUT.setOnClickListener(view -> {
            inORout = "OUT";

            if (ActivityCompat.checkSelfPermission(AttendanceActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission();
            } else {
                getImageFromCameraOut();
            }
        });


//        if (new AuthPrefsDataClass(this).getRole().equals("Admin")) {
//            btnIN.setEnabled(false);
//            btnOUT.setEnabled(false);
//        } else {
//            btnIN.setEnabled(true);
//            btnOUT.setEnabled(true);
//        }


        btnIN.setOnClickListener(view -> {

            try {


                String currentDate;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                currentDate = sdf.format(new Date());


                // Proceed with API call
                APIService apiservice = ApiClient.getRetrofit().create(APIService.class);
                JsonObject requestData = new JsonObject();
                requestData.addProperty("strEMP_CARD_NO", new AuthPrefsDataClass(this).getCardNO());
                requestData.addProperty("strDate", currentDate);
                requestData.addProperty("strStatus", "Attendance");

                Log.d("hhhDelete", "" + requestData);
                Call<String> call = apiservice.getStatusLeaveAttendance(requestData);

                inORout = "IN";
                if (ActivityCompat.checkSelfPermission(AttendanceActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestCameraPermission();
                } else {
                    getImageFromCamera();
                }

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

//                        if (response.body().equals("Leave Exist")) {
//                            new AlertDialog.Builder(AttendanceActivity.this)
//                                    .setTitle("সতর্কতা")
//                                    .setMessage("আজকের তারিখে ছুটির আবেদন পাওয়া গেছে, আপনি কি ছুটি বাতিল করে উপস্থিতি দিতে চান?")
//                                    .setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            //Toast.makeText(AttendanceActivity.this, "Delete Leave and Add", Toast.LENGTH_SHORT).show();
//
//
//                                            String currentDate;
//                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//                                            currentDate = sdf.format(new Date());
//
//
//                                            // Proceed with API call
//                                            APIService apiservice = ApiClient.getRetrofit().create(APIService.class);
//                                            JsonObject requestData = new JsonObject();
//                                            requestData.addProperty("strEMP_CARD_NO", new AuthPrefsDataClass(AttendanceActivity.this).getCardNO());
//                                            requestData.addProperty("strDate", currentDate);
//                                            requestData.addProperty("strStatus", "LeaveDelete");
//
//                                            Log.d("hhhDelete", "" + requestData);
//                                            Call<String> call = apiservice.postForDeleteLeaveInsertAttendane(requestData);
//                                            call.enqueue(new Callback<String>() {
//                                                @Override
//                                                public void onResponse(Call<String> call, Response<String> response) {
//
//                                                    if (response.body().equals("1")) {
//                                                        Toast.makeText(AttendanceActivity.this, "Leave Deleted!", Toast.LENGTH_SHORT).show();
//                                                        inORout = "IN";
//                                                        if (ActivityCompat.checkSelfPermission(AttendanceActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                                                            requestCameraPermission();
//                                                        } else {
//                                                            getImageFromCamera();
//                                                        }
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<String> call, Throwable t) {
//
//                                                }
//                                            });
//
//                                        }
//                                    })
//                                    .setNegativeButton("না", null)
//                                    .show();
//
//                        } else {
                        inORout = "IN";
                        if (ActivityCompat.checkSelfPermission(AttendanceActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestCameraPermission();
                        } else {
                            getImageFromCamera();
                        }
                        // }


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            } catch (Exception e) {
                Log.d("toast", "from here");
            }


        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strShiftspinner = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void btnRefressh() {


        AuthPrefsDataClass s = new AuthPrefsDataClass(this);
        CardNO = s.getCardNO();
        TextView d = (TextView) findViewById(R.id.textView50);
        d.setText("Card No: " + CardNO);

        //for button decision
        CardDate cardDate = new CardDate();
        cardDate.setStrEMPCARDNO(CardNO);
        cardDate.setStrATTENDATEIN(todayDateFormated);
        Gson gson = new Gson();
        String json = gson.toJson(cardDate);
        JsonObject jsonObject = null;
        jsonObject = new JsonParser().parse(json).getAsJsonObject();

        Log.d("test", ""+jsonObject);


        APIService apiservice = ApiClient.getRetrofit().create(APIService.class);
        //APIService apiservice = retrofitSms.create(APIService.class);
        Call<List<AttendanceModel>> call = apiservice.getButtonStatus(jsonObject);
        call.enqueue(new Callback<List<AttendanceModel>>() {
            @Override
            public void onResponse(Call<List<AttendanceModel>> call, Response<List<AttendanceModel>> response) {

                try {

                    attendanceModelsList = response.body();
                    //kisu nai
                    if (attendanceModelsList.size() == 0 ) {

                        btnIN.setClickable(true);
                        btnIN.setBackgroundResource(R.color.colorPrimaryDark);
                        btnOUT.setClickable(false);
                        btnOUT.setBackgroundResource(R.color.gray_btn_bg_color);
                        dis = 0;
                        constraintLayoutOut.setVisibility(View.GONE);
                        constraintLayoudftdf7IN.setVisibility(View.GONE);

                    }

                    //in ase
                    if (attendanceModelsList.size() == 1 ) {

                        constraintLayoudftdf7IN.setVisibility(View.VISIBLE);
                        btnIN.setClickable(false);
                        btnIN.setBackgroundResource(R.color.gray_btn_bg_color);
                        btnOUT.setClickable(true);
                        btnOUT.setBackgroundResource(R.color.colorPrimaryDark);
                        constraintLayoutOut.setVisibility(View.GONE);


                    }

                    //in/out duta ase
                    if (attendanceModelsList.size() >= 2) {

                        dis = 0;
                        btnIN.setClickable(false);
                        btnIN.setBackgroundResource(R.color.gray_btn_bg_color);
                        btnOUT.setClickable(false);
                        btnOUT.setBackgroundResource(R.color.gray_btn_bg_color);
                        constraintLayoutOut.setVisibility(View.VISIBLE);
                        constraintLayoudftdf7IN.setVisibility(View.VISIBLE);
                        Toast.makeText(AttendanceActivity.this, "Today's Attendance Already Submitted", Toast.LENGTH_LONG).show();


                    }


                    txt_Default.setText(attendanceModelsList.get(0).getStrATTENSHIFT());
                    txt_TimeDate.setText(attendanceModelsList.get(0).getStrATTENTIMEIN()+" "+attendanceModelsList.get(0).getstrINSERT_DATE());
                    txt_Status.setText(attendanceModelsList.get(0).getStrATTENSTATUS());
                    btnText_Status.setText(attendanceModelsList.get(0).getStrACTION());


                    try {
                        txt_DefaultOUT.setText(attendanceModelsList.get(1).getStrATTENSHIFT());
                        txt_TimeDateOUT.setText(attendanceModelsList.get(0).getStrATTENTIMEIN()+" "+attendanceModelsList.get(1).getstrINSERT_DATE());
                        txt_StatusOUT.setText(attendanceModelsList.get(1).getStrATTENSTATUS());
                        btnText_StatusOUT.setText(attendanceModelsList.get(1).getStrACTION());

                    } catch (Exception e) {

                    }


                } catch (Exception e) {
                    Toast.makeText(AttendanceActivity.this, "Error From Attendance IN/OUT", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AttendanceModel>> call, Throwable t) {
                Toast.makeText(AttendanceActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                constraintLayoutOut.setVisibility(View.GONE);
                constraintLayoudftdf7IN.setVisibility(View.GONE);
            }
        });

    }


    public void statusRefresh() {


        try {

            String s[] = attendanceModelsList.get(0).getStrATTENTIMEIN().split(" ");
            txt_Default.setText(attendanceModelsList.get(0).getStrATTENSHIFT());
            txt_TimeDate.setText(s[4]);
            txt_Status.setText(attendanceModelsList.get(0).getStrATTENSTATUS());
            btnText_Status.setText(attendanceModelsList.get(0).getStrACTION());


            try {
                String ss[] = attendanceModelsList.get(1).getStrATTENTIMEIN().split(" ");

                txt_DefaultOUT.setText(attendanceModelsList.get(1).getStrATTENSHIFT());
                txt_TimeDateOUT.setText(ss[4]);
                txt_StatusOUT.setText(attendanceModelsList.get(1).getStrATTENSTATUS());
                btnText_StatusOUT.setText(attendanceModelsList.get(1).getStrACTION());

            } catch (Exception ignored) {

            }

        } catch (Exception ignored) {

        }


    }

    private void initt() {


        locationRequest = new LocationRequest();
        locationRequest.setInterval(100 * DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(100 * FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        btnIN = findViewById(R.id.btnIn);
        btnOUT = findViewById(R.id.btnOut);
        imageView = findViewById(R.id.imageViewSelect);
        shift = findViewById(R.id.txtShift);
        spinner = findViewById(R.id.spinner_id);
        //progressBar = findViewById(R.id.progressBar_lave);
        inCardClicked = findViewById(R.id.clickinCard);
        outCardClicked = findViewById(R.id.clickinCardOUT);
        constraintLayoutOut = findViewById(R.id.constraintLayoudfdsft7OUT);
        constraintLayoudftdf7IN = findViewById(R.id.constraintLayoudftdf7IN);

        txt_Default = findViewById(R.id.textView_ShiftName);
        txt_Status = findViewById(R.id.textView_IN);
        btnText_Status = findViewById(R.id.btnText_Status);

        txt_DefaultOUT = findViewById(R.id.textView_ShiftNameOUT);
        txt_TimeDateOUT = findViewById(R.id.textView_DateOUT);
        txt_StatusOUT = findViewById(R.id.textView_OUT);
        btnText_StatusOUT = findViewById(R.id.btnText_StatusOUT);


        txt_Default = findViewById(R.id.textView_ShiftName);
        txt_TimeDate = findViewById(R.id.textView_Date);
        txt_Status = findViewById(R.id.textView_IN);
        btnText_Status = findViewById(R.id.btnText_Status);


    }


    private void requestCameraPermission() {
        String permission2 = Manifest.permission.CAMERA;
        int grants = ContextCompat.checkSelfPermission(this, permission2);
        if (grants != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list2 = new String[1];
            permission_list2[0] = permission2;
            ActivityCompat.requestPermissions(this, permission_list2, 1);
        }
        getImageFromCamera();
    }


    void forceGpsON() {

        LocationRequest locationRequest1 = LocationRequest.create();
        locationRequest1.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest1.setInterval(10000);
        locationRequest1.setFastestInterval(10000 / 2);
        LocationSettingsRequest.Builder locationSettingBuilder = new LocationSettingsRequest.Builder();
        locationSettingBuilder.addLocationRequest(locationRequest1);
        locationSettingBuilder.setAlwaysShow(true);
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationSettingBuilder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                updateGPS();
            }
        });


        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        AttendanceActivity.this,
                                        101);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });

    }


    private void startLocationUpdate() {
        try {
            //tv_update.setText("Location is being tracked");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            updateGPS();
        } catch (Exception e) {

        }

    }


    private void updateGPS() {

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                updateUIValue(locationResult.getLastLocation());
            }
        };


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AttendanceActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
                updateUIValue(location);
                currentLocation = location;
                String adds = "";
                Geocoder geocoder = new Geocoder(AttendanceActivity.this);
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    adds = addresses.get(0).getAddressLine(0);

                } catch (Exception ignore) {

                }


                try {
                    sharedPref = getSharedPreferences("location", MODE_PRIVATE);
                    editor = sharedPref.edit();
                    editor.putString("lat", String.valueOf(location.getLatitude()));
                    editor.putString("lang", String.valueOf(location.getLongitude()));
                    editor.putString("address", adds);//0 = not submited, 1 = already submited
                    editor.apply(); // Or editor.commit();
                } catch (Exception ignore) {

                }


            });
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateGPS();
                    }
                }, 3000);

            }

        }
    }


    private void updateUIValue(Location lastLocation) {


        Handler handler = new Handler();
        handler.postDelayed(() -> {

            try {

                Geocoder geocoder = new Geocoder(AttendanceActivity.this);
                try {

                    List<Address> addresses = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
                    Address = addresses.get(0).getAddressLine(0);

                } catch (Exception ignore) {

                }

                if (lastLocation != null) {
                    shift.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                } else {

                }
            } catch (Exception ignore) {

            }

        }, 3000); // Delay in milliseconds

    }

    private void getImageFromCameraOut() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private void getImageFromCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            //Toast.makeText(this, "Permission issue", Toast.LENGTH_SHORT).show();
        }


    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        //startActivity(new Intent(this, MainActivity.class));
//        //this.overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                // GPS enabled by user
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // GPS not enabled
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                forceGpsON();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                ActivityCompat.finishAffinity(AttendanceActivity.this);
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceActivity.this);
                builder.setMessage("Location is Mandatory")
                        .setPositiveButton("Try Again", dialogClickListener)
                        .setNegativeButton("Exit", dialogClickListener)
                        .show();
            }
        }

        switch (requestCode) {
            case 101:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        updateGPS();
                        startLocationUpdate();
                        break;

                    case Activity.RESULT_CANCELED:

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {

                                    case DialogInterface.BUTTON_POSITIVE:
                                        forceGpsON();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        ActivityCompat.finishAffinity(AttendanceActivity.this);
                                        break;

                                }
                            }
                        };


                        AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceActivity.this);
                        builder.setMessage("Location is Mandatory").setPositiveButton("Try Again", dialogClickListener)
                                .setNegativeButton("Exit", dialogClickListener).show();
                        break;
                    default:
                        break;
                }
                break;
        }


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            String img = getTextFromImage(imageBitmap);


            androidx.appcompat.app.AlertDialog.Builder itemDialog = new androidx.appcompat.app.AlertDialog.Builder(AttendanceActivity.this);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemDialog.setCancelable(false);
            final View itemdialogview = inflater.inflate(R.layout.comment_layour, null);


            EditText comment = (EditText) itemdialogview.findViewById(R.id.editTextTextComment);
            TextView btnCommentSave = (TextView) itemdialogview.findViewById(R.id.buttonCommentSave);
            TextView btnCancel = (TextView) itemdialogview.findViewById(R.id.textView76);


            itemDialog.setView(itemdialogview);
            final androidx.appcompat.app.AlertDialog alertDialog = itemDialog.create();
            alertDialog.show();


            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String lat = sharedPref.getString("lat", "");
                    String lang = sharedPref.getString("lang", "");
                    String address = sharedPref.getString("address", "");

                    int userType = status.get(0).getIntMpoType();
                    String TypeUser = "";

                    if (userType == 0) {
                        if (new AuthPrefsDataClass(getApplicationContext()).getRole().equals("ZH")) {
                            TypeUser = "ZH";
                        } else {
                            TypeUser = "MPO";
                        }
                    } else if (userType == 1) {
                        TypeUser = "AH";
                    } else if (userType == 2) {
                        TypeUser = "DH";
                    } else if (userType == 6) {
                        TypeUser = "TEAM";
                    }

                    String convertdis = "0";
                    if (inORout.equals("OUT")) {
                        try {
                            dis = Distance.distance(Double.valueOf(attendanceModelsList.get(0).getStrLATITUDE()),
                                    Double.valueOf(lat), Double.valueOf(attendanceModelsList.get(0).getStrLONGITUDE()), Double.valueOf(lang));
                            convertdis = String.valueOf(dis);
                        } catch (Exception e) {
                            convertdis = "0";
                        }

                    }


                    sendDateToServer(authPrefsDataClass.getUserName(), TypeUser, CardNO, todayDateFormated, lat, lang, address,
                            convertdis, comment.getText().toString(), "Pending", inORout, img);

                    alertDialog.dismiss();
                }
            });

            btnCommentSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //try {


                    String lat = sharedPref.getString("lat", "");
                    String lang = sharedPref.getString("lang", "");
                    String address = sharedPref.getString("address", "");
                    String TypeUser = "USER";
                    String convertdis = "0";

                    try {

                        if (inORout.equals("OUT")) {
                            try {
                                dis = Distance.distance(Double.valueOf(attendanceModelsList.get(0).getStrLATITUDE()), Double.valueOf(lat), Double.valueOf(attendanceModelsList.get(0).getStrLONGITUDE()), Double.valueOf(lang));
                                convertdis = String.valueOf(dis);
                            } catch (Exception e) {
                                convertdis = "0";
                            }

                        }

                    }catch (Exception e){
                        Toast.makeText(AttendanceActivity.this, "fail try again", Toast.LENGTH_SHORT).show();
                    }


                    sendDateToServer(authPrefsDataClass.getUserName(), TypeUser, CardNO, todayDateFormated, lat, lang, address, convertdis, comment.getText().toString(), "Pending", inORout, img);

                    alertDialog.dismiss();
//                    } catch (Exception e) {
//                        Toast.makeText(AttendanceActivity.this, "Check Location & Internet", Toast.LENGTH_SHORT).show();
//                        alertDialog.dismiss();
//                    }

                }
            });

        }

    }

    public static String getTextFromImage(Bitmap bitmap) {

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(imageView.getContext(), "upda" + imageString.substring(0, 50), Toast.LENGTH_LONG).show();
            imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(decodedImage);

            return imageString;
        } catch (Exception e) {
            Log.d("Error From Image Catch", "" + e);
            return "";
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void whenClickBack(View view) {
        startActivity(new Intent(this, MainActivity.class));
        //this.overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
    }


    void sendDateToServer(String strUSER_NAME, String strROLE, String strEMP_CARD_NO, String strATTEN_DATEIN, String strLATITUDE, String strLONGITUDE,
                          String strADDRESS, String intDISTANCE, String strATTEN_COMMENTS, String strACTION, String strATTEN_STATUS, String imageString) {


        AttendanceModel attendanceModel = new AttendanceModel();
        try {

            if (new AuthPrefsDataClass(getApplicationContext()).getRole().equals("ZH")) {
                attendanceModel.setStrUSERNAME(new AuthPrefsDataClass(getApplicationContext()).getZoneName());
            } else {
                attendanceModel.setStrUSERNAME(strUSER_NAME);
            }

        } catch (Exception ignore) {

        }


        try {
            // Get the current time
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String currentTime = sdf.format(new Date());
            attendanceModel.setStrATTENTIMEIN(currentTime);
        } catch (Exception ignore) {

        }


        if (strADDRESS.isEmpty() || strLATITUDE.isEmpty() || strLONGITUDE.isEmpty()) {
            Toast.makeText(this, "Address/Location Not Found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (strUSER_NAME.isEmpty()) {
            Toast.makeText(this, "Your UserName Not Found", Toast.LENGTH_SHORT).show();
            return;
        }


        if (strROLE.isEmpty()) {
            Toast.makeText(this, "Your Role Not Found" + strROLE, Toast.LENGTH_SHORT).show();
            return;
        }


        if (imageString.isEmpty()) {
            Toast.makeText(this, "Your Image Not Found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (strEMP_CARD_NO.isEmpty()) {
            Toast.makeText(this, "Your CardNo Not Found", Toast.LENGTH_SHORT).show();
            return;
        }
        attendanceModel.setStrUSERNAME(strUSER_NAME);
        attendanceModel.setStrROLE(strROLE);
        attendanceModel.setStrEMPCARDNO(strEMP_CARD_NO);
        attendanceModel.setStrATTENDATEIN(strATTEN_DATEIN);
        attendanceModel.setStrLATITUDE(strLATITUDE);
        attendanceModel.setStrLONGITUDE(strLONGITUDE);
        attendanceModel.setStrADDRESS(strADDRESS);
        attendanceModel.setIntDISTANCE(intDISTANCE);
        attendanceModel.setStrATTENSHIFT("");
        attendanceModel.setStrATTENCOMMENTS(strATTEN_COMMENTS);
        attendanceModel.setStrACTION(strACTION);
        attendanceModel.setStrATTENSTATUS(strATTEN_STATUS);
        attendanceModel.setStrEMPIMAGE(imageString);//imageString
        attendanceModel.setAppsVersion(VersionCodeString);

        List<AttendanceModel> leaveList = new ArrayList<>();
        leaveList.add(attendanceModel);

        Gson gson = new Gson();
        String json = gson.toJson(attendanceModel);
        JsonObject jsonObject = null;
        jsonObject = new JsonParser().parse(json).getAsJsonObject();


        Log.d("Hello=", "" + jsonObject);


        APIService apiservice = ApiClient.getRetrofit().create(APIService.class);
        //APIService apiservice = retrofitSms.create(APIService.class);
        Call<JsonObject> call = apiservice.getAttendanceDataSubmit(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    //Toast.makeText(AttendanceActivity.this, "===" + response.code(), Toast.LENGTH_SHORT).show();
                    sharedPref = getSharedPreferences("location", MODE_PRIVATE);
                    editor = sharedPref.edit();
                    editor.clear();
                    btnIN.setClickable(false);
                    btnRefressh();
                    Toast.makeText(AttendanceActivity.this, "Done "+response.body().toString(), Toast.LENGTH_SHORT).show();

                } else {

                    //dbHelper.attendanceInsert(leaveList);
                    sharedPref = getSharedPreferences("location", MODE_PRIVATE);
                    editor = sharedPref.edit();
                    editor.clear();
                    btnIN.setClickable(false);
                    btnRefressh();
                    Toast.makeText(AttendanceActivity.this, "Please Try Again Later\n" + response.body().toString(), Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                //Toast.makeText(AttendanceActivity.this, "fail "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }



    @Override
    protected void onResume() {
        super.onResume();
        //LocationOffMassage.showLocationOffMessage(this);
    }

    public void WhenClickCalenderFromAttendance(View view) {
        openDatePickerDialog();

    }

    private void openDatePickerDialog() {
        // Get the current date
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(AttendanceActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Set the selected date in Calendar
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    // Format the date in "dd-MM-yyyy"
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    selectedDate = dateFormat.format(calendar.getTime());

                    try {
                        String[] dateFormatString = selectedDate.split("-");
                        todayDateFormated = dateFormatString[2] + "-" + dateFormatString[1] + "-" + dateFormatString[0];
                        Toast.makeText(this, todayDateFormated, Toast.LENGTH_SHORT).show();
                        //todayShowDate = dff1.format(Calendar.getInstance().getTime());
                        btnRefressh();
                    } catch (Exception ignored) {

                    }


                }, year, month, day);

        Calendar currentDate = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMaxDate(currentDate.getTimeInMillis());
        datePickerDialog.show();

    }

    public void ClickAttendanceSummery(View view) {
    }

    public void ClickApprove(View view) {
    }




}