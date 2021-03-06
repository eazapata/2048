package com.example.a2048.DataBase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2048.R;

import java.util.ArrayList;
import java.util.List;

public class ScoresActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private DataBaseHelper dataBaseHelper;
    private DataBaseAdapter dataBaseAdapter;
    private Spinner spinnerSearch, spinnerScores;
    private EditText nameSearch, scoreSearch;

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
        scoreSearch = (EditText) findViewById(R.id.score_search);

        spinnerScores = (Spinner) findViewById(R.id.spinner_score_options);
        spinnerScores.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.score_options, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerScores.setAdapter(adapter1);

        dataBaseHelper = new DataBaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_score);

        dataBaseAdapter = new DataBaseAdapter(this, dataBaseHelper);
        loadData();

        recyclerView.setAdapter(dataBaseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                dataBaseHelper.delete(dataBaseAdapter.getScoreList().get(viewHolder.getAdapterPosition()));
                dataBaseAdapter.getScoreList().remove(viewHolder.getAdapterPosition());
                dataBaseAdapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(recyclerView);


    }

    public void loadData() {
        dataBaseAdapter.setScoreList(dataBaseHelper.getAllScores());
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.score_layout_search);
        switch (position) {
            case 0:
                if (spinnerSearch.getSelectedItem().toString().equals("Name")) {
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

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.dataBaseAdapter.setScoreList(dataBaseHelper.getAllScores());
        this.dataBaseAdapter.notifyDataSetChanged();

    }

    public void search(View v) {

        if (spinnerSearch.getSelectedItem().toString().equals("Name")) {
            dataBaseAdapter.setScoreList( dataBaseHelper.getScoresByName(nameSearch.getText().toString()));
            dataBaseAdapter.notifyDataSetChanged();

        } else if (spinnerSearch.getSelectedItem().toString().equals("Score")) {
          dataBaseAdapter.setScoreList(dataBaseHelper.getScoresByScore(
                  nameSearch.getText().toString(),
                  spinnerScores.getSelectedItem().toString(),
                  scoreSearch.getText().toString()));
            dataBaseAdapter.notifyDataSetChanged();
        }
    }
}