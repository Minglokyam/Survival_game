package com.example.survivalgame.ponggame.presenter;

import com.example.survivalgame.User;
import com.example.survivalgame.ponggame.model.PongGameItem;
import com.example.survivalgame.ponggame.model.PongGameManager;
import com.example.survivalgame.ponggame.view.View;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PongGameReplayPresenter extends Thread {
    private boolean running;
    private View view;
    private User user;
    private PongGameManager pongGameManager;
    private long fps = 30;
    List<List<Float>> itemList;
    /**
     * the countdown of this game
     */
    private Duration pongDuration;

    public PongGameReplayPresenter(View view, User user, int screenWidth, int screenHeight) {
        this.user = user;
        this.view = view;
        pongGameManager = new PongGameManager(user, screenWidth, screenHeight);
        pongDuration = Duration.ofSeconds(30);
    }

    /**
     * citation: http://gamecodeschool.com/android/programming-a-pong-game-for-android/ and assignment
     * 1 fishtank
     */
    @Override
    public void run() {
        while (running) {
            long startTime = System.currentTimeMillis();
            view.clearCanvas();
            try {
                view.lockCanvas();
                synchronized (this) {
                    pongGameManager.update(fps);
                    setTouchReference();
                    view.drawColor(255, 255, 255);
                    view.drawText("Life: " + user.getLife(), 0, 32);
                    view.drawText("Total time: " + user.getTotalDuration().getSeconds(), 0, 64);
                    view.drawText("Game time: " + pongDuration.getSeconds(), 0, 96);
                    view.drawText("Score: " + user.getScore(), 0, 128);
                    checkQuit();
                    ArrayList<float[]> replayList = user.getReplay();
                    float[] tempReplay = replayList.get(0);
                    user.deleteReplay();
                    itemList = pongGameManager.getItemList();
                    for (List<Float> floatList : itemList) {
                        if (floatList.get(0) == PongGameItem.CIRCLE) {
                            view.drawCircle(tempReplay[0], tempReplay[1], floatList.get(3));
                        } else {
                            view.drawRect(tempReplay[2], tempReplay[3], floatList.get(3), floatList.get(4));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                view.unlockCanvasAndPost();
            }

            long timeInterval = System.currentTimeMillis() - startTime;
            // update the total time
            user.setTotalDuration(user.getTotalDuration().plusMillis(timeInterval));
            // update the countdown
            pongDuration = (pongDuration.minusMillis(timeInterval));

            if (timeInterval > 1) {
                fps = 1000 / timeInterval;
            }
        }
    }

    /**
     * citation: http://gamecodeschool.com/android/programming-a-pong-game-for-android/
     */
    public void checkQuit() {
        user.setScore(user.getScore() + 1);
        if (user.isEmptyReplay()) { // If no life left, return to main screen.
            running = false;
            view.toMain();
        }
    }

    public void setRunning(boolean newRunning) {
        running = newRunning;
    }

    void setTouchReference() {
        float newTouchReference = pongGameManager.getTouchReference();
        view.setTouchReference(newTouchReference);
    }




}
