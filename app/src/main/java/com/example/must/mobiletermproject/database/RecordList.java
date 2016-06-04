package com.example.must.mobiletermproject.database;

import java.io.Serializable;
import java.util.List;

/**
 * Created by must on 05.06.2016.
 */
public class RecordList implements Serializable {
    private List<Record> recordList;

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

}
