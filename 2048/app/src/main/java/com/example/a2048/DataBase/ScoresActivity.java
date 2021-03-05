package com.example.a2048.DataBase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2048.R;

import java.util.ArrayList;
import java.util.List;

public class ScoresActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private List<Score> scores;
    private DataBaseHelper dataBaseHelper;
    private DataBaseAdapter dataBaseAdapter;
    private Spinner spinnerSearch, spinnerScores;
    private EditText nameSearch,scoreSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        spinnerSearch = (Spinner) findViewById(R.id.spinner_search);
        spinnerSearch.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearch.setAdapter(adapter);
        nameSearch = (EditText) findViewById(R.id.name_search);
        scoreSearch = (EditText)findViewById(R.id.score_search);

        spinnerScores = (Spinner) findViewById(R.id.spinner_score_options);
        spinnerScores.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.score_options, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerScores.setAdapter(adapter1);

        dataBaseHelper = new DataBaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_score);

        scores = new ArrayList<>();
        loadData();
        dataBaseAdapter = new DataBaseAdapter(scores, this, dataBaseHelper);
        recyclerView.setAdapter(dataBaseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void loadData() {
        scores =  dataBaseHelper.getAllScores();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.score_layout_search);
        switch (position) {
            case 0:
                if(spinnerSearch.getSelectedItem().toString().equals("Name")){
                    layout.setVisibility(View.GONE);
                }
                break;
            case 1:
                layout.setVisibility(View.VISIBLE);
                break;
            default:
                System.out.println("Option not found.");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void search(View v) {

        if (spinnerSearch.getSelectedItem().toString().equals("Name")) {
            scores = dataBaseHelper.getScoresByName(nameSearch.getText().toString());
            dataBaseAdapter.setScoreList(scores);
            dataBaseAdapter.notifyDataSetChanged();
        }else if(spinnerSearch.getSelectedItem().toString().equals("Score")){
            scores = dataBaseHelper.getScoresByScore(nameSearch.getText().toString(),spinnerScores.getSelectedItem().toString(),
                    scoreSearch.getText().toString());
            dataBaseAdapter.setScoreList(scores);
            dataBaseAdapter.notifyDataSetChanged();
        }
    }
}