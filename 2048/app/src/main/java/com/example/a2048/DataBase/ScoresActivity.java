package com.example.a2048.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2048.R;

import java.util.ArrayList;
import java.util.List;

public class ScoresActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    List<Score> scores;
    DataBaseHelper dataBaseHelper;
    DataBaseAdapter dataBaseAdapter;
    private Button score, player, time, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_score);
        score = (Button) findViewById(R.id.score_query);
        score.setOnClickListener(this);
        player = (Button) findViewById(R.id.name_query);
        player.setOnClickListener(this);
        time = (Button) findViewById(R.id.time_query);
        time.setOnClickListener(this);
        country = (Button) findViewById(R.id.country_query);
        country.setOnClickListener(this);
        scores = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(this, "score", null, 1);
        loadData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataBaseAdapter = new DataBaseAdapter(scores, this,dataBaseHelper);
        recyclerView.setAdapter(dataBaseAdapter);


    }

    public void loadData() {
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

    @Override
    public void onClick(View v) {
        String text = (String) ((Button) v).getText();
        switch (text) {
            case "Score":
                scores = dataBaseHelper.getScoresBy(text);
                dataBaseAdapter.scoreList = scores;
                dataBaseAdapter.notifyDataSetChanged();
                break;
            case "Player":
                dataBaseHelper.getScoresBy(text);
                break;
            default:
                System.out.println("view not found");
        }

    }
}