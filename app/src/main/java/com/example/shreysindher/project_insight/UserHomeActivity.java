package com.example.shreysindher.project_insight;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserHomeActivity extends AppCompatActivity {

    Location mLocation;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rb_a1)
    RadioButton rb1;
    @BindView(R.id.rb_a2)
    RadioButton rb2;
    @BindView(R.id.rb_a3)
    RadioButton rb3;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String alert1, alert2, alert3, mAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        alert1 = rb1.getText().toString();
        alert2 = rb2.getText().toString();
        alert3 = rb3.getText().toString();

    }

    @OnClick(R.id.btn_submit)
    public void submitRequest() {
        mAlert = getAlert();

        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    mUser = mAuth.getCurrentUser();
                    mLocation = locationResult.getLastLocation();
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                    if (mLocation != null) {
                        mDatabaseReference.child(mUser.getUid()).child("alert").setValue(mAlert);
                        mDatabaseReference.child(mUser.getUid()).child("name").setValue(mUser.getDisplayName());
                        mDatabaseReference.child(mUser.getUid()).child("latitude").setValue(mLocation.getLatitude());
                        mDatabaseReference.child(mUser.getUid()).child("longitude").setValue(mLocation.getLongitude());
                    }
                }
            }, null);
        }

        Toast.makeText(UserHomeActivity.this, "Request Submitted!\nHelp is on the way", Toast.LENGTH_SHORT).show();
        radioGroup.clearCheck();
    }

    public String getAlert() {
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
            RadioButton rb = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            return rb.getText().toString();
        } else {
            Toast.makeText(UserHomeActivity.this, "Select the type of alert", Toast.LENGTH_SHORT).show();
            return alert1;
        }
    }

    @OnClick(R.id.btn_broadcastListen)
    public void gotoBroadcast() {
        startActivity(new Intent(UserHomeActivity.this, UserBroadcastActivity.class));
    }
}
