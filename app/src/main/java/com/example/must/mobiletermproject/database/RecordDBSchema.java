package com.example.must.mobiletermproject.database;

/**
 * Created by must on 03.06.2016.
 */
public class RecordDBSchema {
    public static final class RecordTable{
        public static final String name = "record";

        public static final class Cols{
            public static final String STRENGTH = "strength"; //sinyal gücü
            public static final String LATITUTE = "latitute"; //enlem
            public static final String LONGITUTE = "longitute"; //boylam
            public static final String OPERATOR_NAME = "operatorName";
            public static final String TIME = "time";

        }
    }
}
