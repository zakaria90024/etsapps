package com.sasoftbd.ets.latlong;


import static com.sasoftbd.ets.utils.Dates.calculateDistance;
import static com.sasoftbd.ets.utils.Dates.calculateTimeDifference;
import static com.sasoftbd.ets.utils.Dates.convertKmToMeters;
import static com.sasoftbd.ets.utils.Dates.convertMillisecondsToMinutesAndSeconds;
import static com.sasoftbd.ets.utils.Dates.getCurrentTime;
import static com.sasoftbd.ets.utils.utils.getBatteryPercentage;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sasoftbd.ets.MainActivity;
import com.sasoftbd.ets.R;
import com.sasoftbd.ets.model.LocationPostModel;
import com.sasoftbd.ets.network.ApiClient;
import com.sasoftbd.ets.utils.AuthPrefsDataClass;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationService extends Service {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    //public static final long INTERVAL_TIME = 120000;//TWO MIN,  //600000; //10 MINUIT'S
    public static final long INTERVAL_TIME = 1200000;//20 MIN,  //600000; //10 MINUIT'S
    //public static final long INTERVAL_TIME = 6000;//20 MIN,  //600000; //10 MINUIT'S
    AuthPrefsDataClass authPrefsDataClass;

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        startForegroundService();
        requestLocationUpdates();
        authPrefsDataClass = new AuthPrefsDataClass(getApplicationContext());
        authPrefsDataClass.getCardNO();
    }


    private void startForegroundService() {

        String channelId = "LocationServiceChannel";
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    channelId, "Location Service Channel", NotificationManager.IMPORTANCE_DEFAULT
            );
        }

        NotificationManager manager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            manager = getSystemService(NotificationManager.class);
        }

        if (manager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(channel);
            }
        }


        // Create an intent that opens the MainActivity when the notification is clicked
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );


        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("ETS Tracking")
                .setContentText("Running in Background")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }


