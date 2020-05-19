package com.example.crim;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class eme_contacts extends AppCompatActivity {

    ImageView call1, call2, call3, call4, call5, call6, call7, call8, call9, call10;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eme_contacts);

        call1 = findViewById(R.id.call1);
        call2 = findViewById(R.id.call2);
        call3 = findViewById(R.id.call3);
        call4 = findViewById(R.id.call4);
        call5 = findViewById(R.id.call5);
        call6 = findViewById(R.id.call6);
        call7 = findViewById(R.id.call7);
        call8 = findViewById(R.id.call8);
        call9 = findViewById(R.id.call9);
        call10 = findViewById(R.id.call10);

        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });

        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });

        call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });

        call4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });

        call5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });

        call6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });

        call7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });

        call8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });

        call9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });

        call10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();

            }
        });


    }

    public void makephonecall() {
        String number = "8860979330";

        if (ContextCompat.checkSelfPermission(eme_contacts.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(eme_contacts.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makephonecall();
            } else {
                Toast.makeText(eme_contacts.this, "Permission Denied", Toast.LENGTH_SHORT).show();

            }
        }
    }
}