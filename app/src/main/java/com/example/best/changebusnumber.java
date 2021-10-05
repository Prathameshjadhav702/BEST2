package com.example.best;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class changebusnumber extends AppCompatActivity {
    DatabaseReference reference;
    EditText newnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changebusnumber);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button reset = findViewById(R.id.RESETT);
        Button BACK = findViewById(R.id.BACK);
        newnum=findViewById(R.id.editTextPhone);
        TextView busnum = findViewById(R.id.textView24);
        String number = getIntent().getStringExtra("busn");
        busnum.setText(number);
        String PHNNO = getIntent().getStringExtra("nummber");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no= newnum.getEditableText().toString();
                if (no.isEmpty()) {
                    Toast.makeText(changebusnumber.this, "PLEASE ENTER NEW BUS NUMBER", Toast.LENGTH_LONG).show();
                }else {
                    reference = FirebaseDatabase.getInstance().getReference("DATA");
                    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                    DatabaseReference BUSNUMM = rootNode.getReference("DATA").child(PHNNO).child("NUMBER");
                    BUSNUMM.setValue(no);
                    busnum.setText(no);
                    Toast.makeText(changebusnumber.this, "BUS NUMBER CHANGED SUCCESSFUL", Toast.LENGTH_LONG).show();
                }
            }
        });
        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(changebusnumber.this, conductor.class);
                startActivity(intent);
                finish();
            }
        });


    }
}