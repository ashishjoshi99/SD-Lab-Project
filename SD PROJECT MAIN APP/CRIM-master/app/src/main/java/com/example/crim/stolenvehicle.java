package com.example.crim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class stolenvehicle extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText regno, engno, chassis;
    Button submit;
    Spinner vtypespinner1;
    ImageView upload;
    String[] code1 = {"Car", "Ambulance", "Truck", "Van", "Tractor", "Earth Mover", "Trailer", "Mini Bus"};
    static final int GALLERY_CODE_REQ = 3;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private Uri mImagevehicle = null;
    private Uri dloaduri = null;
    private String filelink;
    boolean unfilled = false;
    ProgressBar mv_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stolenvehicle);


        regno = findViewById(R.id.regno);
        engno = findViewById(R.id.engnumber);
        chassis = findViewById(R.id.chassisno);
        submit = findViewById(R.id.stolesubmit);
        upload = findViewById(R.id.uploadvh);
        vtypespinner1 = findViewById(R.id.vtypespinner);
        vtypespinner1.setOnItemSelectedListener(this);
        mv_pb = findViewById(R.id.mv_pgbar);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter adaptervtypespinner = new ArrayAdapter<>(this, R.layout.spinner, code1);
        adaptervtypespinner.setDropDownViewResource(R.layout.spinner);
        vtypespinner1.setAdapter(adaptervtypespinner);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE_REQ);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfilled = false;
                if (regno.getText().toString().trim().isEmpty()) {
                    Toast.makeText(stolenvehicle.this, "Enter Reg Number", Toast.LENGTH_SHORT).show();
                    unfilled = true;
                }
                if (engno.getText().toString().trim().isEmpty()) {
                    Toast.makeText(stolenvehicle.this, "Enter Eng Number", Toast.LENGTH_SHORT).show();
                    unfilled = true;
                }
                if (chassis.getText().toString().trim().isEmpty()) {
                    Toast.makeText(stolenvehicle.this, "Enter Chassis Number", Toast.LENGTH_SHORT).show();
                    unfilled = true;
                }
                if(mImagevehicle==null){
                    Toast.makeText(stolenvehicle.this, "Upload Image", Toast.LENGTH_SHORT).show();
                    unfilled=true;
                }
                if (!unfilled) {
                    mv_pb.setVisibility(View.VISIBLE);
                    final String email = mAuth.getCurrentUser().getEmail();
                    final String reg_no = regno.getText().toString().trim();
                    final String eng_no = engno.getText().toString().trim();
                    final String chas_no = chassis.getText().toString().trim();
                    final String veh_type = vtypespinner1.getSelectedItem().toString().trim(); //Get selected item from spinner.
                    final String unique_id = email + " " + reg_no;
                    final StorageReference vehicle_img = mStorageRef.child("Report Missing Vehicle").child(email).child(mImagevehicle.getLastPathSegment());
                    vehicle_img.putFile(mImagevehicle).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            vehicle_img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    filelink = uri.toString();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    DocumentReference documentReference = db.collection("StolenVehicleRecords").document(unique_id);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("reg_no", reg_no);
                                    user.put("eng_no", eng_no);
                                    user.put("chas_no", chas_no);
                                    user.put("veh_type", veh_type);
                                    user.put("veh_img", filelink);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mv_pb.setVisibility(View.GONE);

                                            Toast.makeText(stolenvehicle.this, "Details Added to Database.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), homepage.class));

                                        }
                                    });
                                }
                            });

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}