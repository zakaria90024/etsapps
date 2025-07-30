package com.sasoftbd.ets.vpn;


import android.content.Intent;
import android.net.VpnService;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MyVpnService extends VpnService implements Runnable {

    private Thread thread;
    private ParcelFileDescriptor vpnInterface;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (thread != null) {
            thread.interrupt();
        }
        thread = new Thread(this, "MyVpnThread");
        thread.start();
        return START_STICKY;
    }

    @Override
    public void run() {
        try {
            Builder builder = new Builder();
            builder.setSession("MyVPN")
                    .addAddress("10.0.0.2", 24)
                    .addDnsServer("8.8.8.8")
                    .addRoute("0.0.0.0", 0);

            vpnInterface = builder.establish();

            FileChannel inputChannel = new FileInputStream(vpnInterface.getFileDescriptor()).getChannel();
            FileChannel outputChannel = new FileOutputStream(vpnInterface.getFileDescriptor()).getChannel();

            ByteBuffer packet = ByteBuffer.allocate(32767);
            while (!Thread.interrupted()) {
                int length = inputChannel.read(packet);
                if (length > 0) {
                    Log.d("VPN", "Packet captured: " + length + " bytes");
                    packet.clear();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (vpnInterface != null) {
                vpnInterface.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
