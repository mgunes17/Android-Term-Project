package com.example.must.mobiletermproject;

import android.app.Activity;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

import com.example.must.mobiletermproject.UI.MainActivity;
import com.example.must.mobiletermproject.UI.StartActivity;

/**
 * Created by must on 25.05.2016.
 */
public class MyPhoneStateListener extends PhoneStateListener {
    private int strength = 0;
    private Activity activity;
    private StartActivity m;

    public MyPhoneStateListener(Activity activity, StartActivity m){
        super();
        this.activity = activity;
        this.m = m;
    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        strength = signalStrength.getGsmSignalStrength();
        strength = (2 * strength) - 113; // -> dBm

        m.setSignal("Sinyal Gücü:"+String.valueOf(strength));
    }

    public int getSignalStrength(){
        return strength;
    }
}