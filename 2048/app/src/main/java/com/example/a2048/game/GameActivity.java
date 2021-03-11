package com.example.a2048.game;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import com.example.a2048.DataBase.DataBaseHelper;
import com.example.a2048.DataBase.Score;
import com.example.a2048.R;

import java.util.Random;


public class GameActivity extends AppCompatActivity implements View.OnTouchListener, SwipeCallback, View.OnClickListener {

    private ImageView[][] imageViews;
    private int[][] values;
    private GridLayout grid;
    private SwipeListener swipeListener;
    private TextView scoreTextView, maxScoreTextview;
    private int actualScore;
    private int previousScore;
    private int[][] previousValues = new int[4][4];
    private Button undo;
    private Button newGame;
    private GameLogic game;
    private TextView playerName;
    private DataBaseHelper dataBaseHelper;
    private String player;
    private Chronometer chronometer;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
        this.mediaPlayer = MediaPlayer.create(this,R.raw.music);
        this.mediaPlayer.setLooping(true);
        this.mediaPlayer.start();
        this.game = new GameLogic();
        this.swipeListener = new SwipeListener(this, this);
        this.scoreTextView = (TextView) findViewById(R.id.score_field);
        this.maxScoreTextview = (TextView) findViewById(R.id.max_score_field);
        this.playerName = (TextView) findViewById(R.id.player_name_field);
        this.dataBaseHelper = new DataBaseHelper(this);
        this.chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setTypeface(ResourcesCompat.getFont(this, R.font.coiny));
        this.chronometer.start();
        setMaxScore();
        this.scoreTextView.setText("0");
        setPlayerName();
        this.undo = (Button) findViewById(R.id.undo);
        this.undo.setOnClickListener(this);
        this.newGame = (Button) findViewById(R.id.new_game);
        this.newGame.setOnClickListener(this);
        this.values = new int[4][4];
        this.imageViews = fillArray();
        this.grid = (GridLayout) findViewById(R.id.grid);
        this.grid.setOnTouchListener(this);
        setRandomNumber();
        copyArray();
    }

    /**
     * Method to ask the player if he wants to starts a new game with a custom dialog
     */
    private void askNewGame() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_new_game);
        dialog.setCanceledOnTouchOutside(false);
        Button yes = dialog.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
                dialog.dismiss();
            }
        });
        Button no = dialog.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Method to set the backup array for the undo button
     */
    private void copyArray() {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                previousValues[i][j] = values[i][j];
            }
        }

    }

    /**
     * Method to fill the array of imageViews
     *
     * @return an array with the imageviews
     */
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

    /**
     * Method that reset the content of the activity
     */
    private void newGame() {
        this.values = null;
        this.values = new int[4][4];
        this.actualScore = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        chronometer.start();
        this.scoreTextView.setText(String.valueOf(this.actualScore));
        resetImages();
        setRandomNumber();
        copyArray();
    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText() == this.undo.getText()) {
            undoMovement();
        } else if (((Button) v).getText() == this.newGame.getText()) {
            askNewGame();
        }
    }

    @Override
    public void onSwipe(Direction direction) {
        copyArray();
        this.previousScore = this.actualScore;
        switch (direction) {
            case UP:
                this.actualScore = game.up(imageViews, values, this.actualScore);
                break;
            case DOWN:
                this.actualScore = game.down(imageViews, values, this.actualScore);
                break;
            case LEFT:
                this.actualScore = game.left(imageViews, values, this.actualScore);
                break;
            case RIGHT:
                this.actualScore = game.right(imageViews, values, this.actualScore);
                break;
            default:
                System.out.println("Wrong direction");
                break;
        }
        this.scoreTextView.setText(String.valueOf(this.actualScore));
        if (game.isMovementSuccessful()) {
            setRandomNumber();
        } else {
            if (game.finished(values)) {
                setGameOver();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        swipeListener.onTouchEvent(event);
        return true;
    }

    /**
     * Method to set all the imageViews value to null.
     */
    private void resetImages() {
        for (int i = 0; i < imageViews.length; i++) {
            for (int j = 0; j < imageViews[i].length; j++) {
                imageViews[i][j].setImageDrawable(null);
            }
        }
    }

    /**
     * Method to save the score with the player info
     */
    private void savePlayerData() {
        chronometer.stop();
        Score score = new Score();
        score.setPlayer(player);
        score.setPlayerScore(this.actualScore);
        score.setTime(chronometer.getText().toString());
        dataBaseHelper.insertScore(score);
    }

    /**
     * Method to create a dialog for the game over.
     */
    private void setGameOver() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.game_over);
        dialog.setCanceledOnTouchOutside(false);
        Button retry = dialog.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlayerData();
                newGame();
                dialog.dismiss();
            }
        });
        Button exit = dialog.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlayerData();
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    /**
     * Method to set the top score reached
     */
    private void setMaxScore() {
        int score = dataBaseHelper.getMaxScore();
        maxScoreTextview.setText(String.valueOf(score));
    }

    /**
     * Method to set the player name in the textview
     */
    private void setPlayerName() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        player = extras.getString("PLAYER KEY");
        if (player.equals("")) {
            player = "Player";
        }
        playerName.setText(player);

    }

    /**
     * Method to set a new number on the grid
     */
    public void setRandomNumber() {
        Random random = new Random();
        int pos1 = random.nextInt(4);
        int pos2 = random.nextInt(4);
        int[] numbers = {2, 2, 2, 4};
        int value = numbers[random.nextInt(numbers.length)];
        while (this.values[pos1][pos2] != 0) {
            pos1 = random.nextInt(4);
            pos2 = random.nextInt(4);
        }
        if (value == 2) {
            this.imageViews[pos1][pos2].setImageResource(R.drawable.two_img);
        } else {
            this.imageViews[pos1][pos2].setImageResource(R.drawable.four);
        }
        this.values[pos1][pos2] = value;
    }

    /**
     * Method to undo a movement
     */
    private void undoMovement() {
        resetImages();
        this.actualScore = this.previousScore;
        scoreTextView.setText(String.valueOf(this.previousScore));
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                values[i][j] = previousValues[i][j];
            }
        }
        game.setImage(imageViews, values);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mediaPlayer.pause();
    }

}