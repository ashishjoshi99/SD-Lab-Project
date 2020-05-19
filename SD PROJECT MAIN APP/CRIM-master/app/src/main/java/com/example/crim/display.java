package com.example.crim;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class display extends AppCompatActivity {

    RecyclerView r_view;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        db = FirebaseFirestore.getInstance();
        r_view = findViewById(R.id.res_view);
        //Query
        Query query = db.collection("missinggpersonrec");

        //RecyclerOptions
        FirestoreRecyclerOptions<results> options = new FirestoreRecyclerOptions.Builder<results>()
                .setQuery(query, results.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<results, result_viewholder>(options) {
            @NonNull
            @Override
            public result_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_fb,parent,false);
                return new result_viewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull result_viewholder holder, int position, @NonNull results model) {
                    holder.name.setText("Name:"+model.getMname());
                    holder.age.setText("Age:"+model.getMage());
                    holder.birthmark.setText("BirthMark:"+model.getMbmark());
                    holder.miss_date.setText("Missing Date:"+model.getMdate());
                    holder.hair_clr.setText("Hair Color:"+model.getMhairclr());
                    holder.height.setText("Height:"+model.getMheight());
                    holder.sex.setText("Gender:"+model.getMsex());
                    Picasso.get().load(model.getMurl()).into(holder.img);
            }
        };

         r_view.setHasFixedSize(true);
         r_view.setLayoutManager(new LinearLayoutManager(this));
         r_view.setAdapter(adapter);

    }

    private class result_viewholder extends RecyclerView.ViewHolder {

        private TextView name,age,birthmark,miss_date,hair_clr,height,sex;
        private ImageView img;

        public result_viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.list_name);
            age = itemView.findViewById(R.id.list_age);
            birthmark = itemView.findViewById(R.id.list_bm);
            miss_date = itemView.findViewById(R.id.list_date);
            hair_clr = itemView.findViewById(R.id.list_hairclr);
            height = itemView.findViewById(R.id.list_height);
            sex = itemView.findViewById(R.id.list_sex);
            img = itemView.findViewById(R.id.rec_image);

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
