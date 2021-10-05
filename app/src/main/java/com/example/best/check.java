package com.example.best;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class check extends AppCompatActivity {
    Button SUBMIT;
    EditText phnnum;
    FirebaseDatabase rootnode;
   // Handler mHandler;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        phnnum= findViewById(R.id.DATAA);
        SUBMIT=findViewById(R.id.button3);
        ImageButton admin=findViewById(R.id.imageButton);
         SUBMIT.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String phn=phnnum.getEditableText().toString();
                 if (phn.isEmpty()){
                     Toast.makeText(check.this, "PLEASE ENTER YOUR PHONE NUMBER", Toast.LENGTH_LONG).show();
                 }
                 else if (phn.length()!=10){
                     Toast.makeText(check.this, "PLEASE ENTER VALID PHONE NUMBER", Toast.LENGTH_LONG).show();
                 }else {
                     String ognum="91"+phn;
                     Toast.makeText(check.this, ognum, Toast.LENGTH_LONG).show();
                     reference = FirebaseDatabase.getInstance().getReference("DATA");
                     Query checkphonenum = reference.orderByChild("PHONE").equalTo(ognum);
                     checkphonenum.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             //Toast.makeText(check.this, "WELCOME CONDUCTOR", Toast.LENGTH_LONG).show();
                             if(snapshot.exists()) {
                                 Intent intent = new Intent(check.this, conductor.class);
                                 intent.putExtra("numberr", ognum);
                                 startActivity(intent);
                                 finish();
                             }
                             else{
                                 Toast.makeText(check.this, "THIS LOGIN IS AUTHENTICATED TO SPECIFIC USERS ONLY", Toast.LENGTH_LONG).show();
                                 Intent intent = new Intent(check.this, MainActivity2.class);
                                 startActivity(intent);
                                 finish();
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {
                             Toast.makeText(check.this, "THIS LOGIN IS AUTHENTICATED TO SPECIFIC USERS ONLY", Toast.LENGTH_LONG).show();
                             Intent intent = new Intent(check.this, MainActivity2.class);
                             startActivity(intent);
                             finish();
                         }
                     });
                 }
             }
         });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(check.this, administrator1.class);
                startActivity(intent);
                finish();
            }
        });
    }
}