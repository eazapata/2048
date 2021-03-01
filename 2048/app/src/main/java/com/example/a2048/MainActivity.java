package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2048.DataBase.DataBaseHelper;
import com.example.a2048.DataBase.Score;
import com.example.a2048.game.Game;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, SwipeCallback, View.OnClickListener {

    private ImageView[][] imageViews;
    private int[][] textViewValues;
    private GridLayout grid;
    private SwipeListener swipeListener;
    private TextView scoreTextView;
    private int actualScore;
    private int[][] previousValues = new int[4][4];
    private Button undo;
    private Button newGame;
    private Game game;
    private TextView playerName;
    private DataBaseHelper dataBaseHelper;
    private String player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        swipeListener = new SwipeListener(this, this);
        scoreTextView = (TextView) findViewById(R.id.score_field);
        playerName = (TextView) findViewById(R.id.player_name_field);

        scoreTextView.setText("0");
        dataBaseHelper = new DataBaseHelper(this, "score", null, 1);
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        player = extras.getString("PLAYER KEY");
        playerName.setText(player);
        this.undo = (Button) findViewById(R.id.undo);
        this.undo.setOnClickListener(this);
        this.newGame = (Button) findViewById(R.id.new_game);
        this.newGame.setOnClickListener(this);
        this.textViewValues = new int[4][4];
        this.imageViews = fillArray();
        this.grid = (GridLayout) findViewById(R.id.grid);
        this.grid.setOnTouchListener(this);
        setRandomNumber();
    }

    @Override
    public void onSwipe(Direction direction) {
        copyArray();
        switch (direction) {
            case UP:
                this.actualScore = game.up(imageViews, textViewValues, this.actualScore);
                setRandomNumber();
                break;
            case DOWN:
                this.actualScore = game.down(imageViews, textViewValues, this.actualScore);
                setRandomNumber();
                break;
            case LEFT:
                this.actualScore = game.left(imageViews, textViewValues, this.actualScore);
                setRandomNumber();
                break;
            case RIGHT:
                this.actualScore = game.right(imageViews, textViewValues, this.actualScore);
                setRandomNumber();
                break;
            default:
                System.out.println("Wrong direction");
                break;
        }
        this.scoreTextView.setText(String.valueOf(this.actualScore));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        swipeListener.onTouchEvent(event);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText() == this.undo.getText()) {
            undoMovement();
            System.out.println("Works");
        } else if (((Button) v).getText() == this.newGame.getText()) {
            newGame();
        }
    }

    private void copyArray() {
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                previousValues[i][j] = textViewValues[i][j];
            }
        }
    }

    private ImageView[][] fillArray() {
        ImageView textView1 = (ImageView) findViewById(R.id.one);
        ImageView textView2 = (ImageView) findViewById(R.id.two);
        ImageView textView3 = (ImageView) findViewById(R.id.three);
        ImageView textView4 = (ImageView) findViewById(R.id.four);
        ImageView textView5 = (ImageView) findViewById(R.id.five);
        ImageView textView6 = (ImageView) findViewById(R.id.six);
        ImageView textView7 = (ImageView) findViewById(R.id.seven);
        ImageView textView8 = (ImageView) findViewById(R.id.eight);
        ImageView textView9 = (ImageView) findViewById(R.id.nine);
        ImageView textView10 = (ImageView) findViewById(R.id.ten);
        ImageView textView11 = (ImageView) findViewById(R.id.eleven);
        ImageView textView12 = (ImageView) findViewById(R.id.twelve);
        ImageView textView13 = (ImageView) findViewById(R.id.thirteen);
        ImageView textView14 = (ImageView) findViewById(R.id.fourteen);
        ImageView textView15 = (ImageView) findViewById(R.id.fifteen);
        ImageView textView16 = (ImageView) findViewById(R.id.sixteen);
        ImageView[][] arrayText = {
                {textView1, textView2, textView3, textView4},
                {textView5, textView6, textView7, textView8},
                {textView9, textView10, textView11, textView12},
                {textView13, textView14, textView15, textView16}};
        return arrayText;
    }

    private void newGame() {
        this.textViewValues = null;
        this.textViewValues = new int[4][4];
        this.actualScore = 0;
        this.scoreTextView.setText(String.valueOf(0));
        resetImages();
        setRandomNumber();
    }

    private void resetImages() {
        for (int i = 0; i < imageViews.length; i++) {
            for (int j = 0; j < imageViews[i].length; j++) {
                imageViews[i][j].setImageDrawable(null);
            }
        }
    }

    public void setRandomNumber() {
        boolean play = game.checkEndgame(this.textViewValues);
        if (play) {
            Score score = new Score();
            score.setPlayer(player);
            score.setPlayerScore(this.actualScore);
            dataBaseHelper.insertScore(score);
            finish();
        } else {
            boolean freeSpace = false;
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (textViewValues[x][y] == 0) {
                        freeSpace = true;
                    }
                }
            }
            if (freeSpace) {
                Random random = new Random();
                int pos1 = random.nextInt(4);
                int pos2 = random.nextInt(4);
                int[] numbers = {2, 4};
                int value = numbers[random.nextInt(2)];
                while (this.textViewValues[pos1][pos2] != 0) {
                    pos1 = random.nextInt(4);
                    pos2 = random.nextInt(4);

                }
                if (value == 2) {
                    this.imageViews[pos1][pos2].setImageResource(R.drawable.two_img);
                } else {
                    this.imageViews[pos1][pos2].setImageResource(R.drawable.four);
                }
                this.textViewValues[pos1][pos2] = value;

            }
        }
    }


    private void undoMovement() {
        resetImages();
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                textViewValues[i][j] = previousValues[i][j];
            }
        }
        game.setImage(imageViews, textViewValues);

    }

}