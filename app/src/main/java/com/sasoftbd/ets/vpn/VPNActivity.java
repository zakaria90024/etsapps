package com.sasoftbd.ets.vpn;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sasoftbd.ets.R;

public class VPNActivity extends AppCompatActivity {

    private static final int VPN_REQUEST_CODE = 1;
    private boolean vpnRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpnactivity);

        Button btnVpn = findViewById(R.id.btnVpn);

        btnVpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!vpnRunning) {
                    Intent intent = VpnService.prepare(VPNActivity.this);
                    if (intent != null) {
                        startActivityForResult(intent, VPN_REQUEST_CODE);
                    } else {
                        onActivityResult(VPN_REQUEST_CODE, RESULT_OK, null);
                    }
                } else {
                    stopService(new Intent(VPNActivity.this, MyVpnService.class));
                    vpnRunning = false;
                    Toast.makeText(VPNActivity.this, "VPN Disconnected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VPN_REQUEST_CODE && resultCode == RESULT_OK) {
            startService(new Intent(this, MyVpnService.class));
            vpnRunning = true;
            Toast.makeText(this, "VPN Connected", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
