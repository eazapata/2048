package com.example.a2048.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
    private static String COUNTRY = "country";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    PLAYER_NAME + " TEXT," +
                    COUNTRY + " TEXT," +
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

    public long count() {
        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }
        return DatabaseUtils.queryNumEntries(mReadableDB, TABLE);
    }

    public int delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            deleted = mWritableDB.delete(TABLE,
                    KEY_ID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d(TAG, "DELETE EXCEPTION! " + e.getMessage());
        }
        return deleted;
    }

    public List<Score> getAllScores() {
        List<Score> getAllScores = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String queryAllScores = "SELECT * FROM " + TABLE + " order by " + SCORE + " desc";
        Cursor cursor = db.rawQuery(queryAllScores, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                String username = cursor.getString(1);
                String country = cursor.getString(2);
                Integer score = cursor.getInt(3);
                Score newScore = new Score(scoreId, username, score, country);
                getAllScores.add(newScore);
            }
            while (cursor.moveToNext());
        } else {
            //There aren't scores. No scores will be displayed
        }
        cursor.close();
        db.close();
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
            cursor.moveToFirst();
            score.setId(cursor.getInt(0));
            score.setPlayer(cursor.getString(1));
            score.setCountry(cursor.getString(2));
            score.setPlayerScore(cursor.getInt(3));

        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return score.getPlayerScore();
        }
    }

    public List<Score> getScoresByName(String name) {
        if (name.equals(null)) {
            name = " ";
        }
        List<Score> getScoresByParam = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE + " where " + PLAYER_NAME + " like '%" + name + "%'";
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
                    String country = cursor.getString(2);
                    Integer score = cursor.getInt(3);
                    Score newScore = new Score(scoreId, username, score, country);
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

    public List<Score> getScoresBy(String order) {
        String getScoresBy = null;
        List<Score> getScoresByParam = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        switch (order) {
            case "Score":
                getScoresBy = "SELECT * FROM " + TABLE + " order by " + SCORE + " asc";
                break;
            case "ByScoreDesc":
                getScoresBy = "SELECT * FROM " + TABLE + " order by " + SCORE + " desc";
                break;
            case "ByUsername":
                getScoresBy = "SELECT * FROM " + TABLE + " order by " + PLAYER_NAME + " desc";
                break;
            case "ByUsernameDesc":
                getScoresBy = "SELECT * FROM " + TABLE + " order by " + PLAYER_NAME;
                break;
            case "ByCountry":
                getScoresBy = "SELECT * FROM " + TABLE + " order by " + COUNTRY + " desc";
                break;
            case "ByCountryDesc":
                getScoresBy = "SELECT * FROM " + TABLE + " order by " + COUNTRY;
                break;
            case "ByDuration":
                getScoresBy = "SELECT * FROM " + TABLE + " order by " + "GAME_DURATION_TABLE" + " desc";
                break;
            case "ByDurationDesc":
                getScoresBy = "SELECT * FROM " + TABLE + " order by " + "GAME_DURATION_TABLE";
                break;
        }
        Cursor cursor = db.rawQuery(getScoresBy, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                String username = cursor.getString(1);
                String country = cursor.getString(2);
                Integer score = cursor.getInt(3);
                Score newScore = new Score(scoreId, username, score, country);
                getScoresByParam.add(newScore);
            }
            while (cursor.moveToNext());
        } else {
            //There aren't scores. No scores will be displayed
        }
        cursor.close();
        // db.close();

        return getScoresByParam;
    }

    public void insertScore(Score score) {
        if (mWritableDB == null) {
            mWritableDB = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME, score.getPlayer());
        values.put(COUNTRY, score.getCountry());
        values.put(SCORE, score.getPlayerScore());
        mWritableDB.insert(TABLE, null, values);

    }

    public Score query(int position) {
        String query = "SELECT * FROM " + TABLE +
                " ORDER BY " + KEY_ID + " DESC " +
                "LIMIT " + position + ",1";
        Cursor cursor = null;
        Score score = new Score();
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            score.setId(cursor.getInt(0));
            score.setPlayer(cursor.getString(1));
            score.setCountry(cursor.getString(2));
            score.setPlayerScore(cursor.getInt(3));

        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return score;
        }
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
            values.put(COUNTRY, score.getCountry());
            values.put(SCORE, score.getPlayerScore());
            mNumberOfRowsUpdated = mWritableDB.update(TABLE, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(score.getId())});

        } catch (Exception e) {
            Log.d(TAG, "UPDATE EXCEPTION: " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }

}

