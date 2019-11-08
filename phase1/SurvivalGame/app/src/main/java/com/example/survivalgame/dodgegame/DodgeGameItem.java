package com.example.survivalgame.dodgegame;

import android.graphics.Canvas;
import android.graphics.Paint;
// The super class for all game objects in Dodge Game
abstract class DodgeGameItem {
  /** The x-coordinate of this DodgeGameItem */
  private float xCoordinate;
  /** The y-coordinate of this DodgeGameItem */
  private float yCoordinate;
  /** An instance of DodgeGameManager */
  private DodgeGameManager dodgeGameManager;

  private Paint paint;

  /** Create a DodgeGameItem. */
  DodgeGameItem(DodgeGameManager dodgeGameManager, float xCoordinate, float yCoordinate) {
    this.xCoordinate = xCoordinate;
    paint = new Paint();
    this.dodgeGameManager = dodgeGameManager;
    this.yCoordinate = yCoordinate;
  }

  /** A getter of dodgeGameManager */
  DodgeGameManager getDodgeGameManager() {
    return dodgeGameManager;
  }

  /** A getter of xCoordinate */
  public float getXCoordinate() {
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

  abstract void draw(Canvas canvas);
}