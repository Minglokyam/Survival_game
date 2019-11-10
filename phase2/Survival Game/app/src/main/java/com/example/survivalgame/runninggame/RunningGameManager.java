package com.example.survivalgame.runninggame;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.survivalgame.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class RunningGameManager {

    private RunningGameView runningGameView;

    private RunnerFactory runnerFactory;

    private RandomItemFactory randomItemFactory;

    private GroundFactory groundFactory;

    // the runner.
    Runner runner;

    // ground.
    private Ground ground;

    // the timer of the coin.
    private int timerCoins = 0;

    // the timer of the spike.
    private int timerSpike = 0;

    // random timer of the spike.
    private int timerRandomSpikes = 0;

    // The height of ground
    private int groundHeight;

    private List<RandomItem> randomItems = new ArrayList<>();

    RunningGameManager(RunningGameView runningGameView) {
        this.runningGameView = runningGameView;
        randomItemFactory = new RandomItemFactory();
        runnerFactory = new RunnerFactory();
        groundFactory = new GroundFactory();
        // add runner and ground to the game.
        ground = groundFactory.createGround(runningGameView, runningGameView.getGroundBMP(), 0, 0);
        groundHeight = ground.getHeight();
        runner =
                runnerFactory.createRunner(
                        runningGameView, runningGameView.getRunnerBMP(), 50, 50, groundHeight);
    }

    /**
     * update the coin when it moves out of the screen or the runner touches it.
     */
    private void updateItems(RunningGameActivity runningGameActivity, User user) {

        Iterator<RandomItem> randomItemIterator = randomItems.iterator();

        while (randomItemIterator.hasNext()) {

            RandomItem randomItem = randomItemIterator.next();
            Rect runnerRect = runner.getRect();
            Rect rect = randomItem.getRect();

            if (randomItem.checkCollision(runnerRect, rect)) {
                collideAction(runningGameActivity, user, randomItem);
                // remove the random item after touching.
                randomItemIterator.remove();
                break;
            }
        }
    }

    private void collideAction(
            RunningGameActivity runningGameActivity, User user, RandomItem randomItem) {
        if (randomItem instanceof Coin) {
            // add points to the score when the runner touches a coin.
            user.setScore(user.getScore() + 100);
        } else {
            user.setLife(user.getLife() - 1);
        }
    }

    /**
     * update the coins and spikes.
     */
    void update(RunningGameActivity runningGameActivity, User user) {
        updateTimer();
        randomGenerateItems();
        removeRandomItems();
        updateItems(runningGameActivity, user);
    }

    /**
     * update the timers.
     */
    private void updateTimer() {
        timerCoins++;
        timerSpike++;
    }

    /**
     * randomly generate the coins and spikes.
     */
    private void randomGenerateItems() {
        // randomly generate spikes.
        randomGenerateSpikes();
        // randomly generate coins.
        randomGenerateCoins();
    }

    /**
     * remove random items that are not inside the screen
     */
    private void removeRandomItems() {
        Iterator<RandomItem> randomItemIterator = randomItems.iterator();
        while (randomItemIterator.hasNext()) {
            RandomItem randomItem = randomItemIterator.next();
            if (randomItem.outOfScreen()) {
                randomItemIterator.remove();
            }
        }
    }

    /**
     * randomly generate the spikes in the running game. citation:
     * https://www.youtube.com/watch?v=zyCZEaw3Gow&t=266s
     */
    private void randomGenerateSpikes() {
        switch (timerRandomSpikes) {
            // three different cases to generate spikes in different distances.
            case 0:
                if (timerSpike >= 100) {
                    makeSpikes();
                }
                break;
            case 1:
                if (timerSpike >= 125) {
                    makeSpikes();
                }
                break;
            case 2:
                if (timerSpike >= 150) {
                    makeSpikes();
                }
                break;
        }
    }

    private void makeSpikes() {
        RandomItem spike =
                randomItemFactory.createRandomItem(
                        runningGameView,
                        runningGameView.getSpikeBMP(),
                        runningGameView.getWidth() + 24,
                        24,
                        groundHeight,
                        randomItemFactory.SPIKE);
        randomItems.add(spike);
        Random spikesRandom = new Random();
        timerRandomSpikes = spikesRandom.nextInt(3);
        timerSpike = 0;
    }

    /**
     * randomly generate the coins in the running game. citation:
     * https://www.youtube.com/watch?v=lmAmr8Efu34&t=492s
     */
    private void randomGenerateCoins() {
        if (timerCoins >= 100) {
            // randomly generate int 0 and 1 to decide which case the coins are generated.
            int random = new Random().nextInt(2);
            switch (random) {
                case 0:
                    // construct five consecutive coins in same height.
                    for (int i = 1; i < 6; i++) {
                        makeCoins(i * 64, 130);
                    }
                    break;

                case 1:
                    // construct three consecutive coins in different height.
                    makeCoins(32, 150);
                    makeCoins(96, 130);
                    makeCoins(160, 150);
                    break;
            }
            // reset the timer.
            timerCoins = 0;
        }
    }

    private void makeCoins(int xCoordinate, int yCoordinate) {
        RandomItem coin =
                randomItemFactory.createRandomItem(
                        runningGameView,
                        runningGameView.getCoinBMP(),
                        runningGameView.getWidth() + xCoordinate,
                        yCoordinate,
                        groundHeight,
                        randomItemFactory.COIN);
        randomItems.add(coin);
    }

    void draw(Canvas canvas) {
        runner.draw(canvas);
        for (RandomItem randomItem : randomItems) {
            randomItem.draw(canvas);
        }
        ground.draw(canvas);
    }
}
