package com.example.must.mobiletermproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.must.mobiletermproject.database.Record;

import java.util.List;

/**
 * Created by must on 05.06.2016.
 */
public class RecordDBAdapter  extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Record> recordList;

    public RecordDBAdapter(Activity activity, List<Record> recordList){
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recordList = recordList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View satirView;
        satirView = mInflater.inflate(R.layout.record_db_row, null);

        TextView operator = (TextView) satirView.findViewById(R.id.txtDBOperatorName);
        TextView signal = (TextView) satirView.findViewById(R.id.txtDBSignal);
        TextView latitude = (TextView) satirView.findViewById(R.id.txtDBLatitude);
        TextView longitude = (TextView) satirView.findViewById(R.id.txtDBLongitude);

        Record record = recordList.get(position);
        signal.setText("  "+record.getSinyalGucu());
        latitude.setText("  "+record.getEnlem());
        longitude.setText("  "+record.getBoylam());
        operator.setText(" "+record.getOperatorAdi());

        return satirView;
    }

    public int getCount(){
        return recordList.size();
    }

    public Record getItem(int position){
        return recordList.get(position);
    }

    public long getItemId(int position){
        return position;
    }
}
