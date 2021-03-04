package com.example.a2048.game;

import android.widget.ImageView;

import com.example.a2048.R;



public class Game {
    private boolean movementSuccessful;
    private boolean firstMovement;

    public boolean isFirstMovement() {
        return firstMovement;
    }

    public void setFirstMovement(boolean firstMovement) {
        this.firstMovement = firstMovement;
    }

    public boolean isMovementSuccessful() {
        return movementSuccessful;
    }

    public void setMovementSuccessful(boolean movementSuccessful) {
        this.movementSuccessful = movementSuccessful;
    }

    public int up(ImageView[][] imageViews, int[][] textViewValues, int score) {
        this.movementSuccessful = false;
        for (int i = 1; i < textViewValues.length; i++) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                imageViews[i][j].setImageDrawable(null);
                for (int k = i; k > 0; k--) {
                    if (textViewValues[k][j] != 0 && textViewValues[k - 1][j] == 0) {
                        textViewValues[k - 1][j] = textViewValues[k][j];
                        textViewValues[k][j] = 0;
                        this.movementSuccessful = true;
                    }
                }
            }
        }
        for (int i = 1; i < textViewValues.length; i++) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                if (textViewValues[i][j] != 0 && textViewValues[i - 1][j] == textViewValues[i][j]) {
                    textViewValues[i - 1][j] += textViewValues[i][j];
                    score +=   textViewValues[i - 1][j];
                    textViewValues[i][j] = 0;
                    this.movementSuccessful = true;
                    this.firstMovement = false;
                }
            }
        }
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                for (int k = i; k > 0; k--) {
                    if (textViewValues[k][j] != 0 && textViewValues[k - 1][j] == 0) {
                        textViewValues[k - 1][j] = textViewValues[k][j];
                        textViewValues[k][j] = 0;
                        this.movementSuccessful = true;
                    }
                }
            }
        }
        setImage(imageViews, textViewValues);
        return score;
    }

    public int down(ImageView[][] imageViews, int[][] textViewValues, int score) {
        this.movementSuccessful = false;
        for (int i = textViewValues.length - 2; i >= 0; i--) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                imageViews[i][j].setImageDrawable(null);
                for (int k = i; k < textViewValues.length - 1; k++) {
                    if (textViewValues[k][j] != 0 && textViewValues[k + 1][j] == 0) {
                        textViewValues[k + 1][j] = textViewValues[k][j];
                        textViewValues[k][j] = 0;
                        this.movementSuccessful = true;
                    }
                }
            }
        }
        for (int i = textViewValues.length - 2; i >= 0; i--) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                if (textViewValues[i][j] != 0 && textViewValues[i + 1][j] == textViewValues[i][j]) {
                    textViewValues[i + 1][j] += textViewValues[i][j];
                    score += textViewValues[i + 1][j];
                    textViewValues[i][j] = 0;
                    this.movementSuccessful = true;
                    this.firstMovement = false;
                }
            }

        }
        for (int i = textViewValues.length - 2; i >= 0; i--) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                for (int k = i; k < textViewValues.length - 1; k++) {
                    if (textViewValues[k][j] != 0 && textViewValues[k + 1][j] == 0) {
                        textViewValues[k + 1][j] = textViewValues[k][j];
                        textViewValues[k][j] = 0;
                        this.movementSuccessful = true;
                    }
                }
            }
        }
        setImage(imageViews, textViewValues);
        return score;
    }

    public int left(ImageView[][] imageViews, int[][] textViewValues, int score) {
        this.movementSuccessful = false;
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = 1; j < textViewValues[i].length; j++) {
                imageViews[i][j].setImageDrawable(null);
                for (int k = j; k > 0; k--) {
                    if (textViewValues[i][k] != 0 && textViewValues[i][k - 1] == 0) {
                        textViewValues[i][k - 1] = textViewValues[i][k];
                        textViewValues[i][k] = 0;
                        this.movementSuccessful = true;
                    }
                }
            }
        }
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = 1; j < textViewValues[i].length; j++) {
                if (textViewValues[i][j] != 0 && textViewValues[i][j - 1] == textViewValues[i][j]) {
                    textViewValues[i][j - 1] += textViewValues[i][j];
                    score +=textViewValues[i][j - 1];
                    textViewValues[i][j] = 0;
                    this.movementSuccessful = true;
                    this.firstMovement = false;
                }
            }
        }
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = 1; j < textViewValues[i].length; j++) {
                for (int k = j; k > 0; k--) {
                    if (textViewValues[i][k] != 0 && textViewValues[i][k - 1] == 0) {
                        textViewValues[i][k - 1] = textViewValues[i][k];
                        textViewValues[i][k] = 0;
                        this.movementSuccessful = true;
                    }
                }
            }
        }
        setImage(imageViews, textViewValues);
        return score;
    }



    public int right(ImageView[][] imageViews, int[][] textViewValues, int score) {
        this.movementSuccessful = false;
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = textViewValues.length - 2; j >= 0; j--) {
                imageViews[i][j].setImageDrawable(null);
                for (int k = j; k < textViewValues.length - 1; k++) {
                    if (textViewValues[i][k] != 0 && textViewValues[i][k + 1] == 0) {
                        textViewValues[i][k + 1] = textViewValues[i][k];
                        textViewValues[i][k] = 0;
                        this.movementSuccessful = true;
                    }
                }
            }
        }
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = textViewValues.length - 2; j >= 0; j--) {
                if (textViewValues[i][j] != 0 && textViewValues[i][j + 1] == textViewValues[i][j]) {
                    textViewValues[i][j + 1] += textViewValues[i][j];
                    score += textViewValues[i][j + 1];
                    textViewValues[i][j] = 0;
                    this.movementSuccessful = true;
                    this.firstMovement = false;
                }
            }
        }
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = textViewValues.length - 2; j >= 0; j--) {
                for (int k = j; k < textViewValues.length - 1; k++) {
                    if (textViewValues[i][k] != 0 && textViewValues[i][k + 1] == 0) {
                        textViewValues[i][k + 1] = textViewValues[i][k];
                        textViewValues[i][k] = 0;
                        this.movementSuccessful = true;
                    }
                }
            }
        }
        setImage(imageViews, textViewValues);
        return score;
    }

    public boolean checkGrid(int[][] values){
        boolean endGame = true;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (values[i][j] == 0) {
                    endGame = false;
                    break;
                }
            }
        }
        return endGame;
    }

    public boolean checkEndgame(int[][] values) {
        boolean endGame = true;

        if (endGame) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if ((i > 0 && values[i - 1][j] == values[i][j]) ||
                            (i < 3 && values[i + 1][j] == values[i][j]) ||
                            (j > 0 && values[i][j - 1] == values[i][j]) ||
                            (j < 3 && values[i][j + 1]== values[i][j])) {
                        endGame = false;
                    }
                }
            }
        }
        return endGame;
    }

    public void setImage(ImageView[][] imageViews, int[][] imageViewsValues) {

        for (int i = 0; i < imageViews.length; i++) {
            for (int j = 0; j < imageViews[i].length; j++) {
                switch (imageViewsValues[i][j]) {
                    case 2:
                        imageViews[i][j].setImageResource(R.drawable.two_img);
                        break;
                    case 4:
                        imageViews[i][j].setImageResource(R.drawable.four);
                        break;
                    case 8:
                        imageViews[i][j].setImageResource(R.drawable.eight);
                        break;
                    case 16:
                        imageViews[i][j].setImageResource(R.drawable.sixteen);
                        break;
                    case 32:
                        imageViews[i][j].setImageResource(R.drawable.thirty);
                        break;
                    case 64:
                        imageViews[i][j].setImageResource(R.drawable.sixty);
                        break;
                    case 128:
                        imageViews[i][j].setImageResource(R.drawable.hundred);
                        break;
                    case 256:
                        imageViews[i][j].setImageResource(R.drawable.two_hundred);
                        break;
                    case 512:
                        imageViews[i][j].setImageResource(R.drawable.five_hundred);
                        break;
                    case 1024:
                        imageViews[i][j].setImageResource(R.drawable.thousand);
                        break;
                    case 2048:
                        imageViews[i][j].setImageResource(R.drawable.two_thousand);
                        break;
                }
            }
        }
    }
}
