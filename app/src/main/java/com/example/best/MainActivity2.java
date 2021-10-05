
package com.example.best;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    Button LOGIN;
    private LocationManager locationManager;
    TextView longi, latti;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    ImageButton locationn,admin;
    Marker marker;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LOGIN = findViewById(R.id.login);
        //longi = findViewById(R.id.textView11);
        // latti = findViewById(R.id.textView2);
        locationn= findViewById(R.id.imageView3);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();
        locationn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });


        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, check.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void getCurrentLocation() {
        //  ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED

       // ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
            return;
        }
        if (ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!= null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            double USERLAT=  location.getLatitude();
                            double USERLONG=  location.getLongitude();

                            LatLng latLng= new LatLng(location.getLatitude(),location.getLongitude());
//                            latti.setText("LATTITUDE: "+USERLAT+" "+"LONGITUDE: "+USERLONG);
                            MarkerOptions optionss= new MarkerOptions();
                            optionss.position(latLng);
                            optionss.title("you are here");
//                            longi.setText("ACCURACY: "+ location.getAccuracy());
                            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                                @Override
                                public void onMyLocationChange(Location location) {

                                }
                            });

                            int strokeColor = 0xffff0000; //red outline
                            int shadeColor = 0x44ff0000; //opaque red fill
                            CircleOptions circle= new CircleOptions().center(latLng).radius(400).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
                            googleMap.addCircle(circle);
                           // circle.fillColor(Color.rgb(208, 208, 255));
                            //googleMap.fillColor(shadeColor);
                              //   googleMap.strokeColor(strokeColor);
                          //  circle.strokeColor(Color.argb(0,0,0,255));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                           // FirebaseDatabase.getInstance().getReference("DATA").child("LONGITUDE");
                            googleMap.addMarker(optionss);
                            //Location locationA = new Location("point A");

                            //locationA.setLatitude(USERLAT);
                            //locationA.setLongitude(USERLONG);

                           // Location locationB = new Location("point B");

                          //  locationB.setLatitude(19.014012);
                          //  locationB.setLongitude(72.8343188);

                         //   double distance = locationA.distanceTo(locationB);
                       //     String dist=String.valueOf(distance);
                       //     float[] results = new float[1];
                            ArrayList<Double> LAT = new ArrayList<Double>();
                            ArrayList<Double> LONG = new ArrayList<Double>();
                            FirebaseDatabase.getInstance().getReference().child("DATA").addValueEventListener(new ValueEventListener() {
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                        String lattt =  (String) snapshot.child("LATITUDE").getValue();
                                        String looong = (String) snapshot.child("LONGITUDE").getValue();
                                        String density=(String) snapshot.child("STATUS").getValue();
                                        String number=(String) snapshot.child("NUMBER").getValue();
                                        double lattii = new Double(lattt);
                                        double longiii = new Double(looong);
                                        float[] results = new float[1];
                                        Location.distanceBetween(USERLAT, USERLONG,lattii,longiii, results);
                                        float distanceInMeters = results[0];
                                        String dist= String.valueOf(distanceInMeters);
                                  //      Toast.makeText(MainActivity2.this, dist, Toast.LENGTH_LONG).show();
                                        boolean isWithin400m = distanceInMeters < 400;
                                        if( isWithin400m == true){
                                           LatLng latLng2= new LatLng(lattii,longiii);

                                           // String stopname=bustopname[i];
                                            if(density.equals("VACANT")){
                                                if (marker != null) {
                                                    marker.remove();
                                                }
                                               googleMap.addMarker(new MarkerOptions().position(latLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus2)).title(number));
                                            }
                                            else if (density.equals("LOW DENSITY")){
                                                if (marker != null) {
                                                    marker.remove();
                                                }
                                                 googleMap.addMarker(new MarkerOptions().position(latLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.blueog)).title(number));
                                            }
                                            else if (density.equals("MODERATE")){
                                                if (marker != null) {
                                                    marker.remove();
                                                }
                                            googleMap.addMarker(new MarkerOptions().position(latLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue)).title(number));
                                            }
                                             else if (density.equals("HIGH DENSITY")){
                                                if (marker != null) {
                                                    marker.remove();
                                                };
                                              googleMap.addMarker(new MarkerOptions().position(latLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.orange)).title(number));
                                            }
                                            else {
                                                if (marker != null) {
                                                    marker.remove();
                                                }
                                                googleMap.addMarker(new MarkerOptions().position(latLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.busxxl)).title(number));
                                                //googleMap.addMarker(new MarkerOptions().position(latLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.busxxl)).title(number));
                                            }
                                        }
                                        else{
                                          //  Toast.makeText(MainActivity2.this, "NO BUSES NEAR BY", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(MainActivity2.this, "NETWORK ERROR PLEASE TRY AFTER SOMETIME", Toast.LENGTH_LONG).show();
                                }
                            });
                            double[] locationsslongi ={ 72.893917,72.893268,72.891010,72.896699,72.885451,72.884444,72.889652,72.890286,72.886941,72.891219,72.888197};
                            double[] locationlatti ={ 19.051609,19.054280,19.052518,19.060048,19.059362,19.056836,19.049910,19.050630,19.049436,19.052204,19.049180};
                            String[] bustopname={"BasantPark","Chemburnaka ","Bhakti Bhawan"," Chembur phatak" ,"Kamgar Nagar" ,"Kurla Station bus stop","Swami Vivekanand College", "Sindhi soc Gymkhana ","Lal Dongar" ,"Bhakti Bhawan" ,"Gymkhana"};
                            for (int i=0;i<locationsslongi.length;i++){
                                float[] results = new float[1];
                                Location.distanceBetween(USERLAT, USERLONG, locationlatti[i], locationsslongi[i], results);
                                float distanceInMeters = results[0];
                                boolean isWithin400m = distanceInMeters < 400;
                                String distance= String.valueOf(isWithin400m);
                                //Toast.makeText(MainActivity2.this,  distance, Toast.LENGTH_LONG).show();

//                                    Toast.makeText(MainActivity2.this, "INSIDE", Toast.LENGTH_LONG).show();
                                  //  MarkerOptions options[i]= new MarkerOptions();
                                    LatLng latLng2= new LatLng(locationlatti[i],locationsslongi[i]);
                                    String stopname=bustopname[i];
                                    googleMap.addMarker(new MarkerOptions().position(latLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop)).title(stopname));

                                    //options1.rotation(location.getBearing());
                            }

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==44){
            if (grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }
}