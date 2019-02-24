package com.example.med_hack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView lv_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Medical Contacts");

        lv_contacts = (ListView) findViewById(R.id.lv_contacts);

        lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent i = new Intent(getApplicationContext(),ContactClicked.class);
                i.putExtra("key",arrKey.get(pos));
                i.putExtra("contactName",contactName.get(pos));
                i.putExtra("position",position.get(pos));
                i.putExtra("contactNum",contactNum.get(pos));
                i.putExtra("lat",lat.get(pos));
                i.putExtra("lng",lng.get(pos));
                startActivity(i);

            }
        });

        getContacts();

    }

    ArrayList<String> arrKey = new ArrayList<>();
    ArrayList<String> contactName = new ArrayList<>();
    ArrayList<String> position = new ArrayList<>();
    ArrayList<String> contactNum = new ArrayList<>();
    ArrayList<String> lat = new ArrayList<>();
    ArrayList<String> lng = new ArrayList<>();

    ProgressDialog pd;
    public void getContacts(){

        pd = new ProgressDialog(Contacts.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();

        firebaseDatabase.getInstance().getReference("medicalContacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the node
                for (DataSnapshot ps : dataSnapshot.getChildren()) {
                    arrKey.add(ps.getKey());
                    contactName.add(ps.child("contactName").getValue(String.class));
                    position.add(ps.child("position").getValue(String.class));
                    lat.add(ps.child("Latitude").getValue(String.class));
                    lng.add(ps.child("Longitude").getValue(String.class));
                    contactNum.add(ps.child("contactNumber").getValue(String.class));
                }

                ContactAdapter contactAdapter = new ContactAdapter(Contacts.this,contactName,position);
                lv_contacts.setAdapter(contactAdapter);

                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
