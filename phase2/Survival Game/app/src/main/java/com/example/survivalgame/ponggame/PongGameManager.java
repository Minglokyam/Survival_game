package com.example.survivalgame.ponggame;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.example.survivalgame.User;

import java.util.ArrayList;
import java.util.List;

class PongGameManager {
  private BallFactory ballFactory = new BallFactory();
  private RectPaddleFactory rectPaddleFactory = new RectPaddleFactory();
  /** The screen width */
  private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

  /** The screen height */
  private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

  private RectPaddle rectPaddle;

  private List<Movable> movableList = new ArrayList<>();

  PongGameManager(User user) {
    Ball ball =
        ballFactory.createBall(
                this,
                10,
                screenWidth / 2,
                screenHeight / 2,
                screenWidth / 3,
                -screenHeight / 3, user
        );
    movableList.add(ball);
    rectPaddle =
        rectPaddleFactory.createRectPaddle(
            this,
            screenWidth * 2 / 9,
            screenWidth / 4,
            screenHeight / 25,
            screenWidth / 6,
            screenHeight * 7 / 8
        );
    movableList.add(ball);
  }

  int getScreenWidth() {
    return this.screenWidth;
  }

  int getScreenHeight() {
    return this.screenHeight;
  }

  RectPaddle getRectPaddle() {
    return this.rectPaddle;
  }

  public void update(long fps) {
    for(Movable movable: movableList){
      movable.move(fps);
    }
  }

  public void draw(Canvas canvas) {
    for(Movable movable: movableList){
      movable.draw(canvas);
    }
  }

  void paddleMoveLeft() {
    rectPaddle.moveLeft();
  }

  void paddleMoveRight() {
    rectPaddle.moveRight();
  }

  void paddleStop() {
    rectPaddle.stop();
  }
}
