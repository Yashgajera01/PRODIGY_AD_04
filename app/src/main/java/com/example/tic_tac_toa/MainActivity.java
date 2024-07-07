package com.example.tic_tac_toa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
public class MainActivity extends AppCompatActivity {

    //    State meanings:
    //    0 - X
    //    1 - O
    //    2 - Null

    public static int counter = 0;
    boolean gameActive = true;
    int activePlayer = 0;

    int[] gameState = {2, 2 , 2, 2, 2, 2, 2, 2, 2};
    int[][] winPositions = {{0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}};
    TextView status;
    Button rst;

    public void playerTap(View view)
    {
        // Update the status bar for winner announcement
        status = findViewById(R.id.status);

        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        if(!gameActive){
            gameReset(view);
        }

        if(gameState[tappedImage] == 2 && gameActive) {
            counter++;
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x_bg);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);
                status.setText("O's Turn - Tap to play");
            } else {
                img.setImageResource(R.drawable.o_bg);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);
                status.setText("X's Turn - Tap to play");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }

        // Check if any player has won
        if(counter>4) {
            for (int[] winPosition : winPositions)
            {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                        gameState[winPosition[1]] == gameState[winPosition[2]] &&
                        gameState[winPosition[0]] != 2) {

                    // Somebody has won! - Find out who!
                    gameActive = false;
                    if (gameState[winPosition[0]] == 0) {

                        status.setText("X Has Won");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                gameReset(view);
                            }
                        },3000);
                    }
                    else {
                        status.setText("O Has Won");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                gameReset(view);
                            }
                        },3000);

                    }
                }


            }
            // set the status if the match draw
            if (counter == 9)
            {
                TextView status = findViewById(R.id.status);
                status.setText("Match Draw");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gameReset(view);
                    }
                },3000);
            }
        }
    }

    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        counter = 0;
        Arrays.fill(gameState, 2);

        status = findViewById(R.id.status);
        status.setText("X's Turn - Tap to play");

        ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rst = findViewById(R.id.rst);

        rst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameReset(v);
            }
        });
    }
}