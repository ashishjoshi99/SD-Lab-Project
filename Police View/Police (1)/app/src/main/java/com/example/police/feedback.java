package com.example.police;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class feedback extends AppCompatActivity {
    Button submit1;
    EditText feedback_title, feedback_msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        submit1 = findViewById(R.id.f_submit);
        feedback_msg = findViewById(R.id.f_messages);
        feedback_title = findViewById(R.id.f_title);

        String feed_title = feedback_title.getText().toString().trim();
        String feed_msg = feedback_msg.getText().toString().trim();

        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (feedback_title.getText().toString().isEmpty()) {
                    Toast.makeText(feedback.this, "Enter Title!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (feedback_msg.getText().toString().isEmpty()) {
                    Toast.makeText(feedback.this, "A feedback message is appreciated!", Toast.LENGTH_SHORT).show();
                    return;

                } else {

                    Toast.makeText(feedback.this, "Ashish the great will do this", Toast.LENGTH_SHORT).show();
                    /*

                    String feed_title = feedback_title.getText().toString().trim();
                    String feed_msg = feedback_msg.getText().toString().trim();
                    String emailid = mAuth.getCurrentUser().getEmail();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference documentReference = db.collection("Feedbacks").document(emailid);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Feedback Title", feed_title);
                    user.put("Feedback Message", feed_msg);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(feedback.this, "Thank you for the valuable Feedback", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), homepage.class));
                            finishAffinity();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(feedback.this, "Failed.Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    */
                }
            }
        });
    }
}
