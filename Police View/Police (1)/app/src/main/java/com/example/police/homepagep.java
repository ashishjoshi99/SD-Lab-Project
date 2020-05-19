package com.example.police;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class homepagep extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_CODE = 1;
    FloatingActionButton fab;
    String[] list;
    ImageView setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepagep);

        ImageSlider imgslider = findViewById(R.id.imgslider);
        GridLayout gridLayout = findViewById(R.id.home_grid);
        setting = findViewById(R.id.settings);
        fab = findViewById(R.id.fab);
        fab.setEnabled(false);
        // for sos
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            fab.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_CODE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSend();
                Intent i = new Intent(homepagep.this, message.class);
                startActivity(i);

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(homepagep.this, "Work in progress:(", Toast.LENGTH_SHORT).show();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepagep.this, settngsp.class));
                overridePendingTransition(R.anim.slide_down, android.R.anim.fade_out);
            }
        });
        //Image auto slider view

        List<SlideModel> slidemodels = new ArrayList<>();
        slidemodels.add(new SlideModel(R.drawable.police1));
        slidemodels.add(new SlideModel(R.drawable.police2));
        slidemodels.add(new SlideModel(R.drawable.police3));
        slidemodels.add(new SlideModel(R.drawable.police4));
        slidemodels.add(new SlideModel(R.drawable.police5));
        slidemodels.add(new SlideModel(R.drawable.police1));
        imgslider.setImageList(slidemodels, true);

        setClickEvent(gridLayout);


    }
    // for sos

    public void onSend() {

        String phone = "7349470092";
        String message = "Help Needed SOS";

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(this, "SOS Sent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error Occured", Toast.LENGTH_SHORT).show();
        }

    }
    // permission checking for sos

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    // for grid layout
    private void setClickEvent(final GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finali = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finali == 0) {
                        startActivity(new Intent(getApplicationContext(), incident_reports.class));

                    } else if (finali == 1) {
                        startActivity(new Intent(getApplicationContext(), missign_person_list.class));

                    } else if (finali == 2) {

                        startActivity(new Intent(getApplicationContext(), missing_phone_list.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), stolen_vehicle_list.class));
                    }


                }
            });
        }
    }

    // defines if back is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), login_screenp.class));
        finish();
    }
}
