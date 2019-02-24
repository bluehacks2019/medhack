package com.example.med_hack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText eEmail,ePass;
    Button btnlogin;
    TextView txtsignup;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        eEmail = (EditText)findViewById(R.id.email);
        ePass = (EditText)findViewById(R.id.pass);
        btnlogin = (Button) findViewById(R.id.btndone);
        txtsignup = (TextView) findViewById(R.id.txtsignup);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eEmail.getText().toString().equals("") || ePass.getText().toString().equals("")){

                }else{
                    Login();
                }

            }
        });

    }

    ProgressDialog pd;
    public void Login(){

        pd = new ProgressDialog(Login.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        //attaching value event listener

        firebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the nodes

                int user = 0;
                for (final DataSnapshot ps : dataSnapshot.getChildren()) {

                    if(ps.child("email").getValue(String.class).equals(eEmail.getText().toString())){
                        if(ps.child("pass").getValue(String.class).equals(ePass.getText().toString())){

                            editor.putString("mykey",ps.getKey());
                            editor.putString("fname",ps.child("fname").getValue(String.class));
                            editor.putString("lname",ps.child("lname").getValue(String.class));
                            editor.putString("email",ps.child("email").getValue(String.class));
                            editor.commit();

                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();

                            user = 1;

                        }else{
                            Toast.makeText(Login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                if(user == 0){
                    Toast.makeText(Login.this, "User Not Found!", Toast.LENGTH_SHORT).show();
                }


                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
