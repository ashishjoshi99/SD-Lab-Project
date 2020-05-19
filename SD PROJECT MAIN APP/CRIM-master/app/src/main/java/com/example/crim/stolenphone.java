package com.example.crim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class stolenphone extends AppCompatActivity {
    Button reportphone;
    EditText brand, model, color, imei, phone_no1, lastseen_date, lastseen_time, details;
    FirebaseAuth mAuth;
    ProgressBar pgbar;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stolenphone);
        reportphone = findViewById(R.id.reportphone);
        brand = findViewById(R.id.brand);
        model = findViewById(R.id.model);
        color = findViewById(R.id.color);
        phone_no1 = findViewById(R.id.lostphone);
        imei = findViewById(R.id.imei);
        lastseen_date = findViewById(R.id.date);
        lastseen_time = findViewById(R.id.losttime);
        details = findViewById(R.id.lostdetail);
        mAuth = FirebaseAuth.getInstance();
        pgbar = findViewById(R.id.progressBar2);


        reportphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                if (brand.getText().toString().isEmpty()) {
                    Toast.makeText(stolenphone.this, "Enter data in Mandatory Fields.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (model.getText().toString().isEmpty()) {
                    Toast.makeText(stolenphone.this, "Enter data in Mandatory Fields.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (phone_no1.getText().toString().isEmpty()) {
                    Toast.makeText(stolenphone.this, "Enter data in Mandatory Fields.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (color.getText().toString().isEmpty()) {
                    Toast.makeText(stolenphone.this, "Enter data in Mandatory Fields.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (details.getText().toString().isEmpty()) {
                    Toast.makeText(stolenphone.this, "Enter data in Mandatory Fields.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (imei.getText().toString().isEmpty()) {
                    Toast.makeText(stolenphone.this, "Enter data in Mandatory Fields.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (!flag) {
                    pgbar.setVisibility(View.VISIBLE);
                    String date = lastseen_date.getText().toString().trim();
                    String time = lastseen_time.getText().toString().trim();
                    final String lp_brand = brand.getText().toString().trim();
                    final String lp_model = model.getText().toString().trim();
                    final String lp_phone = phone_no1.getText().toString().trim();
                    final String lp_color = color.getText().toString().trim();
                    final String lp_imei = imei.getText().toString().trim();
                    final String lp_details = details.getText().toString().trim();
                    final String lp_datetime = date + " " + time;
                    final String emailid = mAuth.getCurrentUser().getEmail();
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String unique_id = emailid + " " + lp_imei;
                    DocumentReference documentReference = db.collection("ReportPhoneRecords").document(unique_id);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Brand", lp_brand);
                    user.put("Model", lp_model);
                    user.put("Phone Number", lp_phone);
                    user.put("Color", lp_color);
                    user.put("Date", lp_datetime);
                    user.put("IMEI", lp_imei);
                    user.put("Additional Details", lp_details);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(stolenphone.this, "Details Submitted Sucessfully .Please visit nearest police station for verification", Toast.LENGTH_SHORT).show();
                            pgbar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(), homepage.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(stolenphone.this, "Failed.Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

}
