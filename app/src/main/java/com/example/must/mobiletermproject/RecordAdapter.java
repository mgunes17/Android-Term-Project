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
public class RecordAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Record> recordList;

    public RecordAdapter(Activity activity, List<Record> recordList){
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recordList = recordList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View satirView;
        satirView = mInflater.inflate(R.layout.record_row, null);

        TextView signal = (TextView) satirView.findViewById(R.id.txtSessionSignal);
        TextView latitude = (TextView) satirView.findViewById(R.id.txtSessionLatitude);
        TextView longitude = (TextView) satirView.findViewById(R.id.txtSessionLongitude);

        Record record = recordList.get(position);
        signal.setText("  "+record.getSinyalGucu());
        latitude.setText("  "+record.getEnlem());
        longitude.setText("  "+record.getBoylam());

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
