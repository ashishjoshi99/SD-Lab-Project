package com.example.crim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register_screen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "TAG";
    EditText emailid, passwd, name, phone;

    Spinner spinner;
    TextView backtologin;
    Button regist;
    String[] code = {"Home District", "Belgaum", "Bagalkot","Hubli","Dharwad","Bangalore"};
    private FirebaseAuth mAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        regist = findViewById(R.id.register);
        backtologin = findViewById(R.id.backtologin);
        mAuth = FirebaseAuth.getInstance();
        spinner = findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);


        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        emailid = findViewById(R.id.email_reg);
        passwd = findViewById(R.id.pass_reg);
        phone = findViewById(R.id.phone_reg);
        name = findViewById(R.id.reg_name);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        ArrayAdapter<String> adapterspinner = new ArrayAdapter<>(this, R.layout.spinner, code);
        adapterspinner.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(adapterspinner);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(register_screen.this,"Work in progress",Toast.LENGTH_SHORT).show();
                final String email = emailid.getText().toString().trim();
                String password = passwd.getText().toString().trim();
                final String phoneNo = phone.getText().toString();
                final String uName = name.getText().toString();

                if (email.isEmpty()) {
                    emailid.setError("Email is Required");
                    return;
                } else if (password.isEmpty()) {
                    passwd.setError("Password is Required");
                    return;
                } else if (phone.length() == 0 || phone.length() != 10) {
                    phone.setError("Enter Valid Phone Number");
                    return;
                } else if (name.length() == 0) {
                    name.setError("Enter Valid Name");
                    return;
                } else if (password.length() < 6) {
                    passwd.setError("Password Length should be greater than 6");
                    return;
                }


                //Register for FireBase

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(register_screen.this, "User Created", Toast.LENGTH_SHORT).show();
                            userId = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Name", uName);
                            user.put("Phone", phoneNo);
                            user.put("EmailId", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User profile Created for " + userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure:" + e.toString());
                                }
                            });
                            startActivity(new Intent(register_screen.this, login_screen.class));
                            FirebaseAuth.getInstance().signOut();
                            finish();
                        } else {
                            Toast.makeText(register_screen.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(register_screen.this, login_screen.class);
                startActivity(log);
                finish();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
