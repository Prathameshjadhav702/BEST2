package com.example.best;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class administrator1 extends AppCompatActivity {
    EditText depot,number,passcode;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        depot=findViewById(R.id.textView11);
        number=findViewById(R.id.editTextPhone2);
        passcode=findViewById(R.id.textView19);
        Submit=findViewById(R.id.button7);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        String dep=depot.getEditableText().toString();
        String phnum=number.getEditableText().toString();
        String pascode=passcode.getEditableText().toString();
        if (phnum.isEmpty()){
            Toast.makeText(administrator1.this, "PLEASE ENTER YOUR PHONE NUMBER", Toast.LENGTH_LONG).show();
        }
        else if(pascode.isEmpty()){
            Toast.makeText(administrator1.this, "PLEASE ENTER PASSCODE", Toast.LENGTH_LONG).show();
        }
        else if(dep.isEmpty()){
            Toast.makeText(administrator1.this, "PLEASE ENTER BUS DEPOT", Toast.LENGTH_LONG).show();
        }
        else {

            String[] depot = {"WORLI", "WADALA", "SEWRI"};
            String[] code = {"555", "222", "333"};
            int i=0;
            for(i = 0;i<depot.length;i++){

                if (dep.equals(depot[i])) {
                    int index = Arrays.asList(depot).indexOf(dep);
                    String codee = code[i];
                    if (pascode.equals(codee)) {
                        Toast.makeText(administrator1.this, "WELCOME ADMIN", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(administrator1.this, admin2.class);
                        intent.putExtra("ADMINNUMBER", phnum);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(administrator1.this, "PASSCODE DOESN'T MATCH", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(administrator1.this, "INVALID BUS DEPOT", Toast.LENGTH_LONG).show();
                }
            }
        }
            }
        });
    }
}