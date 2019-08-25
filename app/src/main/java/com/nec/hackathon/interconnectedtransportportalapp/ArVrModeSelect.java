package com.nec.hackathon.interconnectedtransportportalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArVrModeSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_vr_mode_select);

        Button arFeatureBtn = findViewById(R.id.arFeatureBtn);
        Button vrFeatureBtn = findViewById(R.id.vrFeatureBtn);

        arFeatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.NEC.ARwindow");
                startActivity(intent);
            }
        });

        vrFeatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                FullscreenVRPlayerActivity.launch(ArVrModeSelect.this,
//                        40004, // Replace with your Content ID
//                        true,       // Autoplay
//                        Mode.ON     // Run in Cardboard mode
//                );

                Intent intent = new Intent(ArVrModeSelect.this, vrFeatureAccess.class);
                CommonUtils commonUtils = new CommonUtils();
                commonUtils.waitAndNavigate(ArVrModeSelect.this, intent, "Enabling VR mode, please wait..", 2000);
            }
        });

    }
}
