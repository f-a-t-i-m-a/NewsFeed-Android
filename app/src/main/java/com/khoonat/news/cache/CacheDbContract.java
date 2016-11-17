package com.khoonat.news.cache;

import android.provider.BaseColumns;

public final class CacheDbContract {
    private CacheDbContract() {
    }

    public static abstract class CacheEntry implements BaseColumns {
        public static final String TABLE_NAME = "cache";
        public static final String COLUMN_NAME_CACHE_KEY = "cachekey";
        public static final String COLUMN_NAME_CONTENTS = "contents";
        public static final String COLUMN_NAME_CREATION = "creation";
        public static final String COLUMN_NAME_LAST_ACCESS = "lastaccess";
        public static final String COLUMN_NAME_PERSIST = "persist";

        public static final String SQL_CREATE_TABLE = "" +
                "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_CACHE_KEY + " TEXT, " +
                COLUMN_NAME_CONTENTS + " TEXT, " +
                COLUMN_NAME_CREATION + " INTEGER, " +
                COLUMN_NAME_LAST_ACCESS + " INTEGER, " +
                COLUMN_NAME_PERSIST + " INTEGER, " +
                ")";

        public static final String SQL_DROP_TABLE = "" +
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
