package com.example.akhileshlamba.sample;

import android.provider.BaseColumns;

/**
 * Created by akhileshlamba on 29/3/18.
 */

public class ElectricityContract {

    private ElectricityContract(){}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "ELECTRICITY_USAGE";
        public static final String COLUMN_NAME_ID = "USAGE_ID";
        public static final String COLUMN_NAME_RESID = "RESID";
        public static final String COLUMN_NAME_AC = "AC_USAGE";
        public static final String COLUMN_NAME_WM = "WASHINGMACHINE_USAGE";
        public static final String COLUMN_NAME_FR = "FRIDGE_USAGE";
        public static final String COLUMN_NAME_TEMP = "TEMPERATURE";
        public static final String COLUMN_NAME_DATE = "TEMPERATURE";
        public static final String COLUMN_NAME_HOUR = "TEMPERATURE";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_RESID + " INTEGER," +
                    FeedEntry.COLUMN_NAME_AC + " DOUBLE," +
                    FeedEntry.COLUMN_NAME_WM + " DOUBLE," +
                    FeedEntry.COLUMN_NAME_FR + " DOUBLE," +
                    FeedEntry.COLUMN_NAME_TEMP + " DOUBLE," +
                    FeedEntry.COLUMN_NAME_DATE + " DATE," +
                    FeedEntry.COLUMN_NAME_HOUR + " INTEGER)";
}

