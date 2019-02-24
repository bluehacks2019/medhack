package com.example.med_hack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText fname,lname,contactnum,email,pass;
    Button btndone;
    TextView txtgologin;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        fname = (EditText)findViewById(R.id.fname);
        lname = (EditText)findViewById(R.id.lname);
        contactnum = (EditText)findViewById(R.id.contactnum);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        btndone = (Button) findViewById(R.id.btndone);
        txtgologin = (TextView) findViewById(R.id.txtgologin);

        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s0 = fname.getText().toString();
                String s1 = lname.getText().toString();
                String s2 = contactnum.getText().toString();
                String s3 = email.getText().toString();
                String s4 = pass.getText().toString();

                if(s0.equals("") || s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("")){
                    Toast.makeText(Register.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }else{
                    createNewUser(s0,s1,s2,s3,s4);
                }


            }
        });

        txtgologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void createNewUser(String sfname, String slname,String scontact,String semail, String spass){
        String key = firebaseDatabase.getInstance().getReference("Users").push().getKey();

        final Map<String,String> setVal = new HashMap<>();
        setVal.put("fname",sfname);
        setVal.put("lname",slname);
        setVal.put("contact",scontact);
        setVal.put("email",semail);
        setVal.put("pass",spass);

        firebaseDatabase.getInstance().getReference("Users").child(key).setValue(setVal);

        fname.getText().clear();
        lname.getText().clear();
        contactnum.getText().clear();
        email.getText().clear();
        pass.getText().clear();

        Toast.makeText(this, "Welcome "+sfname+"!", Toast.LENGTH_SHORT).show();

    }
}
