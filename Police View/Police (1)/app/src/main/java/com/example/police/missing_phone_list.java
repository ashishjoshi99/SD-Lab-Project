package com.example.police;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class missing_phone_list extends AppCompatActivity {

    private RecyclerView rec_view;
    private FirebaseFirestore fStore;
    private FirestoreRecyclerAdapter adapter;  // defining recycler view for firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_phone_list);

        rec_view = findViewById(R.id.missingphoneview);
        fStore = FirebaseFirestore.getInstance();

        //Query
        Query query = fStore.collection("ReportPhoneRecords");

        FirestoreRecyclerOptions<phones> options = new FirestoreRecyclerOptions.Builder<phones>().setQuery(query, phones.class).build();

        adapter = new FirestoreRecyclerAdapter<phones, phone_viewholder>(options) {
            @NonNull
            @Override
            public phone_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_stolen_phone, parent, false);
                return new phone_viewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull phone_viewholder holder, int position, @NonNull phones model) {
                holder.brand.setText("Phone Brand:" + model.getBrand());
                holder.model.setText("Phone Model:" + model.getModel());
                holder.color.setText("Phone Color:" + model.getColor());
                holder.imei.setText("IMEI Number:" + model.getIMEI());
                holder.date.setText("Stolen Since:" + model.getDate());
            }
        };

        rec_view.setHasFixedSize(true);
        rec_view.setLayoutManager(new LinearLayoutManager(this));
        rec_view.setAdapter(adapter);


    }

    private class phone_viewholder extends RecyclerView.ViewHolder {

        private TextView brand, date, color, model, imei;

        public phone_viewholder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.phone_1);
            model = itemView.findViewById(R.id.phone_2);
            color = itemView.findViewById(R.id.phone_3);
            imei = itemView.findViewById(R.id.phone_4);
            date = itemView.findViewById(R.id.phone_5);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
