package com.example.a2048.DataBase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2048.R;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText zoneEdit, playerNameEdit,scoreEdit,idEdit;
    private Button  save;
    private DataBaseHelper dataBaseHelper;
    private DataBaseAdapter dataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        idEdit = (EditText)findViewById(R.id.player_id_edit);
        idEdit.setEnabled(false);
        playerNameEdit = (EditText) findViewById(R.id.player_name_edit);
        zoneEdit = (EditText) findViewById(R.id.time_edit);
        scoreEdit = (EditText) findViewById(R.id.player_score_edit);
        scoreEdit.setEnabled(false);
        save = (Button) findViewById(R.id.save_edit);
        save.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        idEdit.setText(String.valueOf(extras.getInt("KEY ID")));
        playerNameEdit.setText(extras.getString("PLAYER NAME"));

        zoneEdit.setText(extras.getString("PLAYER COUNTRY"));
        scoreEdit.setText(String.valueOf(extras.getInt("PLAYER SCORE")));

        dataBaseHelper = new DataBaseHelper(this);
        dataBaseAdapter = (DataBaseAdapter) extras.get("ADAPTER");
    }

    @Override
    public void onClick(View v) {
        String text = (String) ((Button) v).getText();
        switch (text) {
            case "Save":
                Score score = new Score();
                score.setId(Integer.parseInt(idEdit.getText().toString()));
                score.setPlayer(String.valueOf(playerNameEdit.getText()));
                score.setTime(String.valueOf(zoneEdit.getText()));
                score.setPlayerScore(Integer.parseInt(scoreEdit.getText().toString()));
                dataBaseHelper.update(score);
                Toast.makeText(this,"Registro guardado",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ((Button) v).getText());
        }
    }
}