package com.example.best;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admin2 extends AppCompatActivity {
    EditText name,busnum,phnum,mhnum;
    Button submit;
    ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);
        name=findViewById(R.id.name);
        busnum=findViewById(R.id.busnum);
        phnum=findViewById(R.id.phn);
        mhnum=findViewById(R.id.mhnum);
        submit=findViewById(R.id.button8);
        logout=findViewById(R.id.imageButton2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin2.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NAME= name.getEditableText().toString();
                String BUSNUM= busnum.getEditableText().toString();
                String PHNUM= phnum.getEditableText().toString();
                String MHNUM=mhnum.getEditableText().toString();
                // String passcodee= pass.getEditableText().toString();
                if (NAME.isEmpty()){
                    Toast.makeText(admin2.this, "PLEASE ENTER CONDUCTOR NAME", Toast.LENGTH_LONG).show();
                }
                else if(BUSNUM.isEmpty()){
                    Toast.makeText(admin2.this, "PLEASE ENTER BUS NUMBER", Toast.LENGTH_LONG).show();
                }
                else if(PHNUM.isEmpty()){
                    Toast.makeText(admin2.this, "PLEASE ENTER CONDUCTOR PHONE NUMBER", Toast.LENGTH_LONG).show();
                }
                else if (PHNUM.length()!=10){
                    Toast.makeText(admin2.this, "PLEASE ENTER VALID PHONE NUMBER", Toast.LENGTH_LONG).show();
                }
                else if(MHNUM.isEmpty()){
                    Toast.makeText(admin2.this, "PLEASE ENTER VEHICLE NUMBER SERIES(MH NUMBER)", Toast.LENGTH_LONG).show();
                }
                else{
                    FirebaseDatabase rootNode= FirebaseDatabase.getInstance();
                    DatabaseReference PHONE =rootNode.getReference("DATA").child("91"+PHNUM).child("PHONE");
                    DatabaseReference CONDUCTOR =rootNode.getReference("DATA").child("91"+PHNUM).child("CONDUCTOR");
                    DatabaseReference BUSNUMM =rootNode.getReference("DATA").child("91"+PHNUM).child("NUMBER");
                    DatabaseReference MHNUMM =rootNode.getReference("DATA").child("91"+PHNUM).child("MH-NUMBER");
                    DatabaseReference latt =rootNode.getReference("DATA").child("91"+PHNUM).child("LATITUDE");
                    DatabaseReference longi =rootNode.getReference("DATA").child("91"+PHNUM).child("LONGITUDE");
                    DatabaseReference density =rootNode.getReference("DATA").child("91"+PHNUM).child("STATUS");
                    PHONE.setValue("91"+PHNUM);
                    CONDUCTOR.setValue(NAME);
                    BUSNUMM.setValue(BUSNUM);
                    MHNUMM.setValue(MHNUM);
                    latt.setValue("0");
                    longi.setValue("0");
                    density.setValue("VACANT");
                    Toast.makeText(admin2.this, "DATA ENTERED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}