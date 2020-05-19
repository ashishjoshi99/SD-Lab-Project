package com.example.police;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class settngsp extends AppCompatActivity {

    TextView faq, about, aboutd;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settngsp);


        logout = findViewById(R.id.logout);
        about = findViewById(R.id.about);
        aboutd = findViewById(R.id.developers);





        aboutd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(settngsp.this, aboutthed.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(settngsp.this, "This app is for the Police, it shows all the reported crimes", Toast.LENGTH_SHORT).show();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getApplicationContext(), login_screen.class));
                finishAffinity();*/
                Toast.makeText(settngsp.this, "Logging Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),login_screenp.class));
                finish();
            }
        });
    }
}
