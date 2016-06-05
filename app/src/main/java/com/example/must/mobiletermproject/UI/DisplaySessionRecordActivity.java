package com.example.must.mobiletermproject.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.must.mobiletermproject.R;
import com.example.must.mobiletermproject.RecordAdapter;
import com.example.must.mobiletermproject.database.RecordBaseHelper;
import com.example.must.mobiletermproject.database.RecordList;

public class DisplaySessionRecordActivity extends AppCompatActivity {
    private RecordList recordList;
    private Intent intent;
    private ListView list;
    private TextView operatorName;
    private TextView maxStrength;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_session_record);
        initialize();
        buttonHandler();

        RecordAdapter ra = new RecordAdapter(this, recordList.getRecordList());
        list.setAdapter(ra);
        operatorName.setText("Operatör Adı:"+recordList.getRecordList().get(0).getOperatorAdi());
        maxStrength.setText("Max Sinyal Gücü:"+findMax(recordList));
    }

    private void initialize(){
        intent = getIntent();
        recordList = (RecordList) intent.getSerializableExtra("recordList");
        list = (ListView) findViewById(R.id.lstRecords);
        operatorName = (TextView) findViewById(R.id.txtSessionOperatorName);
        maxStrength = (TextView) findViewById(R.id.txtSessionMaxStrength);
        save = (Button) findViewById(R.id.btnSessionSave);

    }

    private void buttonHandler(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordBaseHelper rbh = new RecordBaseHelper(getApplicationContext());

                for(int i = 0; i<recordList.getRecordList().size(); i++){
                    long x = rbh.insert(recordList.getRecordList().get(i));
                    if(x < -1){
                        Toast.makeText(DisplaySessionRecordActivity.this,"DB kayıt hatası", Toast.LENGTH_SHORT).show();
                    }
                }

                Toast.makeText(DisplaySessionRecordActivity.this,"Liste kaydedildi", Toast.LENGTH_SHORT).show();
                save.setClickable(false);
            }
        });
    }

    private String findMax(RecordList recordList){
        int max = -113;

        for(int i=0; i<recordList.getRecordList().size(); i++){
            if(recordList.getRecordList().get(i).getSinyalGucu() > max){
                max = recordList.getRecordList().get(i).getSinyalGucu();
            }
        }

        return String.valueOf(max);
    }
}
