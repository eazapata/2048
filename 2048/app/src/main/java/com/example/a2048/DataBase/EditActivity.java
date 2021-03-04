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

    private EditText zoneEdit, playerNameEdit;
    private Button show, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        zoneEdit = (EditText) findViewById(R.id.zone_edit);
        playerNameEdit = (EditText) findViewById(R.id.player_name_edit);
        show = (Button) findViewById(R.id.show_edit);
        save = (Button) findViewById(R.id.save_edit);
        save.setOnClickListener(this);
        show.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        playerNameEdit.setText(extras.getString("PLAYER NAME"));
        zoneEdit.setText(extras.getString("PLAYER Country"));

    }

    @Override
    public void onClick(View v) {
        String text = (String) ((Button) v).getText();
        switch (text) {
            case "Save":
                Score score = new Score();
                score.setPlayer(String.valueOf(playerNameEdit.getText()));
                score.setCountry(String.valueOf(zoneEdit.getText()));
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"score",null,1);
                dataBaseHelper.insertScore(score);
                Toast.makeText(this,"Registro guardado",Toast.LENGTH_SHORT).show();
                break;
            case "Show":

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ((Button) v).getText());
        }
    }
}