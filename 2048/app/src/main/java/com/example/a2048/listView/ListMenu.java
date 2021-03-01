package com.example.a2048.listView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.a2048.DataBase.ScoresActivity;
import com.example.a2048.MainActivity;
import com.example.a2048.R;
import com.example.a2048.listView.Item;
import com.example.a2048.listView.ListViewAdapter;

import java.util.ArrayList;

public class ListMenu extends AppCompatActivity  implements AdapterView.OnItemClickListener{
    private ListView listView;
    private ListViewAdapter adapter;
    private ArrayList<Item> activities;
    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        editTextName = (EditText) findViewById(R.id.player_name_menu);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        this.getActivities();
        this.adapter = new ListViewAdapter(this,activities);
        listView.setAdapter(this.adapter);




    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position){
            case 0:
                String player_name = editTextName.getText().toString();
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("PLAYER KEY",player_name);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, ScoresActivity.class);
                startActivity(intent);
                break;
            default:
                Log.d("Switch fail","Activity not found.");
        }
    }

    private void getActivities(){
        this.activities = new ArrayList<>();
        this.activities.add(new Item("Play"));
        this.activities.add(new Item("Manage Scores"));
    }
}