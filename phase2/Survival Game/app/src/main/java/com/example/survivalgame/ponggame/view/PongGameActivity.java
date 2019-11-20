package com.example.survivalgame.ponggame.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.survivalgame.UserManager;
import com.example.survivalgame.IOManager;
import com.example.survivalgame.MainActivity;
import com.example.survivalgame.User;
import com.example.survivalgame.dodgegame.view.DodgeGameActivity;
import com.example.survivalgame.scoreboard.view.RankingActivity;

import java.util.ArrayList;
import java.util.List;

public class PongGameActivity extends AppCompatActivity implements PongActivityInterface {
  private PongGameView pongGameView;
  private String name;
  private User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    name = intent.getStringExtra("user");
    user = UserManager.getUser(name);
    user.setGameStage(User.PONG);
    user.setReplay(new ArrayList<List<List<Float>>>()); // empty the replay
    IOManager.saveFile(this);
    pongGameView = new PongGameView(this, this, user);
    setContentView(pongGameView);
  }

  /** sent user statistic to DodgeGame, start DodgeGame, end PongGame */
  @Override
  public void toDodge() {
    Intent intent = new Intent(this, DodgeGameActivity.class);
    intent.putExtra("user", name);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    finish();
  }

  @Override
  public void toScoreBoard() {
    Intent intent = new Intent(this, RankingActivity.class);
    intent.putExtra("user", name);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    finish();
  }
}
