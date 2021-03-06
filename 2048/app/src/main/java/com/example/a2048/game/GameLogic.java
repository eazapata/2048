package com.example.a2048.game;

import android.widget.ImageView;

import com.example.a2048.R;


public class GameLogic {
    private boolean movementSuccessful;

    public boolean isMovementSuccessful() {
        return movementSuccessful;
    }

    // METHODS FOR MOVE THE NUMBERS OF THE GRID
    private void moveUp(ImageView[][] imageViews, int[][] textViewValues, int score) {
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
    }

    private void moveDown(ImageView[][] imageViews, int[][] textViewValues, int score) {
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
    }

    private void moveLeft(ImageView[][] imageViews, int[][] textViewValues, int score) {
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
    }

    private void moveRight(ImageView[][] imageViews, int[][] textViewValues) {
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
    }

    //METHODS TO ADD
    public int up(ImageView[][] imageViews, int[][] textViewValues, int score) {
        this.movementSuccessful = false;
        moveUp(imageViews, textViewValues, score);
        for (int i = 1; i < textViewValues.length; i++) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                if (textViewValues[i][j] != 0 && textViewValues[i - 1][j] == textViewValues[i][j]) {
                    textViewValues[i - 1][j] += textViewValues[i][j];
                    score += textViewValues[i - 1][j];
                    textViewValues[i][j] = 0;
                    this.movementSuccessful = true;
                }
            }
        }
        moveUp(imageViews, textViewValues, score);
        setImage(imageViews, textViewValues);
        return score;
    }

    public int down(ImageView[][] imageViews, int[][] textViewValues, int score) {
        this.movementSuccessful = false;
        moveDown(imageViews, textViewValues, score);
        for (int i = textViewValues.length - 2; i >= 0; i--) {
            for (int j = 0; j < textViewValues[i].length; j++) {
                if (textViewValues[i][j] != 0 && textViewValues[i + 1][j] == textViewValues[i][j]) {
                    textViewValues[i + 1][j] += textViewValues[i][j];
                    score += textViewValues[i + 1][j];
                    textViewValues[i][j] = 0;
                    this.movementSuccessful = true;
                }
            }

        }
        moveDown(imageViews, textViewValues, score);
        setImage(imageViews, textViewValues);
        return score;
    }

    public int left(ImageView[][] imageViews, int[][] textViewValues, int score) {
        this.movementSuccessful = false;
        moveLeft(imageViews, textViewValues, score);
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = 1; j < textViewValues[i].length; j++) {
                if (textViewValues[i][j] != 0 && textViewValues[i][j - 1] == textViewValues[i][j]) {
                    textViewValues[i][j - 1] += textViewValues[i][j];
                    score += textViewValues[i][j - 1];
                    textViewValues[i][j] = 0;
                    this.movementSuccessful = true;
                }
            }
        }
        moveLeft(imageViews, textViewValues, score);
        setImage(imageViews, textViewValues);
        return score;
    }

    public int right(ImageView[][] imageViews, int[][] textViewValues, int score) {
        this.movementSuccessful = false;
        moveRight(imageViews, textViewValues);
        for (int i = 0; i < textViewValues.length; i++) {
            for (int j = textViewValues.length - 2; j >= 0; j--) {
                if (textViewValues[i][j] != 0 && textViewValues[i][j + 1] == textViewValues[i][j]) {
                    textViewValues[i][j + 1] += textViewValues[i][j];
                    score += textViewValues[i][j + 1];
                    textViewValues[i][j] = 0;
                    this.movementSuccessful = true;
                }
            }
        }
        moveRight(imageViews, textViewValues);
        setImage(imageViews, textViewValues);
        return score;
    }

    /**
     * Method to check if the game is finished, checking if there are any free space
     * and after this checks y some of them can be added
     * @param values
     * @return
     */
    public boolean finished(int[][] values) {
        boolean finished = true;
        for (int i = 0; i < values.length ; i++) {
            for (int j = 0; j < values[i].length ; j++) {
                if(values[i][j] == 0){
                    finished = false;
                    break;
                }
            }
        }
        if(finished){
            for (int i = 1; i < values.length - 1; i++) {
                for (int j = 1; j < values[i].length - 1; j++) {
                    if ((values[i - 1][j] == values[i][j]) ||
                            (values[i + 1][j] == values[i][j]) ||
                            (values[i][j - 1] == values[i][j]) ||
                            (values[i][j + 1] == values[i][j]) ||
                            (values[i - 1][j - 1] == values[i][j - 1]) ||
                            (values[i + 1][j + 1] == values[i][j + 1])) {
                        finished = false;
                        break;
                    }
                }
            }
        }
        return finished;
    }

    /**
     * Method to set a drawable to the imageviews depending of the values to the array of int
     * @param imageViews
     * @param imageViewsValues
     */
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
                    case 4096:
                        imageViews[i][j].setImageDrawable(null);
                        break;
                    default:
                        System.out.println("Number no found.");
                }
            }
        }
    }
}
