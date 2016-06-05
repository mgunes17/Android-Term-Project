package com.example.must.mobiletermproject.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.must.mobiletermproject.R;
import com.example.must.mobiletermproject.database.RecordBaseHelper;
import com.example.must.mobiletermproject.database.RecordList;

import java.text.ParseException;

public class DisplayDataFilterActivity extends AppCompatActivity {
    private Button displayAllData;
    private Button filterData;
    private RecordList recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        initialize();
        buttonHandler();

    }

    private void initialize(){
        displayAllData = (Button) findViewById(R.id.btnDisplayAllData);
        filterData = (Button) findViewById(R.id.btnFilterData);
        recordList = new RecordList();
    }

    private void buttonHandler(){
        displayAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordBaseHelper rbh = new RecordBaseHelper(DisplayDataFilterActivity.this);
                try {
                    recordList.setRecordList(rbh.read());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recordList", recordList);
                    Intent intent = new Intent(DisplayDataFilterActivity.this, DisplayRecordActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                catch (ParseException e) {
                    Toast.makeText(DisplayDataFilterActivity.this, "Okuma hatası", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
