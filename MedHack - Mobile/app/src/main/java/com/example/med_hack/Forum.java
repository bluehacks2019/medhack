package com.example.med_hack;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Forum extends AppCompatActivity {

    TextView txtann,txtmoodtype,txtvote;
    ListView lv_forum;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String mykey="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        getSupportActionBar().setTitle("Forum");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        txtann = (TextView)findViewById(R.id.txtann);
        txtmoodtype = (TextView)findViewById(R.id.txtmoodtype);
        txtvote = (TextView)findViewById(R.id.txtvote);
        lv_forum = (ListView) findViewById(R.id.lv_forum);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        mykey = sharedPreferences.getString("mykey","");

        lv_forum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Forum.this);
                alert.setCancelable(false);
               alert.setPositiveButton("+1 Vote", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       vote(arrKey.get(position));
                   }
               });
               alert.setNegativeButton("", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       unvote(arrKey.get(position));
                   }
               });
               alert.setNeutralButton("Comment", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                   }
               });
                alert.show();
            }
        });

        getSolutions();

    }


    public void vote(String comkey){
        String comKey = firebaseDatabase.getInstance().getReference("Announcement").push().getKey();
        String datetime = new SimpleDateFormat("EEE, hh:mm aa | MMM.dd.yyyy", Locale.getDefault()).format(new Date());
        String getvote = "";

        final Map<String,String> setVal = new HashMap<>();
        setVal.put("vote",mykey);

        firebaseDatabase.getInstance().getReference("vote").child(comkey).setValue(setVal);

    }

    public void unvote(String comkey){
        String comKey = firebaseDatabase.getInstance().getReference("Announcement").push().getKey();
        String datetime = new SimpleDateFormat("EEE, hh:mm aa | MMM.dd.yyyy", Locale.getDefault()).format(new Date());
        String getvote = "";

        final Map<String,String> setVal = new HashMap<>();
        setVal.put("vote","");

        firebaseDatabase.getInstance().getReference("vote").child(comkey).setValue(setVal);

    }




    ArrayList<String> arrKey = new ArrayList<>();
    ArrayList<String> ann = new ArrayList<>();
    ArrayList<String> moodtype = new ArrayList<>();
    ArrayList<String> vote = new ArrayList<>();

    ProgressDialog pd;
    public void getSolutions(){

        pd = new ProgressDialog(Forum.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();

        firebaseDatabase.getInstance().getReference("Announcement").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the node

                arrKey.clear();
                ann.clear();
                moodtype.clear();
                vote.clear();


                for (DataSnapshot ps : dataSnapshot.getChildren()) {
                    arrKey.add(ps.getKey());
                    ann.add(ps.child("announcement").getValue(String.class));
                    moodtype.add(ps.child("mood_type").getValue(String.class));
                    vote.add(ps.child("vote").getValue(String.class));
                }



                ForumAdapter forum = new ForumAdapter(Forum.this,ann,moodtype,vote);
                lv_forum.setAdapter(forum);

                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
