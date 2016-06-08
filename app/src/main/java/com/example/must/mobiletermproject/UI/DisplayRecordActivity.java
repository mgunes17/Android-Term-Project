package com.example.must.mobiletermproject.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.must.mobiletermproject.R;
import com.example.must.mobiletermproject.RecordAdapter;
import com.example.must.mobiletermproject.RecordDBAdapter;
import com.example.must.mobiletermproject.database.RecordList;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DisplayRecordActivity extends AppCompatActivity {
    private RecordList recordList;
    private ListView listView;
    private List<Address> eslesmeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_record);

        initialize();
        RecordDBAdapter ra = new RecordDBAdapter(this, recordList.getRecordList());
        listView.setAdapter(ra);

        Geocoder gc = new Geocoder(DisplayRecordActivity.this, Locale.getDefault());
        try {
            eslesmeler = gc.getFromLocation(recordList.getRecordList().get(0).getEnlem(),
                    recordList.getRecordList().get(0).getBoylam(), recordList.getRecordList().size());

        } catch (IOException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Geocoder gc = new Geocoder(DisplayRecordActivity.this, Locale.getDefault());
                List<Address> adres = null;
                try {
                    adres = gc.getFromLocation(recordList.getRecordList().get(position).getEnlem(),
                            recordList.getRecordList().get(position).getBoylam(), 1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                final AlertDialog.Builder diyalog = new AlertDialog.Builder(DisplayRecordActivity.this);
                diyalog.setMessage(
                        adres.get(0).getSubLocality()+" "+adres.get(0).getSubAdminArea()+" "+
                                adres.get(0).getAdminArea()+" "+adres.get(0).getCountryName()
                ).setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diyalog.setCancelable(true);
                    }
                });

                diyalog.create().show();
            }

        });

    }

    private void initialize(){
        Intent intent = getIntent();
        recordList = (RecordList) intent.getSerializableExtra("recordList");
        listView = (ListView) findViewById(R.id.lstRecord);
    }
}
