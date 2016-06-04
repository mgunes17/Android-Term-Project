package com.example.must.mobiletermproject.UI;

import android.Manifest;
import android.content.Context;
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

public class StartActivity extends AppCompatActivity {
    private TextView signal;
    private TextView operatorName;
    private TextView latitude;
    private TextView longitude;
    private Button start;
    TelephonyManager mng;
    LocationManager lmng;
    private LocationListener receiver;
    MyPhoneStateListener pslistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initialize();
        buttonHandler();
    }

    public void setSignal(String strength) {
        signal.setText(strength);
    }

    public void setLatitude(String text) {
        latitude.setText(text);
    }

    public void setLongitude(String text) {
        longitude.setText(text);
    }

    public void setOperatorName(String text) {
        operatorName.setText(text);
    }

    private void initialize() {
        signal = (TextView) findViewById(R.id.txtSignal);
        operatorName = (TextView) findViewById(R.id.txtOperatorName);
        latitude = (TextView) findViewById(R.id.txtLatitude);
        longitude = (TextView) findViewById(R.id.txtLongitude);
        start = (Button) findViewById(R.id.btnStart);
        pslistener = new MyPhoneStateListener(StartActivity.this, this);
        mng = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        receiver = new GpsReceiver(StartActivity.this, StartActivity.this);

    }

    private void buttonHandler() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mng.listen(pslistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                    signal.setText(String.valueOf(mng.getNetworkOperatorName()) + " " +
                            pslistener.getSignalStrength());

                    lmng = (LocationManager) StartActivity.this.getSystemService(Context.LOCATION_SERVICE);

                    Criteria crt = new Criteria();
                    crt.setAccuracy(Criteria.ACCURACY_FINE);
                    crt.setPowerRequirement(Criteria.POWER_MEDIUM);
                    crt.setSpeedRequired(false);

                    String provider = lmng.getBestProvider(crt, true);
                    lmng.requestLocationUpdates(provider, 1000, 1.0F, receiver);

                    //lmng = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
                    if (ContextCompat.checkSelfPermission(
                            StartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                    }

                    setOperatorName("Operatör Adı:"+String.valueOf(mng.getNetworkOperatorName()));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
