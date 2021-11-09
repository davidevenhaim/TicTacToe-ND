package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int BOARD_SIZE = 3;
    private Button buttons[][] = new Button[BOARD_SIZE][BOARD_SIZE];
    private Button resetScoreBtn, replayGameBtn;
    private TextView p1ScoreTxt, p2ScoreTxt, turnIndicator;
    private boolean xTurn = true;
    private ImageView winnerLine;
    private int p1Score = 0, p2Score = 0, playCount = 0;
    int gameBoard[][] = {
        {0,0,0},
        {0,0,0},
        {0,0,0},
    };
    String canBeDiagonal[] = {"00", "02", "20", "22", "11"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p1ScoreTxt = findViewById(R.id.text_p1_score);
        p2ScoreTxt = findViewById(R.id.text_p2_score);
        turnIndicator = findViewById(R.id.turn_indicator);

        resetScoreBtn = findViewById(R.id.reset_btn);
        replayGameBtn = findViewById(R.id.replay_btn);

        winnerLine = findViewById(R.id.winner_line);

        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                String btnID = "tic_" + i + j;
                int btnRef = getResources().getIdentifier(btnID, "id",getPackageName());
                buttons[i][j] = findViewById(btnRef);
                buttons[i][j].setOnClickListener(this);
            }
        }
        resetScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetScore();
                resetBoard();
            }
        });
        replayGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")) {
            return;
        }
        playCount++;
        String btnID = v.getResources().getResourceEntryName(v.getId());
        String boardPosition[] = btnID.split("_");
        int posX = Character.getNumericValue(boardPosition[1].charAt(0));
        int posY = Character.getNumericValue(boardPosition[1].charAt(1));

        if(xTurn == true) {
            turnIndicator.setText("It's O turn");
            ((Button) v).setText("X");
            gameBoard[posX][posY] = 1;
            xTurn = false;
            if(isWin(posX, posY)) {
                turnIndicator.setText("X WON!");
                p1Score++;
                p1ScoreTxt.setText("" + p1Score);
                replayGameBtn.setText("New Game");
                toggleButton(false);
            }
        } else {
//            turnIndicator.setText("It's X turn");
            ((Button) v).setText("O");
            gameBoard[posX][posY] = 2;
            if(isWin(posX, posY)) {
                turnIndicator.setText("O WON!");
                p2Score++;
                p2ScoreTxt.setText("" + p2Score);
                replayGameBtn.setText("New Game");
                toggleButton(false);
            }
            xTurn = true;
        }
        if(playCount == 9) {
            turnIndicator.setText("It's a DRAW!");
            replayGameBtn.setText("New Game");
        }
    }

    public boolean isWin (int row, int col) {
        int userChoice = gameBoard[row][col];
        int count = 0;

        for (int i = 0; i < gameBoard[row].length; i++) {
            if (gameBoard[row][i] == userChoice) {
                count++;
            }
        }
        if (count == BOARD_SIZE) {
            int imgRef = getResources().getIdentifier("r_" + row, "drawable", getPackageName());
            winnerLine.setImageResource(imgRef);
            winnerLine.setVisibility(View.VISIBLE);
            return true;
        }
        count = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i][col] == userChoice) {
                count++;
            }
        }
        if (count == BOARD_SIZE) {
            int imgRef = getResources().getIdentifier("l_" + col, "drawable", getPackageName());
            winnerLine.setImageResource(imgRef);
            winnerLine.setVisibility(View.VISIBLE);
            return true;
        }
        count = 0;

        // Checking the diagonals.
        if (Arrays.asList(canBeDiagonal).indexOf("" + row + col) > -1) {
            for (int i = 0; i < gameBoard.length; i++) {
                if (gameBoard[i][i] == userChoice) {
                    count++;
                }
            }
            if (count == BOARD_SIZE) {

                winnerLine.setImageResource(R.drawable.d_0);
                winnerLine.setVisibility(View.VISIBLE);
                return true;
            }
            count = 0;
            for (int i = gameBoard.length - 1, j = 0; i >= 0; i--, j++) {
                if (gameBoard[i][j] == userChoice) {
                    count++;
                }
            }
            if (count == BOARD_SIZE) {
                winnerLine.setImageResource(R.drawable.d_2);
                winnerLine.setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;
    }

    public void resetBoard() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                gameBoard[i][j] = 0;
            }
        }
        turnIndicator.setText("It's X turn");
        playCount = 0;
        winnerLine.setVisibility(View.INVISIBLE);
    }

    public void resetScore() {
        p1Score = 0;
        p2Score = 0;
        p1ScoreTxt.setText("" + p1Score);
        p2ScoreTxt.setText("" + p2Score);
        turnIndicator.setText("It's X turn");
    }

    public void toggleButton(boolean isEnable) {
        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setEnabled(isEnable);
            }
        }
    }

}