package com.example.med_hack;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StatusComment extends AppCompatActivity {

    ListView lv_comments;
    ImageView imgsend;
    EditText message;

    String keyComment = "",mykey= "";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_comment);

        getSupportActionBar().setTitle("Status Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_comments = (ListView) findViewById(R.id.lv_comments);
        imgsend = (ImageView)findViewById(R.id.send);
        message = (EditText) findViewById(R.id.message);

        keyComment = getIntent().getStringExtra("key");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(StatusComment.this);
        editor = sharedPreferences.edit();

        mykey = sharedPreferences.getString("mykey","");

        imgsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!message.getText().toString().equals("")){
                    sendComments(message.getText().toString());
                }
            }
        });

        getComments();

    }

    public void sendComments(String com){
        String comKey = firebaseDatabase.getInstance().getReference("userThoughtsCom").push().getKey();

        String datetime = new SimpleDateFormat("EEE, hh:mm aa | MMM.dd.yyyy", Locale.getDefault()).format(new Date());

        final Map<String,String> setVal = new HashMap<>();
        setVal.put("comment",com);
        setVal.put("datetime",datetime);
        setVal.put("userKey",mykey);

        firebaseDatabase.getInstance().getReference("userThoughtsCom").child(keyComment).child(comKey).setValue(setVal);

        message.getText().clear();


    }

    ArrayList<String> arrKey = new ArrayList<>();
    ArrayList<String> arrName= new ArrayList<>();
    ArrayList<String> arrStatus= new ArrayList<>();
    ArrayList<String> arrDatetime= new ArrayList<>();
    ArrayList<String> arrMood= new ArrayList<>();

    ProgressDialog pd;
    public void getComments(){
            pd = new ProgressDialog(StatusComment.this);
            pd.setCancelable(false);
            pd.setMessage("Loading...");
            pd.show();

            firebaseDatabase.getInstance().getReference("userThoughtsCom").child(keyComment).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //iterating through all the node

                    arrKey.clear();
                    arrName.clear();
                    arrStatus.clear();
                    arrDatetime.clear();
                    arrMood.clear();

                    if(dataSnapshot.exists()){
                        for (final DataSnapshot ps : dataSnapshot.getChildren()) {
                            arrKey.add(ps.getKey());

                            firebaseDatabase.getInstance().getReference("Users").child(ps.child("userKey").getValue(String.class)).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot us) {
                                    //iterating through all the node
                                    arrName.add(us.child("fname").getValue(String.class) + " " + us.child("lname").getValue(String.class));
                                    arrMood.add("mood");
//                                      us.child("mood").getValue(String.class)

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            arrDatetime.add(ps.child("datetime").getValue(String.class));
                            arrStatus.add(ps.child("comment").getValue(String.class));
                        }

                    }

                    Collections.reverse(arrName);
                    Collections.reverse(arrStatus);
                    Collections.reverse(arrDatetime);
                    Collections.reverse(arrMood);

                    StatusComAdapter statusComAdapter = new StatusComAdapter(StatusComment.this,arrName,arrStatus,arrDatetime,arrMood);
                    lv_comments.setAdapter(statusComAdapter);

                    pd.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
}
