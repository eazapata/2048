package com.example.a2048.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "score";
    private static String TABLE = "score";
    private static String KEY_ID = "id";
    private static String PLAYER_NAME = "playerName";
    private static String SCORE = "score";
    private static String TIME = "time";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    PLAYER_NAME + " TEXT," +
                    TIME + " TEXT," +
                    SCORE + " INTEGER);";
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void delete(Score score) {
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
             mWritableDB.delete(TABLE,KEY_ID + " = ? ", new String[]{String.valueOf(score.getId())});
        } catch (Exception e) {
            Log.d(TAG, "DELETE EXCEPTION! " + e.getMessage());
        }
    }

    public List<Score> getAllScores() {
        List<Score> getAllScores = new ArrayList<>();

        String queryAllScores = "SELECT * FROM " + TABLE + " order by " + SCORE + " desc";
        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }
        Cursor cursor = mReadableDB.rawQuery(queryAllScores, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                String username = cursor.getString(1);
                String time = cursor.getString(2);
                Integer score = cursor.getInt(3);
                Score newScore = new Score(scoreId, username, score, time);
                getAllScores.add(newScore);
            }
            while (cursor.moveToNext());
        } else {
        }
        cursor.close();
        return getAllScores;
    }

    public int getMaxScore() {
        String query = "SELECT * FROM " + TABLE + " order by " + SCORE + " desc";
        Cursor cursor = null;
        Score score = new Score();
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
            if (cursor.moveToNext()) {
                cursor.moveToFirst();
                score.setId(cursor.getInt(0));
                score.setPlayer(cursor.getString(1));
                score.setTime(cursor.getString(2));
                score.setPlayerScore(cursor.getInt(3));
            }
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return score.getPlayerScore();
        }
    }

    public List<Score> getScoresByName(String name) {
        List<Score> getScoresByParam = new ArrayList<>();
        String query = "SELECT * " +
                "FROM " + TABLE +
                " where " + PLAYER_NAME + " like '%" + name + "%' order by " + PLAYER_NAME + " desc";
        Cursor cursor = null;
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
            if (cursor.moveToNext()) {
                do {
                    int scoreId = cursor.getInt(0);
                    String username = cursor.getString(1);
                    String time = cursor.getString(2);
                    Integer score = cursor.getInt(3);
                    Score newScore = new Score(scoreId, username, score, time);
                    getScoresByParam.add(newScore);
                }
                while (cursor.moveToNext());
            } else {
                //There aren't scores. No scores will be displayed
            }
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return getScoresByParam;
        }

    }

    public List<Score> getScoresByScore(String name, String sign, String value) {
        List<Score> getScoresByParam = new ArrayList<>();
        if(Integer.parseInt(value) > 0){

            String query = "SELECT * " +
                    "FROM " + TABLE +
                    " where " + PLAYER_NAME + " like '%" + name + "%' and " +
                    SCORE + " " + sign + " " + value + " order by " + SCORE + " desc";
            Cursor cursor = null;
            try {
                if (mReadableDB == null) {
                    mReadableDB = getReadableDatabase();
                }
                cursor = mReadableDB.rawQuery(query, null);
                if (cursor.moveToNext()) {
                    do {
                        int scoreId = cursor.getInt(0);
                        String username = cursor.getString(1);
                        String time = cursor.getString(2);
                        Integer score = cursor.getInt(3);
                        Score newScore = new Score(scoreId, username, score, time);
                        getScoresByParam.add(newScore);
                    }
                    while (cursor.moveToNext());
                } else {
                    //There aren't scores. No scores will be displayed
                }
            } catch (Exception e) {
                Log.d(TAG, "EXCEPTION! " + e);
            } finally {
                cursor.close();
                return getScoresByParam;
            }
        }
        return  getScoresByParam;
    }

    public void insertScore(Score score) {
        if (mWritableDB == null) {
            mWritableDB = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME, score.getPlayer());
        values.put(TIME, score.getTime());
        values.put(SCORE, score.getPlayerScore());
        mWritableDB.insert(TABLE, null, values);

    }

    public int update(Score score) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(KEY_ID, score.getId());
            values.put(PLAYER_NAME, score.getPlayer());
            values.put(TIME, score.getTime());
            values.put(SCORE, score.getPlayerScore());
            mNumberOfRowsUpdated = mWritableDB.update(TABLE, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(score.getId())});

        } catch (Exception e) {
            Log.d(TAG, "UPDATE EXCEPTION: " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }
}

