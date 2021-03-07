package com.example.a2048.listView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2048.DataBase.Score;
import com.example.a2048.DataBase.ScoresActivity;
import com.example.a2048.R;
import com.example.a2048.game.MainActivity;

import java.util.ArrayList;

public class ListMenu extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ListViewAdapter adapter;
    private ArrayList<Item> activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

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
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        this.getActivities();
        this.adapter = new ListViewAdapter(this, activities);
        listView.setAdapter(this.adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                setPlayerName();
                break;
            case 1:
                Intent intent;
                intent = new Intent(this, ScoresActivity.class);
                startActivity(intent);
                break;
            default:
                Log.d("Switch fail", "Activity not found.");
        }
    }

    private void getActivities() {
        this.activities = new ArrayList<>();
        this.activities.add(new Item("Play"));
        this.activities.add(new Item("Manage Scores"));
    }

    private void setPlayerName(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_name);
        final EditText name = dialog.findViewById(R.id.name_user);

        Button play = dialog.findViewById(R.id.play_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(name.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    private void startGame(String playerName) {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("PLAYER KEY", playerName);
        startActivity(intent);
    }
}