package com.example.survivalgame.runninggame;

import android.graphics.Bitmap;

class CoinFactory {
  /** the factory used to create the coin. */
  Coin createCoin(
      RunningGameView view, Bitmap bmp, int xCoordinate, int yCoordinate, int groundHeight) {
    int newY = view.getHeight() - yCoordinate - groundHeight - bmp.getHeight();
    return new Coin(view, bmp, xCoordinate, newY);
  }
}