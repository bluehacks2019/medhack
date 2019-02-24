package com.example.med_hack;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class Graph extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    PieChart pieChart;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String mykey = "",fname = "",lname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mood Percentage");

        pieChart = (PieChart) findViewById(R.id.pc);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        mykey = sharedPreferences.getString("mykey","");
        fname = sharedPreferences.getString("fname","");
        lname = sharedPreferences.getString("lname","");

        setPieChart();
    }

    int m1 = 0,m2 = 0,m3 = 0,m4 = 0,m5 = 0;

    public void setPieChart() {

        firebaseDatabase.getInstance().getReference("mood").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes

                for (final DataSnapshot ps : dataSnapshot.getChildren()) {
                    String k = mykey+"-"+fname+" "+lname;
                    if(k.equals(ps.getKey())){
                        m1 = Integer.parseInt(ps.child("m1").getValue(String.class));
                        m2 = Integer.parseInt(ps.child("m2").getValue(String.class));
                        m3 = Integer.parseInt(ps.child("m3").getValue(String.class));
                        m4 = Integer.parseInt(ps.child("m4").getValue(String.class));
                        m5 = Integer.parseInt(ps.child("m5").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // creating data values
        pieChart.setUsePercentValues(true);

        ArrayList<Entry> yvalues = new ArrayList<Entry>();


        yvalues.add(new Entry(8f, 0));
        yvalues.add(new Entry(12f, 1));
        yvalues.add(new Entry(18f, 2));
        yvalues.add(new Entry(5f, 3));
        yvalues.add(new Entry(3f, 4));

        PieDataSet dataSet = new PieDataSet(yvalues, "  <= Mood Lists");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Great");
        xVals.add("Good");
        xVals.add("Meh");
        xVals.add("Bad");
        xVals.add("Awful");

        PieData data = new PieData(xVals, dataSet);
        // In Percentage term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
        pieChart.setDescription("Mood Monitoring Chart");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        pieChart.animateXY(1400, 1400);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

}
