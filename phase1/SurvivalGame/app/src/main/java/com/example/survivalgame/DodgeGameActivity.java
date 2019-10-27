package com.example.survivalgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;

public class DodgeGameActivity extends AppCompatActivity {

    static int HEIGHT, WIDTH;
    private DodgeGameView gameView;
    private int prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        HEIGHT = size.y;
        WIDTH = size.x;
        gameView = new DodgeGameView(this);
        setContentView(gameView);
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_MOVE) {

            gameView.player.setxSpeed((int)((event.getX() - gameView.player.getX())/6));
            int spdY = (int)((event.getY() - 300 - gameView.player.getY())/15);
            if(spdY > 20){
                spdY = 20;
            }else if(spdY > 0 && spdY < 8){
                spdY = 8;
            }else if(spdY < 0 && spdY > -8){
                spdY = -8;
            }else if(spdY < -20){
                spdY = -20;
            }
            gameView.player.setySpeed(spdY);
        }
        gameView.invalidate();
        return super.onTouchEvent(event);
    }
}
