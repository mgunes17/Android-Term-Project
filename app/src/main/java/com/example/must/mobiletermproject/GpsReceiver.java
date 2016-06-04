package com.example.must.mobiletermproject;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import com.example.must.mobiletermproject.UI.MainActivity;
import com.example.must.mobiletermproject.UI.StartActivity;

/**
 * Created by must on 26.05.2016.
 */
public class GpsReceiver implements LocationListener {
    private Context context;
    private StartActivity m;

    public GpsReceiver(StartActivity m, Context context){
        super();
        this.m = m;
        this.context = context;
    }

    public GpsReceiver(){}

    @Override
    public void onLocationChanged(Location location){
        if(location != null){
            double enlem = location.getLatitude();
            double boylam = location.getLongitude();
            /*double irtifa = location.getAltitude();
            double hiz = location.getSpeed();*/

            m.setLatitude(String.valueOf(enlem));
            m.setLongitude(String.valueOf(boylam));
        }
        else{
            Toast.makeText(context,"Konum Bilgisi Alınamıyor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){

    }

    @Override
    public void onProviderEnabled(String provider){
        //konum açıldı
    }

    @Override
    public void onProviderDisabled(String provider){
        //konum kapandı
    }
}