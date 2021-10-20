package com.jagsau.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;

    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    private boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPosition = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8},{2,4,6}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.ResetGame);

        for(int i =0; i<9;i++){
            String buttonID = "btn_"+i;
            int resourceID = getResources().getIdentifier(buttonID,"id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v){
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring((buttonID.length() - 1), buttonID.length()));

        if(activePlayer){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#2b8074"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;
        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount ++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Wins!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoScoreCount ++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Wins!", Toast.LENGTH_SHORT).show();
                playAgain();
            }

        } else if (roundCount == 9){
            Toast.makeText(this, "Sorry! No winner!", Toast.LENGTH_SHORT).show();
            playAgain();
        } else {
            activePlayer = !activePlayer;
        }
    }

    public boolean checkWinner(){
        boolean winner = false;

        for(int[] pos: winningPosition){
            if(gameState[pos[0]] == gameState[pos[1]]
            && gameState[pos[1]] == gameState[pos[2]]
            && gameState[pos[0]] != 2){
                winner = true;
            }
        }

        return winner;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        roundCount = 0;
        activePlayer = true;

        for(int i = 0; i<9; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}