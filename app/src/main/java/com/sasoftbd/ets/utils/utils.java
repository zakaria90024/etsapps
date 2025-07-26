package com.sasoftbd.ets.utils;

import static android.content.Context.BATTERY_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Base64;
import android.widget.ImageView;

public class utils {

    public static int getBatteryPercentage(Context context) {

        if (Build.VERSION.SDK_INT >= 21) {
            BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {

            IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, iFilter);
            int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
            int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;
            double batteryPct = level / (double) scale;

            return (int) (batteryPct * 100);
        }
    }


    public void setImageFromBase64(String base64Image, ImageView imageView) {
        try {
            // 🧼 Clean Base64 string (optional, যদি সমস্যা থাকে)
            base64Image = base64Image.replaceAll("\\s", "");

            // Decode Base64 string
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);

            // Convert to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            // Set to ImageView
            imageView.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