//    private void startForegroundService() {
//
//        String channelId = "LocationServiceChannel";
//        NotificationChannel channel = null;

    /// /        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
    /// /            channel = new NotificationChannel(
    /// /                    channelId, "Location Service Channel", NotificationManager.IMPORTANCE_DEFAULT
    /// /            );
    /// /        }
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channell = new NotificationChannel(
//                    channelId, "Location Service Channel", NotificationManager.IMPORTANCE_HIGH
//            );
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            if (manager != null) {
//                manager.createNotificationChannel(channell);
//            }
//        }
//
//        NotificationManager manager = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            manager = getSystemService(NotificationManager.class);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//            if (!pm.isIgnoringBatteryOptimizations(getPackageName())) {
//                Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
//                startActivity(intent);
//            }
//        }
//
//        if (manager != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                manager.createNotificationChannel(channel);
//            }
//        }
//
//
//        // Create an intent that opens the MainActivity when the notification is clicked
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
//        );
//
//
//        Notification notification = new NotificationCompat.Builder(this, channelId)
//                .setContentTitle("Deeplaid Tracking")
//                .setContentText("Running in Background")
//                .setSmallIcon(R.drawable.logo)
//                .setContentIntent(pendingIntent)
//                .build();
//
//        startForeground(1, notification);
//    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            fusedLocationClient.removeLocationUpdates(locationCallback);
        } catch (Exception ignore) {

        }
    }


    private void requestLocationUpdates() {
        //Toast.makeText(LocationService.this, location + "\n" + fullAddress, Toast.LENGTH_SHORT).show();


        LocationRequest request = new LocationRequest();
        request.setInterval(INTERVAL_TIME);
        request.setFastestInterval(3000); //PHONE MOVEMENT TIME
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission == PackageManager.PERMISSION_GRANTED) {

            client.requestLocationUpdates(request, new LocationCallback() {

                @Override

                public void onLocationResult(LocationResult locationResult) {


                    String location = "Latitude : " + locationResult.getLastLocation().getLatitude() +
                            "\nLongitude : " + locationResult.getLastLocation().getLongitude();
                    double latitude = locationResult.getLastLocation().getLatitude();  // Replace with your latitude
                    double longitude = locationResult.getLastLocation().getLongitude();
                    //double Time = locationResult.getLastLocation().getTime();

                    //Log.d("getytime", ""+Time);


                    String fullAddress = getCompleteAddressString(latitude, longitude);
                    if (fullAddress != null) {
                        System.out.println("Full Address: " + fullAddress);
                    } else {
                        System.out.println("Address not found!");
                    }


                    int batteryInt = getBatteryPercentage(getApplicationContext());


                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefLocation", Context.MODE_PRIVATE);
                    String addressLocation = sharedPreferences.getString("AddressLocation", "");

                    if (addressLocation.isEmpty()) {

                        Log.d("Called", "first called again");

                        LocationPostModel locationPostModel = new LocationPostModel(getCurrentTime(), getCurrentTime(), "Travelling", "", fullAddress, String.valueOf(batteryInt) + "%", "", String.valueOf(latitude), String.valueOf(longitude), "dfd", authPrefsDataClass.getCardNO(), authPrefsDataClass.getRole());
                        Gson gson = new Gson();
                        String json = gson.toJson(locationPostModel);
                        JsonObject jsonObject = null;
                        jsonObject = new JsonParser().parse(json).getAsJsonObject();


                        APIService apiservice = ApiClient.getRetrofit().create(APIService.class);
                        Call<String> callLocation = apiservice.postLocation(jsonObject);
                        Log.d("hhhhhhhhhhr", "" + jsonObject);
                        callLocation.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                                Log.d("Called", "first called Submit");

                                //old data
                                //Toast.makeText(LocationService.this, "Inserted Successfully First", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferenceReturn = getSharedPreferences("MySharedPrefLocation", MODE_PRIVATE);
                                SharedPreferences.Editor myEditt = sharedPreferenceReturn.edit();
                                myEditt.putString("AddressLocation", fullAddress);
                                myEditt.commit();

                                //sharedPreferenceReturn.edit().clear();

                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefLocationOld", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("StartTimeOld", getCurrentTime());
                                myEdit.putString("EndTimeOld", getCurrentTime());
                                myEdit.putString("AddressOld", fullAddress);
                                myEdit.putString("latold", String.valueOf(latitude));
                                myEdit.putString("longold", String.valueOf(longitude));
                                myEdit.commit();


                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("Called", "First call Fail");
                            }
                        });

                    } else {

                        if (addressLocation.equals(fullAddress)) {
                            //Toast.makeText(LocationService.this, "Same", Toast.LENGTH_SHORT).show();
                            Log.d("SameOldLocation", "Same");
                        } else {

                            try {


                                Log.d("Called1", "yes");
                                SharedPreferences sharedPreferenceoldDataget = getSharedPreferences("MySharedPrefLocationOld", Context.MODE_PRIVATE);
                                String StartTimeOld = sharedPreferenceoldDataget.getString("StartTimeOld", "");
                                String EndTimeOld = sharedPreferenceoldDataget.getString("EndTimeOld", "");
                                String AddressOld = sharedPreferenceoldDataget.getString("AddressOld", "");
                                String latold = sharedPreferenceoldDataget.getString("latold", "");
                                String longold = sharedPreferenceoldDataget.getString("longold", "");

                                double distance = calculateDistance(Double.valueOf(latold), Double.valueOf(longold), latitude, longitude);
                                double distanceMeters = convertKmToMeters(distance);
                                Log.d("Distance", "Distance between locations: " + (int) Math.floor(distance) + " Km " + (int) Math.ceil(distanceMeters) + " m");
                                LocationPostModel locationPostModel = new LocationPostModel(EndTimeOld, getCurrentTime(), calculateTimeDifference(EndTimeOld, getCurrentTime()), (int) Math.floor(distance) + " Km " + (int) Math.ceil(distanceMeters) + " m", fullAddress, String.valueOf(batteryInt) + "%", convertMillisecondsToMinutesAndSeconds(INTERVAL_TIME), String.valueOf(latitude), String.valueOf(longitude), "dfd", authPrefsDataClass.getCardNO(), authPrefsDataClass.getRole());

                                Gson gson = new Gson();
                                String json = gson.toJson(locationPostModel);
                                JsonObject jsonObject = null;
                                jsonObject = new JsonParser().parse(json).getAsJsonObject();


                                APIService apiservice = ApiClient.getRetrofit().create(APIService.class);
                                Call<String> callLocation = apiservice.postLocation(jsonObject);
                                Log.d("hhhhhhhhhhr", "" + jsonObject);
                                callLocation.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {


                                        //Toast.makeText(LocationService.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefLocation", MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                        myEdit.putString("AddressLocation", fullAddress);
                                        myEdit.commit();
                                        Log.d("Called2", "Success Submit");

                                        SharedPreferences sharedPreferencesold = getSharedPreferences("MySharedPrefLocationOld", MODE_PRIVATE);
                                        SharedPreferences.Editor myEditOld = sharedPreferencesold.edit();
                                        myEditOld.putString("StartTimeOld", getCurrentTime());
                                        myEditOld.putString("EndTimeOld", getCurrentTime());
                                        myEditOld.putString("AddressOld", fullAddress);
                                        myEditOld.putString("latold", String.valueOf(latitude));
                                        myEditOld.putString("longold", String.valueOf(longitude));
                                        myEditOld.commit();


                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.d("Called", "second call Fail");
                                        sharedPreferences.edit().clear();
                                    }
                                });

                            } catch (Exception ignored) {

                            }
                        }
                    }


                }

            }, null);

        } else {

            stopSelf();

        }

    }

    public String getCompleteAddressString(double latitude, double longitude) {
        StringBuilder fullAddress = new StringBuilder();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                // Combine different parts of the address
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    fullAddress.append(address.getAddressLine(i)).append(" ");
                }
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return fullAddress.toString().trim();
    }


    public class MyBinder extends Binder {

        public LocationService getService() {

            return LocationService.this;

        }

    }


}
