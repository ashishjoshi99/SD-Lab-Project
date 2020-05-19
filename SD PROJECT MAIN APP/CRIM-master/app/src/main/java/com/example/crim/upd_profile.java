package com.example.crim;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class upd_profile extends AppCompatActivity {


    public static final String TAG = "upd_profile";
    EditText name, email, phone;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userId;
    //    public Uri imgurl;
//    Integer REQUEST_IMAGE_CAPTURE = 100;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_profile);

        name = findViewById(R.id.up_name);
        email = findViewById(R.id.up_email);
        phone = findViewById(R.id.up_phone);
        submit = findViewById(R.id.sub_update);


        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        final FirebaseUser user = mAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = name.getText().toString();
                String uphone = phone.getText().toString();
                String uemail = email.getText().toString();

                final DocumentReference documentReference = fStore.collection("users").document(userId);
                documentReference.update("Name", uname).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(upd_profile.this, "Name Update Failed ! Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                documentReference.update("Phone", uphone).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(upd_profile.this, "Phone Number Update Failed ! Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                documentReference.update("EmailId", uemail).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(upd_profile.this, "Email Id Update Failed ! Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                user.updateEmail(uemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: ");
                        }
                    }
                });
            }
        });
    }


}
