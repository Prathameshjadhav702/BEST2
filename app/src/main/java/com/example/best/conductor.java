package com.example.best;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.best.ml.Model;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

//import com.example.catdognew.ml.Model;
//import com.example.cd2.ml.Model;

public class conductor extends AppCompatActivity {

    TextView phonenumber;
    FirebaseDatabase rootnode;
    Handler mHandler;
    DatabaseReference reference;
    private LocationManager locationManager;
    FusedLocationProviderClient client;
    LocationRequest locationRequest;
    LocationCallback mlocationcallback;
    private ImageView imgView;
    private Button select, predict, reset;
    private TextView tv;
    private Bitmap img;


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor);

        this.mHandler = new Handler();
        m_Runnable.run();
    }


    private final Runnable m_Runnable = new Runnable() {
        @SuppressLint("NewApi")
        @Override
        public void run() {

            phonenumber = findViewById(R.id.textView5);
                TextView name = findViewById(R.id.textView6);
                TextView sat = findViewById(R.id.textView9);
                TextView busnum = findViewById(R.id.textView3);
                Button vac = findViewById(R.id.button);
                Button LD = findViewById(R.id.button2);
                Button MOD = findViewById(R.id.button5);
                Button HD = findViewById(R.id.button4);
            Button VHD = findViewById(R.id.button6);
            imgView = (ImageView) findViewById(R.id.imageView4);
           // tv = (TextView) findViewById(R.id.textView);
            select = (Button) findViewById(R.id.SELECT);
             reset= (Button) findViewById(R.id.RESET);
            predict = (Button) findViewById(R.id.PREDICT2);
                String[] permission = {
                        Manifest.permission.READ_PHONE_NUMBERS
                };

                requestPermissions(permission, 102);
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

                String number = getIntent().getStringExtra("numberr");
                phonenumber.setText(number);
                client = LocationServices.getFusedLocationProviderClient(conductor.this);

                reference = FirebaseDatabase.getInstance().getReference("DATA");
                Query checkphonenum = reference.orderByChild("PHONE").equalTo(number);
                mlocationcallback = new LocationCallback() {
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }

                        Location location = locationResult.getLastLocation();
                        String LONGI = String.valueOf(location.getLongitude());
                        String latti = String.valueOf(location.getLatitude());
                        Toast.makeText(conductor.this, "LONGITUDE: " + LONGI + "LATITUDE" + latti, Toast.LENGTH_LONG).show();
                        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                        DatabaseReference LONGG = rootNode.getReference("DATA").child(number).child("LONGITUDE");
                        DatabaseReference LATTI = rootNode.getReference("DATA").child(number).child("LATITUDE");
                        LONGG.setValue(LONGI);
                        LATTI.setValue(latti);
                    }
                };
                checkphonenum.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            //  Toast.makeText(conductor.this, "WELCOME CONDUCTOR", Toast.LENGTH_LONG).show();
                            String namee = snapshot.child(number).child("CONDUCTOR").getValue(String.class);
                            String num = snapshot.child(number).child("NUMBER").getValue(String.class);
                            busnum.setText(num);
                            name.setText(namee);
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            checksettandlocupdates();


                        } else {
                            //   Toast.makeText(conductor.this, "THIS LOGIN IS AUTHENTICATED TO SPECIFIC USERS ONLY", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(conductor.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                    private void checksettandlocupdates() {
                        if (ActivityCompat.checkSelfPermission(conductor.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(conductor.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        Task<Location> task = client.getLastLocation();
                        task.addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                String LAT = String.valueOf(location.getLatitude());
                                String LONG = String.valueOf(location.getLongitude());
                                locationRequest = LocationRequest.create();
                                locationRequest.setInterval(4000);
                                locationRequest.setFastestInterval(2000);
                                locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);
                                if (ActivityCompat.checkSelfPermission(conductor.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(conductor.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                client.requestLocationUpdates(locationRequest, mlocationcallback, null);
                            }
                        });
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //  });
                // Toast.makeText(conductor.this, reference,Toast.LENGTH_LONG).show();
                //Query checkphonenumber=reference.equalTo("OFF");
//        Toast.makeText(conductor.this, (CharSequence) checkphonenumber,Toast.LENGTH_LONG).show();
                vac.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DATA").child(number).child("STATUS");
                        reference.setValue("VACANT");
                        sat.setText("VACANT");
                    }
                });

                MOD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DATA").child(number).child("STATUS");
                        reference.setValue("MODERATE");
                        sat.setText("MODERATE");
                    }
                });
                LD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DATA").child(number).child("STATUS");
                        reference.setValue("LOW DENSITY");
                        sat.setText("LOW DENSITY");
                    }
                });
                HD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DATA").child(number).child("STATUS");
                        reference.setValue("HIGH DENSITY");
                        sat.setText("HIGH DENSITY");
                    }
                });
                VHD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DATA").child(number).child("STATUS");
                        reference.setValue("VERY HIGH DENSITY");
                        sat.setText("VERY HIGH DENSITY");
                    }
                });
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(conductor.this, changebusnumber.class);
                        intent.putExtra("nummber",number);
                        String busnumm =  busnum.getText().toString();
                        String namme=  name.getText().toString();
                        intent.putExtra("busn",busnumm);
                        intent.putExtra("na",namme);
                        startActivity(intent);
                        finish();
                    }
                });
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 100);

                }
            });

            predict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(imgView.getDrawable()== null){
                        Toast.makeText(conductor.this, "PLEASE SELECT A IMAGE", Toast.LENGTH_LONG).show();
                    }else {
                        img = Bitmap.createScaledBitmap(img, 64, 64, true);
                        try {
                            Model model = Model.newInstance(getApplicationContext());

                            // Creates inputs for reference.
                            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 64, 64, 3}, DataType.FLOAT32);
                            TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                            tensorImage.load(img);
                            ByteBuffer byteBuffer = tensorImage.getBuffer();
                            inputFeature0.loadBuffer(byteBuffer);

                            // Runs model inference and gets result.
                            Model.Outputs outputs = model.process(inputFeature0);

                            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                            // Releases model resources if no longer used.
                            model.close();

                            String[] Labels = {"HIGH DENSITY", "LOW DENSITY", "MODERATE", "VERY HIGH DENSITY", "VACANT"};


                            for (int i = 0; i < 5; i++) {
                                int x = (int) Math.round(outputFeature0.getFloatArray()[i]);
                                if (x > 0.0) {
                                    sat.setText(Labels[i]);
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DATA").child(number).child("STATUS");
                                    reference.setValue(Labels[i]);
                                    break;

                                }
                            }

                        } catch (IOException e) {
                            // TODO Handle the exception
                        }
                    }

                }
            });


        }

            ;

    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100)
        {
            imgView.setImageURI(data.getData());

            Uri uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


