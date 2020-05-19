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

import java.util.HashMap;
import java.util.Map;

public class cardavtivity0 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner2, spinner4;
    String[] code1 = {"", "Belgaum", "Bagalkot","Hubli","Dharwad","Bangalore"};
    String[] code2 = {"", "Chain Snatching", "Eve Teasing","Pick Pocketing","Robbery","Vandalism","Shoplifting"};
    Button submt;
    ImageView upload;
    private static final int GALLERY_CODE = 1;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private Uri mImageUri = null;
    EditText rep_location, rep_details;
    private String filelink;
    boolean flag = false;
    ProgressBar ca_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardavtivity0);

        spinner2 = findViewById(R.id.spinner2);
        spinner4 = findViewById(R.id.spinner4);
        rep_location = findViewById(R.id.ri_location);
        rep_details = findViewById(R.id.ri_details);
        spinner2.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);
        upload = findViewById(R.id.upload);
        ca_pb = findViewById(R.id.ca0);
        submt = findViewById(R.id.report);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        ArrayAdapter<String> adapterspinner2 = new ArrayAdapter<>(this, R.layout.spinner, code1);
        adapterspinner2.setDropDownViewResource(R.layout.spinner);
        spinner2.setAdapter(adapterspinner2);

        ArrayAdapter<String> adapterspinner4 = new ArrayAdapter<>(this, R.layout.spinner, code2);
        adapterspinner4.setDropDownViewResource(R.layout.spinner);
        spinner4.setAdapter(adapterspinner4);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE);


            }
        });

        submt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                if (spinner2.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(cardavtivity0.this, "Please Enter mandatory fields", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (spinner4.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(cardavtivity0.this, "Please Enter mandatory fields", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (rep_location.getText().toString().isEmpty()) {
                    Toast.makeText(cardavtivity0.this, "Please Enter mandatory fields", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if (rep_details.getText().toString().isEmpty()) {
                    Toast.makeText(cardavtivity0.this, "Please Enter mandatory fields", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                if(mImageUri==null){
                    Toast.makeText(cardavtivity0.this, "Upload Image Mandatory", Toast.LENGTH_SHORT).show();
                    flag=true;
                }
                if (!flag) {
                    ca_pb.setVisibility(View.VISIBLE);
                    String userEm = mAuth.getCurrentUser().getEmail();
                    final String rep_area = spinner2.getSelectedItem().toString().trim();
                    final String rep_type = spinner4.getSelectedItem().toString().trim();
                    final String rep_loc = rep_location.getText().toString().trim();
                    final String rep_det = rep_details.getText().toString().trim();
                    final String unique_id = userEm + " " + rep_loc;

                    final StorageReference file_path = mStorageRef.child("Incident Report").child(userEm).child(mImageUri.getLastPathSegment());
                    file_path.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            file_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    filelink = uri.toString();
                                    Toast.makeText(cardavtivity0.this, "Successfully uploaded Image", Toast.LENGTH_SHORT).show();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    DocumentReference documentReference = db.collection("ReportIncidentRecords").document(unique_id);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("IncidentCity", rep_area);
                                    user.put("IncidentType", rep_type);
                                    user.put("LocationofIncident", rep_loc);
                                    user.put("IncidentDetails", rep_det);
                                    user.put("ImageUrl", filelink);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(cardavtivity0.this, "Details Submitted Successfly.Please visit Nearest Police Station for Verification", Toast.LENGTH_SHORT).show();
                                            ca_pb.setVisibility(View.GONE);
                                            startActivity(new Intent(getApplicationContext(), homepage.class));
                                            finishAffinity();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(cardavtivity0.this, "Failed.Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(cardavtivity0.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {


            mImageUri = data.getData();


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
