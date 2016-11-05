package ir.appson.sportfeed.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CacheManager {

    private Context context;
    private CacheDbOpenHelper dbHelper;

    public CacheManager(Context context) {
        this.context = context;
        this.dbHelper = new CacheDbOpenHelper(context);
    }

    public CacheReadResult get(String key) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                CacheDbContract.CacheEntry.COLUMN_NAME_CONTENTS,
                CacheDbContract.CacheEntry.COLUMN_NAME_CREATION
        };
        String selection = CacheDbContract.CacheEntry.COLUMN_NAME_CACHE_KEY + " = ?";
        String[] selectionArgs = {key};

        CacheReadResult result = null;
        Cursor cursor = db.query(CacheDbContract.CacheEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                String resultContents = cursor.getString(cursor.getColumnIndexOrThrow(CacheDbContract.CacheEntry.COLUMN_NAME_CONTENTS));
                long resultCreationMillis = cursor.getLong(cursor.getColumnIndexOrThrow(CacheDbContract.CacheEntry.COLUMN_NAME_CREATION));

                result = new CacheReadResult(resultContents, System.currentTimeMillis() - resultCreationMillis);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        if (result != null)
            refresh(key);

        return result;
    }

    public void refresh(String key) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CacheDbContract.CacheEntry.COLUMN_NAME_LAST_ACCESS, System.currentTimeMillis());

        String selection = CacheDbContract.CacheEntry.COLUMN_NAME_CACHE_KEY + " = ?";
        String[] selectionArgs = {key};

        db.update(CacheDbContract.CacheEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public void set(String key, String contents, boolean persist) {
        remove(key);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CacheDbContract.CacheEntry.COLUMN_NAME_CACHE_KEY, key);
        values.put(CacheDbContract.CacheEntry.COLUMN_NAME_CREATION, System.currentTimeMillis());
        values.put(CacheDbContract.CacheEntry.COLUMN_NAME_LAST_ACCESS, System.currentTimeMillis());
        values.put(CacheDbContract.CacheEntry.COLUMN_NAME_CONTENTS, contents);
        values.put(CacheDbContract.CacheEntry.COLUMN_NAME_PERSIST, persist ? 1 : 0);

        db.insert(CacheDbContract.CacheEntry.TABLE_NAME, null, values);
    }

    public void remove(String key) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = CacheDbContract.CacheEntry.COLUMN_NAME_CACHE_KEY + " = ?";
        String[] selectionArgs = {key};
        db.delete(CacheDbContract.CacheEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void cleanUp(long maxUnusedTimeMillis, long maxCount) {
        // Delete non-persistent entries, older than the specified age
        long minLastAccessTimeToKeep = System.currentTimeMillis() - maxUnusedTimeMillis;
        String selection = CacheDbContract.CacheEntry.COLUMN_NAME_PERSIST + " = 0 AND " +
                CacheDbContract.CacheEntry.COLUMN_NAME_LAST_ACCESS + " < " + minLastAccessTimeToKeep;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(CacheDbContract.CacheEntry.TABLE_NAME, selection, null);

        // TODO: Also delete old items if count exceeds maxCount
    }
}
