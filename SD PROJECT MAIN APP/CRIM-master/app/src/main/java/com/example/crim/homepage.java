package com.example.crim;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class homepage extends AppCompatActivity {

    String[] list;
    ImageView setting, profile;
    private static final int REQUEST_CALL = 1;
    final int SEND_SMS_PERMISSION_CODE=1;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ImageSlider imgslider = findViewById(R.id.imgslider);
        GridLayout gridLayout = findViewById(R.id.home_grid);
        setting = findViewById(R.id.settings);
        profile = findViewById(R.id.profile);

        fab=findViewById(R.id.fab);
        fab.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)){
            fab.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_CODE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSend();
                Intent i= new Intent(homepage.this,message.class);
                startActivity(i);

            }
        });

//        setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(homepage.this, "Work in progress:(", Toast.LENGTH_SHORT).show();
//            }
//        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.example.crim.profile.class));
                overridePendingTransition(R.anim.slide_down, android.R.anim.fade_out);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepage.this, settings.class));
                overridePendingTransition(R.anim.slide_down, android.R.anim.fade_out);
            }
        });
        //Image auto sliderview

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


    public void onSend(){

        String phone="7349470092";
        String message= "Help Needed SOS";

        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phone,null,message,null,null);
            Toast.makeText(this,"SOS Sent", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Error Occured", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkPermission(String permission){
        int check= ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    private void setClickEvent(final GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finali = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finali == 0) {

                        // for dialog interface

                        list = new String[]{"Emergency (DIAL 100)", "Non Emergency"};
                        AlertDialog.Builder mb = new AlertDialog.Builder(homepage.this);

                        mb.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (list[i].equals("Non Emergency")) {
                                    Intent intent = new Intent(homepage.this, cardavtivity0.class);
                                    startActivity(intent);
                                    dialogInterface.dismiss();
                                }

                                if (list[i].equals("Emergency (DIAL 100)")) {
                                    makephonecall();
                                    dialogInterface.dismiss();
                                }


                            }
                        });

                        mb.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog mdia = mb.create();
                        mdia.show();


                    } else if (finali == 1) {
                        startActivity(new Intent(getApplicationContext(), missingperson.class));
                    } else if (finali == 2) {
                        startActivity(new Intent(getApplicationContext(), stolenphone.class));

                    } else if (finali == 3) {
                        startActivity(new Intent(getApplicationContext(), stolenvehicle.class));

                    } else if (finali == 4) {
                        Intent inteee = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("geo:0,0?q= Neatest Police Station"));
                        startActivity(inteee);
                    } else if (finali == 5) {
                        startActivity(new Intent(getApplicationContext(), eme_contacts.class));

                    } else {
                        startActivity(new Intent(getApplicationContext(), display.class));
                    }
                    //Continue Else if for remaining cardView Activities


                }
            });
        }
    }

    public void makephonecall() {
        String number = "8860979330";

        if (ContextCompat.checkSelfPermission(homepage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(homepage.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
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
                Toast.makeText(homepage.this, "Permission Denied", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
