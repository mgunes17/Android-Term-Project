package com.example.must.mobiletermproject.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.must.mobiletermproject.R;
import com.example.must.mobiletermproject.database.RecordBaseHelper;

public class DeleteActivity extends AppCompatActivity {
    private Button noLocation;
    private Button threshold;
    private Button all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        initialize();
        buttonHandler();
    }

    private void initialize(){
        noLocation = (Button) findViewById(R.id.btnDeleteNoLocation);
        threshold = (Button) findViewById(R.id.btnDeleteThreshold);
        all = (Button) findViewById(R.id.btnDeleteAll);
    }

    private void buttonHandler(){
        noLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(DeleteActivity.this);
                dlg.setTitle("UYARI!");
                dlg.setMessage("Bu işlem geri alınamaz! Silmek istediğinize misiniz?");

                dlg.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RecordBaseHelper rbh = new RecordBaseHelper(DeleteActivity.this);
                        rbh.deleteNoLocation();

                        Toast.makeText(DeleteActivity.this, "Silme işlemi gerçekleşti", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

                dlg.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Toast.makeText(DeleteActivity.this, "İptal ettiniz", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

                AlertDialog ad = dlg.create();
                dlg.show();
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeleteActivity.this, "Bu buton işlevsizdir", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        threshold.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(DeleteActivity.this, "Bu buton işlevsizdir", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
