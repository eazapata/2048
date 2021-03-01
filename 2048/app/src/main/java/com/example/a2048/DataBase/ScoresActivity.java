package com.example.a2048.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2048.R;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Score> scores;
    DataBaseHelper dataBaseHelper;
    DataBaseAdapter dataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_score);
        scores = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(this, "score", null, 1);
        loadData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataBaseAdapter = new DataBaseAdapter(scores, this);
        recyclerView.setAdapter(dataBaseAdapter);


    }

    public void loadData(){
        scores = (ArrayList<Score>) dataBaseHelper.getAllScores();
    }

    public void showData() {
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Score score = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM persona", null);
        while (cursor.moveToNext()) {
            score = new Score();
            score.setId(cursor.getInt(0));
            score.setPlayer(cursor.getString(1));
            score.setCountry(cursor.getString(2));
            score.setPlayerScore(cursor.getInt(3));

          //  personaAdapter.agregarPersona(persona);
        }
    }
}