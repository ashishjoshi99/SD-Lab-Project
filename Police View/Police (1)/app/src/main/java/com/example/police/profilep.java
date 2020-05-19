package com.example.police;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class profilep extends AppCompatActivity {


    EditText name;
    EditText email;
    EditText phone;
    Button reset_passwd, updatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilep);
        phone = findViewById(R.id.profile_phone);
        email = findViewById(R.id.profile_email);
        name = findViewById(R.id.profile_name);
        reset_passwd = findViewById(R.id.pass_reset);
        updatebtn = findViewById(R.id.profile_update);

        //rest is on u frand

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(profilep.this, "Ashis the great will do this", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
