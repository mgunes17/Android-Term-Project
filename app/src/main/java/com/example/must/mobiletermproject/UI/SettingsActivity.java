package com.example.must.mobiletermproject.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.must.mobiletermproject.R;

public class SettingsActivity extends AppCompatActivity {
    private EditText threshold;
    private EditText recordCount;
    private EditText recordDuration;
    private Button saveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialize();
        buttonHandler();
    }

    private void initialize(){
        threshold = (EditText) findViewById(R.id.edtThreshold);
        recordCount = (EditText) findViewById(R.id.edtRecordNumber);
        recordDuration = (EditText) findViewById(R.id.edtRecordDuration);
        saveSettings = (Button) findViewById(R.id.btnSaveSettings);
    }

    private void buttonHandler(){
        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try{
                int thresholdValue = Integer.valueOf(threshold.getText().toString());
                int recordCountValue = Integer.valueOf(recordCount.getText().toString());
                int recordDurationValue = Integer.valueOf(recordDuration.getText().toString());

                if(thresholdValue<0 || thresholdValue > 113){
                    threshold.requestFocus();
                    threshold.setError("Lütfen 0-113 aralığında bir threshold değeri giriniz.");
                }

                SharedPreferences sp = SettingsActivity.this.getSharedPreferences("ayarlar", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("threshold", thresholdValue);
                editor.putInt("recordCount", recordCountValue);
                editor.putInt("recordDuration", recordDurationValue);

                editor.commit();

                Toast.makeText(SettingsActivity.this, "Ayarlar kaydedildi", Toast.LENGTH_SHORT)
                        .show();

                threshold.setText("");
                recordCount.setText("");
                recordDuration.setText("");
            }
            catch (Exception e){
                Toast.makeText(SettingsActivity.this, "Lütfen geçerli değerler girin", Toast.LENGTH_SHORT)
                        .show();
            }

            }
        });
    }
}
