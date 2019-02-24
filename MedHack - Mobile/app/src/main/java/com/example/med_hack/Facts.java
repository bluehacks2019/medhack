package com.example.med_hack;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Facts extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView lv_facts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mind Boosting Facts");

        lv_facts = (ListView)findViewById(R.id.lv_facts);

        getFacts();
    }

    ArrayList<String> facts = new ArrayList<>();
    ProgressDialog pd;
    public void getFacts(){

        pd = new ProgressDialog(Facts.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();

        firebaseDatabase.getInstance().getReference("Facts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ps : dataSnapshot.getChildren()) {
                    facts.add(ps.child("Description").getValue(String.class));

                }

                FactsAdapt factsAdapt = new FactsAdapt(Facts.this,facts);
                lv_facts.setAdapter(factsAdapt);

                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
