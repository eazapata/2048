package com.example.a2048.DataBase;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2048.R;

import java.util.Comparator;

public class ScoresActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private DataBaseHelper dataBaseHelper;
    private DataBaseAdapter dataBaseAdapter;
    private Spinner spinnerSearch, spinnerScores;
    private EditText nameSearch, scoreSearch;
    private Button sortName, sortScore, showAllScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        this.spinnerSearch = (Spinner) findViewById(R.id.spinner_search);
        this.spinnerSearch.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_options, R.layout.spinner_text_checked);
        adapter.setDropDownViewResource(R.layout.spinner_drop);
        this.spinnerSearch.setAdapter(adapter);
        this.nameSearch = (EditText) findViewById(R.id.name_search);
        this.scoreSearch = (EditText) findViewById(R.id.score_search);

        this.spinnerScores = (Spinner) findViewById(R.id.spinner_score_options);
        this.spinnerScores.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.score_options, R.layout.spinner_text_checked);
        adapter1.setDropDownViewResource(R.layout.spinner_drop);
        spinnerScores.setAdapter(adapter1);

        this.sortName = (Button) findViewById(R.id.sort_by_name);
        this.sortName.setOnClickListener(this);
        this.sortScore = (Button) findViewById(R.id.sort_by_score);
        this.sortScore.setOnClickListener(this);
        this.showAllScores = (Button) findViewById(R.id.show_scores_activity);
        this.showAllScores.setOnClickListener(this);


        this.dataBaseHelper = new DataBaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_score);

        this.dataBaseAdapter = new DataBaseAdapter(this, dataBaseHelper);

        this.recyclerView.setAdapter(dataBaseAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                confirmDelete(viewHolder);
            }
        });
        helper.attachToRecyclerView(recyclerView);


    }

    private void confirmDelete(RecyclerView.ViewHolder viewHolder) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_score);
        dialog.setCanceledOnTouchOutside(false);
        Button yes = dialog.findViewById(R.id.yes_remove);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.delete(dataBaseAdapter.getScoreList().get(viewHolder.getAdapterPosition()));
                dataBaseAdapter.getScoreList().remove(viewHolder.getAdapterPosition());
                dataBaseAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        Button no = dialog.findViewById(R.id.no_remove);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sort_by_name:
                this.dataBaseAdapter.getScoreList().sort(Comparator.comparing(Score::getPlayer, String.CASE_INSENSITIVE_ORDER));
                this.dataBaseAdapter.notifyDataSetChanged();
                break;
            case R.id.sort_by_score:
                this.dataBaseAdapter.getScoreList().sort(Comparator.comparing(Score::getPlayerScore).reversed());
                this.dataBaseAdapter.notifyDataSetChanged();
                break;
            case R.id.show_scores_activity:
                this.dataBaseAdapter.setScoreList(dataBaseHelper.getAllScores());
                this.dataBaseAdapter.notifyDataSetChanged();
                break;
            default:
                System.out.println("View not found.");
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.score_layout_search);
        switch (position) {
            case 0:
                if (this.spinnerSearch.getSelectedItem().toString().equals("Name")) {
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
    protected void onResume() {
        super.onResume();
        this.dataBaseAdapter.setScoreList(dataBaseHelper.getAllScores());
        this.dataBaseAdapter.notifyDataSetChanged();
    }

    public void search(View v) {

        if (spinnerSearch.getSelectedItem().toString().equals("Name")) {
            if (nameSearch.getText().toString().equals("")) {
                Toast.makeText(this, "Name not inserted", Toast.LENGTH_SHORT).show();
            } else {
                this.dataBaseAdapter.setScoreList(dataBaseHelper.getScoresByName(nameSearch.getText().toString()));
                this.dataBaseAdapter.notifyDataSetChanged();
            }

        } else if (spinnerSearch.getSelectedItem().toString().equals("Score")) {
            if (scoreSearch.getText().toString().equals("")) {
                Toast.makeText(this, "Score not inserted", Toast.LENGTH_SHORT).show();
            } else {
                this.dataBaseAdapter.setScoreList(dataBaseHelper.getScoresByScore(
                        nameSearch.getText().toString(),
                        spinnerScores.getSelectedItem().toString(),
                        scoreSearch.getText().toString()));
                this.dataBaseAdapter.notifyDataSetChanged();
            }
        }
    }


}