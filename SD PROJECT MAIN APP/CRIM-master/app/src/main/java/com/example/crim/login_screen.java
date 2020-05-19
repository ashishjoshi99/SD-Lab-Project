package com.example.crim;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_screen extends AppCompatActivity {

    Button login;
    final int SEND_SMS_PERMISSION_CODE=1;
    EditText email_login, password_login;
    TextView backtoreg;
    ProgressBar pgrbar;
    FloatingActionButton fab1;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        login = findViewById(R.id.login);
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        backtoreg = findViewById(R.id.backtoreg);
        mAuth = FirebaseAuth.getInstance();
        pgrbar = findViewById(R.id.prgbar);
        fab1=findViewById(R.id.fab1);
        fab1.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)){
            fab1.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_CODE);
        }
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSend();
                Intent i= new Intent(login_screen.this,message.class);
                startActivity(i);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email_login.getText().toString().trim().isEmpty()) {
                    Toast.makeText(login_screen.this, "Please Enter a valid email id", Toast.LENGTH_SHORT).show();

                } else if (password_login.getText().toString().trim().isEmpty() || password_login.getText().toString().trim().length() < 6) {
                    Toast.makeText(login_screen.this, "Please Enter a valid password", Toast.LENGTH_SHORT).show();

                } else {
                    //FireBase Login HERE
                    String email = email_login.getText().toString().trim();
                    String password = password_login.getText().toString().trim();
                    pgrbar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(login_screen.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), homepage.class));
                                finish();
                            } else {
                                pgrbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(login_screen.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        backtoreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), register_screen.class));

                //Toast.makeText(login_screen.this, "work in progress", Toast.LENGTH_SHORT).show();
            }
        });

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


}
