package com.example.med_hack;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Meditate extends AppCompatActivity {

    Button btnr1,btnr2,btnr3;

    int re1 = 0, re2 = 0, re3 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditate);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Meditate & Listen");

        btnr1 = (Button)findViewById(R.id.btnr1);
        btnr2 = (Button)findViewById(R.id.btnr2);
        btnr3 = (Button)findViewById(R.id.btnr3);

        final MediaPlayer r1 = MediaPlayer.create(Meditate.this, R.raw.relax1);
        final MediaPlayer r2 = MediaPlayer.create(Meditate.this, R.raw.relax2);
        final MediaPlayer r3 = MediaPlayer.create(Meditate.this, R.raw.relax3);


        btnr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(re1 == 0) {
                    btnr1.setText("STOP");
                    r1.start();
                    btnr2.setEnabled(false);
                    btnr3.setEnabled(false);
                    re1 = 1;
                }
                else{
                    re1 = 0;
                    btnr1.setText("PLAY 1");
                    btnr2.setEnabled(true);
                    btnr3.setEnabled(true);
                    r1.stop();
                }
            }
        });

        btnr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(re2 == 0) {
                    btnr2.setText("STOP");
                    r2.start();
                    btnr1.setEnabled(false);
                    btnr3.setEnabled(false);
                    re2 = 1;

                }
                else{
                    re2 = 0;
                    btnr2.setText("PLAY 2");
                    btnr1.setEnabled(true);
                    btnr3.setEnabled(true);
                    r2.stop();
                    }
            }
        });
        btnr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(re3 == 0) {
                    r3.start();
                    btnr3.setText("STOP");
                    btnr1.setEnabled(false);
                    btnr2.setEnabled(false);
                    re3 = 1;

                }
                else{
                    re3 = 0;
                    btnr3.setText("PLAY 3");
                    btnr1.setEnabled(true);
                    btnr2.setEnabled(true);
                    r3.stop();
                }

            }
        });

    }
}
