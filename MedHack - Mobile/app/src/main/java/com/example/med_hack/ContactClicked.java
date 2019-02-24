package com.example.med_hack;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactClicked extends AppCompatActivity implements OnMapReadyCallback{

    double lat=0,lng=0;


    ProgressDialog pd;
    GoogleMap mMap;

    Button btncall;
    TextView txtpos,txtname,txtcontact;

    String name="",position="",contactNum="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_clicked);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Medical Contacts");

        btncall = (Button)findViewById(R.id.btncall);
        txtpos = (TextView) findViewById(R.id.txtpos);
        txtname = (TextView) findViewById(R.id.txtname);
        txtcontact = (TextView) findViewById(R.id.txtcontact);


        name = getIntent().getStringExtra("contactName").toString();
        position = getIntent().getStringExtra("position").toString();
        contactNum = getIntent().getStringExtra("contactNum").toString();
        lat = Double.parseDouble(getIntent().getStringExtra("lat").toString());
        lng = Double.parseDouble(getIntent().getStringExtra("lng").toString());


        txtpos.setText(position);
        txtcontact.setText(contactNum);
        txtname.setText(name);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                new Handler().postDelayed(this, 500);
            }
        }, 500);

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactNum));
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(ContactClicked.this);


    }

    LatLng loc;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loc = new LatLng(lat, lng);

        googleMap.addMarker(new MarkerOptions().position(loc)
                .title(txtname.getText().toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).showInfoWindow();


            CameraPosition cameraPosition = new CameraPosition.Builder().
                    target(loc).
                    tilt(60).
                    zoom(15).
                    bearing(0).
                    build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }

}

