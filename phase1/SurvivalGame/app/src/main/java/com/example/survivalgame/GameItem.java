package com.example.survivalgame;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameItem {
  /** The x-coordinate of this GameItem */
  private float xCoordinate;
  /** The y-coordinate of this GameItem */
  private float yCoordinate;
  /** An instance of PongGameManager */
  private PongGameManager pongGameManager;

  private Paint paint;

  /**
   * Create a GameItem.
   *
   * @param xCoordinate the x-coordinate of this GameItem
   * @param yCoordinate the y-coordinate of this GameItem
   */
  GameItem(PongGameManager pongGameManager, float xCoordinate, float yCoordinate) {
    paint = new Paint();
    this.pongGameManager = pongGameManager;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
  }

  /** A getter of pongGameManager */
  PongGameManager getPongGameManager() {
    return pongGameManager;
  }

  /** A getter of xCoordinate */
  float getXCoordinate() {
    return xCoordinate;
  }

  /** A setter of xCoordinate */
  void setXCoordinate(float newXCoordinate) {
    this.xCoordinate = newXCoordinate;
  }

  /** A getter of yCoordinate */
  float getYCoordinate() {
    return yCoordinate;
  }

  /** A setter of yCoordinate */
  void setYCoordinate(float newYCoordinate) {
    this.yCoordinate = newYCoordinate;
  }

  Paint getPaint() {
    return paint;
  }

  public abstract void draw(Canvas canvas);
}
