package com.example.police;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class login_screenp extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_CODE = 1; // asking for SOS permission
    FloatingActionButton fab1;

    Button login;
    EditText email_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screenp);

        login = findViewById(R.id.login);
        email_login = findViewById(R.id.email_login);
        fab1 = findViewById(R.id.fab1);
        fab1.setEnabled(false);
        if (checkPermission(Manifest.permission.SEND_SMS)) {  //checking whether the permission is provided or not
            fab1.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_CODE);
        }
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // for floating sos button
                onSend();
                Intent i = new Intent(login_screenp.this, message.class);
                startActivity(i);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// for login button

                if (email_login.getText().toString().trim().isEmpty()) {
                    Toast.makeText(login_screenp.this, "Please Enter a valid email id", Toast.LENGTH_SHORT).show();

                } else {
                    String pin = email_login.getText().toString().trim();
                    if (pin.equals("14369")) {
                        Intent i = new Intent(login_screenp.this, homepagep.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(login_screenp.this, "Invalid Pin", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


    }

    public void onSend() {  // sends message to the given no.

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


    // checks for permission
    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

}
