package com.example.must.mobiletermproject.UI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.must.mobiletermproject.GpsReceiver;
import com.example.must.mobiletermproject.MyPhoneStateListener;
import com.example.must.mobiletermproject.R;
import com.example.must.mobiletermproject.database.Record;
import com.example.must.mobiletermproject.database.RecordList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private TextView signal;
    private TextView operatorName;
    private TextView latitude;
    private TextView longitude;
    private Button start;
    private Button display;
    private LocationListener receiver;
    private List<Record> recordList;
    private SharedPreferences sp;
    TelephonyManager mng;
    LocationManager lmng;
    MyPhoneStateListener pslistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initialize();
        buttonHandler();
    }

    public void addRecord(Record record){
        recordList.add(record);
    }

    public void setSignal(String strength) {
        signal.setText(strength);
    }

    public String getSignal(){
        return signal.getText().toString();
    }

    public String getOperatorName(){
        return operatorName.getText().toString();
    }

    public String getLatitude(){
        return latitude.getText().toString();
    }

    public String getLongitude(){
        return longitude.getText().toString();
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
        recordList = new ArrayList<>();
        display = (Button) findViewById(R.id.btnDisplaySessionSave);
        //display.setVisibility(Button.INVISIBLE);
        sp = this.getSharedPreferences("ayarlar", Context.MODE_PRIVATE);
    }

    private void buttonHandler() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {
                mng.listen(pslistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                lmng = (LocationManager) StartActivity.this.getSystemService(Context.LOCATION_SERVICE);
                setOperatorName(String.valueOf(mng.getNetworkOperatorName()));

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

                CreateRecordList crl = new CreateRecordList();
                crl.execute();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, DisplaySessionRecordActivity.class);
                Bundle bundle = new Bundle();
                RecordList rl = new RecordList();
                rl.setRecordList(recordList);
                bundle.putSerializable("recordList", rl);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public class CreateRecordList extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog dialog;
        private int duration;
        private int recordCount;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            int defaultDuration = 1;
            duration = sp.getInt("recordDuration", defaultDuration);
            int defaultRecordCount = 10;
            recordCount = sp.getInt("recordCount", defaultRecordCount);
            //recordList = new ArrayList<>();

            Toast.makeText(StartActivity.this, "Kayıt başladı", Toast.LENGTH_SHORT)
                    .show();
            dialog = new ProgressDialog(StartActivity.this);
            dialog.setMax(recordCount);
            dialog.setProgress(0);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params){

            // add recordList ile activity deki listeyi doldur
            Record record;
            long time = duration * 1000;

            for(int i = 0; i<recordCount; i++){
                try{
                    publishProgress(i);

                    record = new Record();
                    record.setOperatorAdi(operatorName.getText().toString());

                    if(signal.getText().toString().length() != 0)
                        record.setSinyalGucu(Integer.parseInt(signal.getText().toString()));

                    if(latitude.getText().toString().length() != 0)
                        record.setEnlem(Double.parseDouble(latitude.getText().toString()));

                    if(longitude.getText().toString().length() != 0)
                        record.setBoylam(Double.parseDouble(longitude.getText().toString()));

                    record.setZaman(new Date());
                    addRecord(record);

                    Thread.sleep(time);
                }
                catch (Exception e){
                    record = new Record();
                    record.setZaman(new Date());
                    record.setEnlem(0.0);
                    record.setBoylam(0.0);
                    record.setSinyalGucu(0);
                    record.setOperatorAdi("");
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate();
            Integer currentProgress = values[0];
            dialog.setProgress(currentProgress);
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            dialog.dismiss();
            //display.setVisibility(Button.VISIBLE);
            Toast.makeText(StartActivity.this, "Başarıyla tamamlandı", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        protected void onCancelled(Void result){
            super.onCancelled(result);
            dialog.dismiss();
        }
    }
}
