package com.example.crim;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class missingperson extends AppCompatActivity {

    final Calendar mycalender = Calendar.getInstance();
    EditText mp_name, mp_age, mp_sex, mp_height, mp_haircolor, mp_birthmark, mp_missingdate;
    Button mp_submitbtn;
    ImageView uploadmp;
    static final int GALLERY_CODE_REQ = 3;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private Uri mImagevehicle = null;
    private String filelink;
    boolean flag = false;
    ProgressBar mp_pb;

    DatePickerDialog.OnDateSetListener missingdate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mycalender.set(Calendar.YEAR, year);
            mycalender.set(Calendar.MONTH, month);
            mycalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

        private void updateLabel() {

            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            mp_missingdate.setText(sdf.format(mycalender.getTime()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missingperson);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        mp_missingdate = findViewById(R.id.mp_missingdate);
        mp_name = findViewById(R.id.mp_name);
        mp_age = findViewById(R.id.mp_age);
        mp_height = findViewById(R.id.mp_height);
        mp_sex = findViewById(R.id.mp_gender);
        mp_haircolor = findViewById(R.id.mp_haircolor);
        mp_birthmark = findViewById(R.id.mp_birthmark);
        mp_submitbtn = findViewById(R.id.mp_submit);
        uploadmp = findViewById(R.id.upload_mp);
        mp_pb = findViewById(R.id.mp_pgrbar);


        uploadmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE_REQ);
            }
        });


        mp_missingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(missingperson.this, missingdate, mycalender.get(Calendar.YEAR), mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mp_submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                if (mp_name.getText().toString().isEmpty()) {
                    Toast.makeText(missingperson.this, "Please enter Name of the Missing Person.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (mp_age.getText().toString().isEmpty()) {
                    Toast.makeText(missingperson.this, "Please enter Age of missing person.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (mp_sex.getText().toString().isEmpty()) {
                    Toast.makeText(missingperson.this, "Please enter Gender of missing person.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (mp_haircolor.getText().toString().isEmpty()) {
                    Toast.makeText(missingperson.this, "Please enter Hair Color of missing person.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (mp_missingdate.getText().toString().isEmpty()) {
                    Toast.makeText(missingperson.this, "Please enter Date when person was found missing.", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if(mImagevehicle==null){
                    Toast.makeText(missingperson.this, "Upload Image", Toast.LENGTH_SHORT).show();
                    flag=true;
                }
                if (!flag) {
                    mp_pb.setVisibility(View.VISIBLE);
                    final String email = mAuth.getCurrentUser().getEmail();
                    final StorageReference vehicle_img = mStorageRef.child("Report Missing Person").child(email).child(mImagevehicle.getLastPathSegment());
                    final String mname = mp_name.getText().toString().trim();
                    final String mdate = mp_missingdate.getText().toString().trim();
                    final String mage = mp_age.getText().toString().trim();
                    final String mheight = mp_height.getText().toString().trim();
                    final String msex = mp_sex.getText().toString().trim();
                    final String mhair = mp_haircolor.getText().toString().trim();
                    final String mbmark = mp_birthmark.getText().toString().trim();
                    final String unique_id = email + " " + mname;


                    vehicle_img.putFile(mImagevehicle).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                            vehicle_img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    filelink = uri.toString();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Toast.makeText(missingperson.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                    DocumentReference documentReference = db.collection("missinggpersonrec").document(unique_id);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("mname", mname);
                                    user.put("mdate", mdate);
                                    user.put("mage", mage);
                                    user.put("mheight", mheight);
                                    user.put("msex", msex);
                                    user.put("mhairclr", mhair);
                                    user.put("mbmark", mbmark);
                                    user.put("murl", filelink);

                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(missingperson.this, "Details added to Database", Toast.LENGTH_SHORT).show();
                                            mp_pb.setVisibility(View.GONE);
                                            startActivity(new Intent(getApplicationContext(), homepage.class));
                                            finish();

                                        }
                                    });


                                }
                            });
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(missingperson.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE_REQ && resultCode == RESULT_OK) {

            mImagevehicle = data.getData();


        }
    }
}