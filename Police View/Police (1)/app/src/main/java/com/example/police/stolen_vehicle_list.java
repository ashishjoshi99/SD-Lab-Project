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

public class stolen_vehicle_list extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView res_view;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stolen_vehicle_list);

        res_view = findViewById(R.id.stolenvehiclerec);

        db = FirebaseFirestore.getInstance();

        //Query
        Query query = db.collection("StolenVehicleRecords");

        FirestoreRecyclerOptions<vehicles> options = new FirestoreRecyclerOptions.Builder<vehicles>().setQuery(query, vehicles.class).build();

        adapter = new FirestoreRecyclerAdapter<vehicles, vehicle_viewholder>(options) {

            @NonNull
            @Override
            public vehicle_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_stolen_vehicles, parent, false);
                return new vehicle_viewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull vehicle_viewholder holder, int position, @NonNull vehicles model) {
                holder.stolen1.setText("Registration Number:" + model.getReg_no());
                holder.stolen2.setText("Chassis Number:" + model.getChas_no());
                holder.stolen3.setText("Engine Number:" + model.getEng_no());
                holder.stolen4.setText("Vehicle Type:" + model.getVeh_type());
                Picasso.get().load(model.getVeh_img()).into(holder.stolenimg);
            }
        };

        res_view.setHasFixedSize(true);
        res_view.setLayoutManager(new LinearLayoutManager(this));
        res_view.setAdapter(adapter);


    }

    // defining view holder
    private class vehicle_viewholder extends RecyclerView.ViewHolder {
        private TextView stolen1, stolen2, stolen3, stolen4;
        private ImageView stolenimg;

        public vehicle_viewholder(@NonNull View itemView) {

            super(itemView);
            stolen1 = itemView.findViewById(R.id.stolen_1);
            stolen2 = itemView.findViewById(R.id.stolen_2);
            stolen3 = itemView.findViewById(R.id.stolen_3);
            stolen4 = itemView.findViewById(R.id.stolen_4);
            stolenimg = itemView.findViewById(R.id.stolen_image);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
