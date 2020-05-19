package com.example.crim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class settings extends AppCompatActivity {
    TextView faq, feed, about, aboutd;
    Button logout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        faq = findViewById(R.id.faq);
        logout = findViewById(R.id.logout);

        feed = findViewById(R.id.feedback);
        about = findViewById(R.id.about);
        aboutd = findViewById(R.id.developers);

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(settings.this, faq.class));
            }
        });

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(settings.this, feedback.class));
            }
        });

        aboutd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(settings.this, aboutthed.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(settings.this, "This app is for day to day user, so that they can register their complaints", Toast.LENGTH_LONG).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getApplicationContext(), login_screen.class));
                finishAffinity();
            }
        });
    }
}
