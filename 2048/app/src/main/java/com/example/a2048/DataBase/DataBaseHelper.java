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

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertScore(Score score){
        if (mWritableDB == null) {
            mWritableDB = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME,score.getPlayer());
        values.put(SCORE,score.getPlayerScore());
        values.put(COUNTRY,score.getCountry());
        mWritableDB.insert(TABLE,null,values);

    }

    public List<Score> getAllScores(){
        List<Score> getAllScores = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String queryAllScores = "SELECT * FROM " + TABLE + " order by " + SCORE +" desc";
        Cursor cursor = db.rawQuery(queryAllScores, null);
        if (cursor.moveToNext()) {
            do {
                int scoreId = cursor.getInt(0);
                String username = cursor.getString(1);
                Integer score = cursor.getInt(2);
                Score newScore = new Score(scoreId, username, score, "asd");
                getAllScores.add(newScore);
            }
            while (cursor.moveToNext());
        }
        else{
            //There aren't scores. No scores will be displayed
        }
        cursor.close();
        db.close();
        return getAllScores;
    }

    public int updateScore(int id, String playerName, String country) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(PLAYER_NAME, playerName);
            mNumberOfRowsUpdated = mWritableDB.update(TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        } catch (Exception e) {
            Log.d(TAG, "UPDATE EXCEPTION: " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }
}

