package com.example.must.mobiletermproject.UI;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.must.mobiletermproject.R;
import com.example.must.mobiletermproject.database.RecordList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private RecordList recordList;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initialize();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(41.114056362957409, 28.26036241837826);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(41.114056362957409, 28.26036241837826);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Lüleburgaz"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        float color;

        for(int i=0; i<recordList.getRecordList().size(); i++){
            if(recordList.getRecordList().get(i).getSinyalGucu() < -99)
                color = BitmapDescriptorFactory.HUE_RED;
            else if(recordList.getRecordList().get(i).getSinyalGucu() < -89)
                color = BitmapDescriptorFactory.HUE_ORANGE;
            else if(recordList.getRecordList().get(i).getSinyalGucu() < -79)
                color = BitmapDescriptorFactory.HUE_YELLOW;
            else
                color = BitmapDescriptorFactory.HUE_GREEN;

            location = new LatLng(recordList.getRecordList().get(i).getEnlem(),
                    recordList.getRecordList().get(i).getBoylam());
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(color)).
                    position(location).title(
                    recordList.getRecordList().get(i).getSinyalGucu() + " " +
                            recordList.getRecordList().get(i).getOperatorAdi()

            ));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    private void initialize(){
        Intent intent = getIntent();
        recordList = (RecordList) intent.getSerializableExtra("recordList");
    }
}
