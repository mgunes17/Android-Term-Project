package com.example.must.mobiletermproject.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.must.mobiletermproject.R;
import com.example.must.mobiletermproject.database.Record;
import com.example.must.mobiletermproject.database.RecordBaseHelper;
import com.example.must.mobiletermproject.database.RecordList;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DisplayDataFilterActivity extends AppCompatActivity {
    private Button displayData;
    private RecordList recordList;
    private RadioButton rdoListView;
    private RadioButton rdoMap;
    private RadioButton rdoSignalStrengthInc;
    private RadioButton rdoSignalStrengthDec;
    private RadioButton rdoDateInc;
    private RadioButton rdoDateDec;
    private RadioButton rdoVodafone;
    private RadioButton rdoTurkcell;
    private RadioButton rdoThresholdUnder;
    private RadioButton rdoThresholAbove;
    private RadioGroup rdoThreshold;
    private RadioGroup rdoOperator;
    private RadioGroup rdoView;
    private RadioGroup rdoSortCriteria;
    private TextView sortCriteria;
    private String operatorName;
    private Class goClass; //geçiş yapılacak activity e ait class
    private int threshold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        initialize();
        handler();

        rdoListView.setOnCheckedChangeListener(new RadioSelection());
        rdoMap.setOnCheckedChangeListener(new RadioSelection());
    }

    private void initialize(){
        displayData = (Button) findViewById(R.id.btnSee);
        recordList = new RecordList();
        sortCriteria = (TextView) findViewById(R.id.txtSortCriteria);

        //radio butonlar
        rdoListView = (RadioButton) findViewById(R.id.rdoListView);
        rdoMap = (RadioButton) findViewById(R.id.rdoMap);
        rdoSignalStrengthInc = (RadioButton) findViewById(R.id.rdoSignalStrengthInc);
        rdoSignalStrengthDec = (RadioButton) findViewById(R.id.rdoSignalStrengthDec);
        rdoDateInc = (RadioButton) findViewById(R.id.rdoSDateInc);
        rdoDateDec = (RadioButton) findViewById(R.id.rdoDateDec);
        rdoVodafone = (RadioButton) findViewById(R.id.rdoVodafone);
        rdoTurkcell = (RadioButton) findViewById(R.id.rdoTurkcell);
        rdoOperator = (RadioGroup) findViewById(R.id.rdoOperator);
        rdoView = (RadioGroup) findViewById(R.id.rdoViewType);
        rdoSortCriteria = (RadioGroup) findViewById(R.id.rdoSortCriteria);
        rdoThreshold = (RadioGroup) findViewById(R.id.rdoThreshold);
        rdoThresholAbove = (RadioButton) findViewById(R.id.rdoThresholdAbove);
        rdoThresholdUnder = (RadioButton) findViewById(R.id.rdoThresholdUnder);

        rdoSignalStrengthInc.setVisibility(Button.GONE);
        rdoSignalStrengthDec.setVisibility(Button.GONE);
        rdoDateDec.setVisibility(Button.GONE);
        rdoDateInc.setVisibility(Button.GONE);
        sortCriteria.setVisibility(TextView.GONE);

        SharedPreferences sp = getSharedPreferences("ayarlar", Context.MODE_PRIVATE);
        threshold = sp.getInt("threshold", -85);

    }

    public class RadioSelection implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
            if(rdoListView.isChecked()){
                rdoSignalStrengthInc.setVisibility(Button.VISIBLE);
                rdoSignalStrengthDec.setVisibility(Button.VISIBLE);
                rdoDateDec.setVisibility(Button.VISIBLE);
                rdoDateInc.setVisibility(Button.VISIBLE);
                sortCriteria.setVisibility(TextView.VISIBLE);

                goClass = DisplayRecordActivity.class;
            }
            if(rdoMap.isChecked()){
                rdoSignalStrengthInc.setVisibility(Button.GONE);
                rdoSignalStrengthDec.setVisibility(Button.GONE);
                rdoDateDec.setVisibility(Button.GONE);
                rdoDateInc.setVisibility(Button.GONE);
                sortCriteria.setVisibility(TextView.GONE);

                goClass = MapActivity.class;
            }

        }
    }

    private void handler(){
        displayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rdoOperator.getCheckedRadioButtonId();
                RadioButton selected = (RadioButton) findViewById(selectedId);

                if(selected.getText().toString().equals("Vodafone")){
                    operatorName = "VODAFONE TR";
                }
                else if(selected.getText().toString().equals("Turkcell")){
                    operatorName = "TR TURKCELL";
                }
                else{
                    operatorName = "all";
                }

                selectedId = rdoThreshold.getCheckedRadioButtonId();
                selected = (RadioButton) findViewById(selectedId);

                if(selected.getText().toString().equals("Kullanma")){
                    threshold = 0;
                }
                else if(selected.getText().toString().equals("Üstündeki Değerler")){
                    threshold = -threshold;
                }

                RecordBaseHelper rbh = new RecordBaseHelper(DisplayDataFilterActivity.this);
                try {
                    recordList = new RecordList();
                    if(operatorName.equals("all"))
                        recordList.setRecordList(rbh.read());
                    else
                        recordList.setRecordList(rbh.readOperator(operatorName, threshold));

                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                try{
                    selectedId = rdoSortCriteria.getCheckedRadioButtonId();
                    selected = (RadioButton)findViewById(selectedId);

                    if(selected.getText().toString().equals("Sinyal Gücü Artan")){
                        Collections.sort(recordList.getRecordList(), new Comparator<Record>() {
                            public int compare(Record r1, Record r2) {
                                return  r2.getSinyalGucu() - r1.getSinyalGucu();
                            }
                        });
                    }
                    else if(selected.getText().toString().equals("Sinyal Gücü Azalan")){
                        Collections.sort(recordList.getRecordList(), new Comparator<Record>() {
                            public int compare(Record r1, Record r2) {
                                return  r1.getSinyalGucu() - r2.getSinyalGucu();
                            }
                        });
                    }
                    else if(selected.getText().toString().equals("Tarih Artan")){
                        Collections.sort(recordList.getRecordList(), new Comparator<Record>() {
                            public int compare(Record r1, Record r2) {
                                return  r1.getZaman().compareTo(r2.getZaman());
                            }
                        });
                    }
                    else if(selected.getText().toString().equals("Tarih Azalan")){
                        Collections.sort(recordList.getRecordList(), new Comparator<Record>() {
                            public int compare(Record r1, Record r2) {
                                return  r2.getZaman().compareTo(r1.getZaman());
                            }
                        });
                    }
                }
                catch (Exception e){
                    //
                }


                Bundle bundle = new Bundle();
                bundle.putSerializable("recordList", recordList);
                Intent intent = new Intent(DisplayDataFilterActivity.this, goClass);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}
