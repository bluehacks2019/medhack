package com.example.med_hack;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Trace;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    EditText status;
    LinearLayout listatus;
    Button btnshare;
    TextView txtload;
    ListView lv_status;
    ImageView logout,imgTrack,imgFacts,imgMeditate,imgContacts,forum;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String mykey = "",fname = "",lname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        status = (EditText)findViewById(R.id.status);
        listatus = (LinearLayout) findViewById(R.id.listatus);
        btnshare = (Button) findViewById(R.id.btnshare);
        txtload = (TextView) findViewById(R.id.txtload);
        lv_status = (ListView) findViewById(R.id.lv_status);
        logout = (ImageView) findViewById(R.id.logout);

        imgContacts = (ImageView) findViewById(R.id.imgContacts);
        imgFacts = (ImageView) findViewById(R.id.imgFacts);
        imgMeditate = (ImageView) findViewById(R.id.imgMeditate);
        imgTrack = (ImageView) findViewById(R.id.imgTrack);
        forum = (ImageView) findViewById(R.id.forum);

        txtload.setVisibility(View.GONE);
        btnshare.setVisibility(View.GONE);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        mykey = sharedPreferences.getString("mykey","");
        fname = sharedPreferences.getString("fname","");
        lname = sharedPreferences.getString("lname","");

        status.setHint("Hello "+fname+"! ,\nwhat made you smile today?");

        imgTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Graph.class);
                startActivity(i);
            }
        });

        imgMeditate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Meditate.class);
                startActivity(i);
            }
        });

        imgFacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Facts.class);
                startActivity(i);
            }
        });

        imgContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Contacts.class);
                startActivity(i);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Forum.class);
                startActivity(i);
            }
        });

        status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!status.getText().toString().equals("")){
                    btnshare.setVisibility(View.VISIBLE);
                }else{
                    btnshare.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sStatus = status.getText().toString();

                if(!sStatus.equals("")){
                    share();
                }

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder lt = new AlertDialog.Builder(MainActivity.this);
                lt.setCancelable(false);
                lt.setTitle("Logout?");
                lt.setMessage("Do you want to logout?");
                lt.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        editor.putString("mykey","");
                        editor.commit();

                        Intent i = new Intent(getApplicationContext(),Login.class);
                        startActivity(i);
                        finish();

                    }
                });
                lt.setNegativeButton("Cancel",null);
                lt.show();
            }
        });

        displayFeed();

        String datetime = new SimpleDateFormat("MM.dd.yyyy", Locale.getDefault()).format(new Date());
        String getSDT = sharedPreferences.getString("datetimeNow","");
        if(getSDT.equals(datetime)){

        }else{


            editor.putString("datetimeNow",datetime);
            editor.commit();
        }

        lv_status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),StatusComment.class);
                intent.putExtra("key",arrKey.get(position));
                startActivity(intent);
            }
        });

        showRadioButtonDialog();


        NotifyMe();

    }

    private void showRadioButtonDialog() {

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

        // custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.moodlist);
        List<String> stringList = new ArrayList<>();  // here is list
        stringList.add(" GREAT");
        stringList.add(" GOOD");
        stringList.add(" MEH");
        stringList.add(" BAD");
        stringList.add(" AWFUL");

        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.rgrp);

        for(int i=0;i<stringList.size();i++){
            RadioButton rb = new RadioButton(MainActivity.this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(stringList.get(i));
            if(i == 0) {
                rb.setButtonDrawable(R.drawable.m1);
            }
            else if(i == 1){
                rb.setButtonDrawable(R.drawable.m2);
            }
            else if(i == 2){
                rb.setButtonDrawable(R.drawable.m3);
            }
            else if(i == 3){
                rb.setButtonDrawable(R.drawable.m4);
            }
            else if(i == 4){
                rb.setButtonDrawable(R.drawable.m5);
            }
            rg.addView(rb);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setCancelable(false);

                for (int x = 0; x < childCount; x++) {

                    RadioButton btn = (RadioButton) group.getChildAt(x);

                    if (btn.getId() == checkedId) {
                        if(checkedId == 1){
                            m1++;
                            alert.setTitle("");
                            alert.setMessage("Go to the mall, treat yourself!");
                            alert.setPositiveButton("That's good!",null);
                            alert.setNegativeButton("Cancel",null);

                        }
                        else if(checkedId == 2){
                            m2++;
                            alert.setTitle("Update yourself!");
                            alert.setMessage("Share your thoughts now!");
                            alert.setPositiveButton("Share",null);
                            alert.setNegativeButton("Cancel",null);
                        }
                        else if(checkedId == 3){
                            m3++;
                            alert.setMessage("Feeling meh?");
                            alert.setPositiveButton("Yup",null);
                            alert.setNegativeButton("Cancel",null);
                        }
                        else if(checkedId == 4){
                            m4++;
                            alert.setTitle("Friends");
                            alert.setMessage("Talk to your family, " +fname+"!");
                            alert.setPositiveButton("Ok",null);
                            alert.setNegativeButton("Cancel",null);
                        }
                        else if(checkedId == 5){
                            m5++;
                            alert.setTitle("");
                            alert.setMessage("I can help you "+fname+"!");
                            alert.setPositiveButton("Share",null);
                            alert.setNegativeButton("Cancel",null);
                        }
                    }
                }

                alert.show();

                setMood();
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();

    }

    String sStatus = "";
    public void share(){
        String key = firebaseDatabase.getInstance().getReference("userStatus").push().getKey();

        String datetime = new SimpleDateFormat("EEE, hh:mm aa | MMM.dd.yyyy", Locale.getDefault()).format(new Date());

        final Map<String,String> setVal = new HashMap<>();
        setVal.put("status",sStatus);
        setVal.put("datetime",datetime);
        setVal.put("userKey",mykey);

        firebaseDatabase.getInstance().getReference("userThoughts").child(key).setValue(setVal);
        status.getText().clear();

    }

    ArrayList<String> arrKey = new ArrayList<>();
    ArrayList<String> arrName= new ArrayList<>();
    ArrayList<String> arrStatus= new ArrayList<>();
    ArrayList<String> arrDatetime= new ArrayList<>();
    ArrayList<String> arrMood= new ArrayList<>();


    ProgressDialog pd;
    public void displayFeed(){
        pd = new ProgressDialog(MainActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();

        firebaseDatabase.getInstance().getReference("userThoughts").addValueEventListener(new ValueEventListener() {
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
                        arrStatus.add(ps.child("status").getValue(String.class));
                    }
                    txtload.setVisibility(View.GONE);

                }else{
                    txtload.setVisibility(View.VISIBLE);
                    txtload.setText("No posts available this time");

                }

                Collections.reverse(arrName);
                Collections.reverse(arrStatus);
                Collections.reverse(arrDatetime);
                Collections.reverse(arrMood);

                MainAdapter mainAdapter = new MainAdapter(MainActivity.this,arrName,arrStatus,arrDatetime,arrMood);
                lv_status.setAdapter(mainAdapter);

                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    int m1 = 0,m2 = 0,m3 = 0,m4 = 0,m5 = 0;
    public void setMood(){


        final Map<String,String> setVal = new HashMap<>();
        setVal.put("m1", String.valueOf(m1));
        setVal.put("m2", String.valueOf(m2));
        setVal.put("m3", String.valueOf(m3));
        setVal.put("m4", String.valueOf(m4));
        setVal.put("m5", String.valueOf(m5));

        firebaseDatabase.getInstance().getReference("mood").child(mykey+"-"+fname+" "+lname).setValue(setVal);

        m1 = 0;
        m2 = 0;
        m3 = 0;
        m4 = 0;
        m5 = 0;


    }


    Intent notificationIntent;
    public static class NotificationID {
        private final static AtomicInteger c = new AtomicInteger(0);
        public static int getID() {
            return c.incrementAndGet();
        }
    }

    NotificationManager manager;

    ArrayList<String> quote = new ArrayList<>();

    public void NotifyMe() {

        Random r = new Random();
        int randomNumber = r.nextInt(4);

        quote.add("It does not matter how slowly you go as long as you do not stop.");
        quote.add("Life is what we make it, always has been, always will be.");
        quote.add("The only way to do great work is to love what you do.");
        quote.add("If you can dream it, you can achieve it.");

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        mBuilder.setSmallIcon(R.drawable.logo1);

        mBuilder.setContentTitle("Inspirational Quote of the Day");

//            MediaPlayer eve = MediaPlayer.create(MainActivity.this, R.raw.announcement);
//            eve.start();
        mBuilder.setContentText(""+quote.get(randomNumber));


        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, NotificationID.getID(), notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);


        manager.notify(NotificationID.getID(), mBuilder.build());

    }
}
