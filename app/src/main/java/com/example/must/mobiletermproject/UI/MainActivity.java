package com.example.must.mobiletermproject.UI;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.must.mobiletermproject.GpsReceiver;
import com.example.must.mobiletermproject.MyPhoneStateListener;
import com.example.must.mobiletermproject.R;

public class MainActivity extends AppCompatActivity {
    private Button startMode;
    private Button displayData;
    private Button settings;
    private Button exit;
    private Button delete;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        buttonHandler();
    }

    private void initialize(){
        startMode = (Button) findViewById(R.id.btnStartMode);
        displayData = (Button) findViewById(R.id.btnDisplayData);
        settings = (Button) findViewById(R.id.btnSettings);
        delete = (Button) findViewById(R.id.btnDeleteData);
        exit = (Button) findViewById(R.id.btnExit);
    }

    private void buttonHandler(){
        startMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        displayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, DisplayDataFilterActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });
    }
}