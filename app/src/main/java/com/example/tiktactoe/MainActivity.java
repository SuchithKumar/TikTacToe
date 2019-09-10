package com.example.tiktactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningCombos = {
            {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}
    };
    boolean isGameActive = true;
    HashSet<Boolean> cellsFilled = new HashSet<Boolean>();
    int clikedCounter = 0;
//    MediaPlayer bgmPlayer ;
    MediaPlayer gameProgressPlayer;


    public void onboardClick(View view){

        gameProgressPlayer = MediaPlayer.create(this,R.raw.buttonclick);
        gameProgressPlayer.start();
//        gameProgressPlayer.stop();

        ImageView imageView = (ImageView) view;
        int tag = Integer.parseInt(imageView.getTag().toString());

        if(gameState[tag]==2 && isGameActive) {
            if (activePlayer == 0) {
                gameState[tag]=0;
                imageView.setTranslationY(-1000);
                imageView.setImageResource(R.drawable.x);
                imageView.animate().rotation(720).translationYBy(1000).setDuration(100);
                activePlayer = 1;
                clikedCounter+=1;

            } else {
                gameState[tag]=1;
                imageView.setTranslationY(-1000);
                imageView.setImageResource(R.drawable.o);
                imageView.animate().rotation(720).translationYBy(1000).setDuration(100);
                activePlayer = 0;
                clikedCounter+=1;
            }
        }

        for(int[] winningCombo : winningCombos) {

            if (gameState[winningCombo[0]] == gameState[winningCombo[1]] && gameState[winningCombo[1]] == gameState[winningCombo[2]] && gameState[winningCombo[0]] != 2) {
                TextView winnerTextView = (TextView) findViewById(R.id.winnerText);
                winnerTextView.setVisibility(View.VISIBLE);
                findViewById(R.id.playAgainButton).setVisibility(View.VISIBLE);

                if (activePlayer == 0) {
                    gameProgressPlayer = MediaPlayer.create(this,R.raw.end);
                    gameProgressPlayer.start();
                    winnerTextView.setText("Winner is O");
                    Toast.makeText(this, "O won!", Toast.LENGTH_SHORT).show();
//                    gameProgressPlayer.stop();
                } else {
                    gameProgressPlayer = MediaPlayer.create(this,R.raw.end);
                    gameProgressPlayer.start();
                    winnerTextView.setText("Winner is X");
                    Toast.makeText(this, "X won!", Toast.LENGTH_SHORT).show();
//                    gameProgressPlayer.stop();
                }
                isGameActive = false;
            }
        }




        if(clikedCounter==9){
            TextView winnerTextView = (TextView) findViewById(R.id.winnerText);
            winnerTextView.setText("Match Drawn!");
            winnerTextView.setVisibility(View.VISIBLE);
            findViewById(R.id.playAgainButton).setVisibility(View.VISIBLE);
            clikedCounter=0;

            gameProgressPlayer = MediaPlayer.create(this,R.raw.end);
            gameProgressPlayer.start();
        }

    }


    public void playAgain(View view){
        TextView winnerTextView = (TextView) findViewById(R.id.winnerText);
        winnerTextView.setVisibility(View.INVISIBLE);
        findViewById(R.id.playAgainButton).setVisibility(View.INVISIBLE);

        GridLayout layout = (GridLayout) findViewById(R.id.boardLayout);
        for(int i=0;i<layout.getChildCount();i++){
            ImageView child = (ImageView) layout.getChildAt(i);
            child.setImageBitmap(null);
        }

        activePlayer = 0;
        for(int j=0; j< gameState.length;j++){
            gameState[j] = 2;
        }

        isGameActive = true;

        clikedCounter = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

    }
}
