package com.example.must.mobiletermproject.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.must.mobiletermproject.R;
import com.example.must.mobiletermproject.RecordAdapter;
import com.example.must.mobiletermproject.RecordDBAdapter;
import com.example.must.mobiletermproject.database.RecordList;

public class DisplayRecordActivity extends AppCompatActivity {
    private RecordList recordList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_record);

        initialize();
        RecordDBAdapter ra = new RecordDBAdapter(this, recordList.getRecordList());
        listView.setAdapter(ra);

    }

    private void initialize(){
        Intent intent = getIntent();
        recordList = (RecordList) intent.getSerializableExtra("recordList");
        listView = (ListView) findViewById(R.id.lstRecord);
    }
}
