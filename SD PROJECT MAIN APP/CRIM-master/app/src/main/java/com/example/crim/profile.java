package com.example.crim;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

//import android.widget.EditText;

public class profile extends AppCompatActivity {


    EditText name;
    EditText email;
    EditText phone;
    Button logout, reset_passwd, updatebtn;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userId;
    StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        phone = findViewById(R.id.profile_phone);
        email = findViewById(R.id.profile_email);
        name = findViewById(R.id.profile_name);
        reset_passwd = findViewById(R.id.pass_reset);
        updatebtn = findViewById(R.id.profile_update);
        logout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        final DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                phone.setText(documentSnapshot.getString("Phone"));
                email.setText(documentSnapshot.getString("EmailId"));
                name.setText(documentSnapshot.getString("Name"));
            }
        });


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), upd_profile.class));
            }
        });

        reset_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordreset = new AlertDialog.Builder(v.getContext());
                passwordreset.setTitle("Reset Password");
                passwordreset.setMessage("Enter email to receive the reset link");
                passwordreset.setView(resetMail);

                passwordreset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString().trim();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(profile.this, "Reset Link sent to your email!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(profile.this, "Error: Link sending failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                passwordreset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), profile.class));
                        finish();
                    }
                });

                passwordreset.create().show();
            }
        });

    }


}
