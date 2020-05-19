package com.example.police;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class incident_reports extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private RecyclerView rec_view;
    private FirestoreRecyclerAdapter adapter; // for using recycler view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_reports);

        fStore = FirebaseFirestore.getInstance();
        rec_view = findViewById(R.id.incident_rview);
        Query query = fStore.collection("ReportIncidentRecords"); // fetching data if incident report


        // using recycler view on fetched data for display
        FirestoreRecyclerOptions<incidents> options = new FirestoreRecyclerOptions.Builder<incidents>().setQuery(query, incidents.class).build();
        adapter = new FirestoreRecyclerAdapter<incidents, incident_viewholder>(options) {
            @NonNull
            @Override
            public incident_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_incidents, parent, false);
                return new incident_viewholder(view);
            }

            // putting data into category
            @Override
            protected void onBindViewHolder(@NonNull incident_viewholder holder, int position, @NonNull incidents model) {
                holder.type.setText("Incident Type:" + model.getIncidentType());
                holder.location.setText("Incident Location:" + model.getLocationofIncident());
                holder.city.setText("Incident City:" + model.getIncidentCity());
                holder.details.setText("Details:" + model.getIncidentDetails());
                Picasso.get().load(model.getImageUrl()).into(holder.img);
            }
        };


        // setting the layout parameters for display
        rec_view.setHasFixedSize(true);
        rec_view.setLayoutManager(new LinearLayoutManager(this));
        rec_view.setAdapter(adapter);
    }

    private class incident_viewholder extends RecyclerView.ViewHolder {
        private TextView city, details, type, location, url;
        private ImageView img;

        public incident_viewholder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.inc_1);
            location = itemView.findViewById(R.id.inc_2);
            city = itemView.findViewById(R.id.inc_3);
            details = itemView.findViewById(R.id.inc_4);
            img = itemView.findViewById(R.id.inc_image);
        }
    }

    // when activity starts
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

     // when activity ends
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
