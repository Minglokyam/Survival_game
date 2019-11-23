package com.example.survivalgame.scoreboard.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.survivalgame.general.IOManager;
import com.example.survivalgame.loginsystem.view.MainActivity;
import com.example.survivalgame.R;

import com.example.survivalgame.general.User;
import com.example.survivalgame.general.UserManager;
import com.example.survivalgame.scoreboard.model.RankingInteractor;
import com.example.survivalgame.scoreboard.presenter.RankingPresenter;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity implements RankingView {
  RankingPresenter rankingPresenter;
  private User user;
  private String name;
  private List<TextView> textViews;
  private TextView yourScore;
  private EditText enterName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    name = intent.getStringExtra("user");
    user = UserManager.getUser(name);

    setContentView(R.layout.activity_ranking);

    textViews = new ArrayList<>();
    setTextViews();
    yourScore = findViewById(R.id.yourScore);
    enterName = findViewById(R.id.enterName);
    Button enter = findViewById(R.id.Enter);
    enter.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                setText(yourScore,enterName);
              }
            });

    Button toMainButton = findViewById(R.id.toMain);
    toMainButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            toMain();
          }
        });

    rankingPresenter = new RankingPresenter(this, user, new RankingInteractor());
  }

  public void toMain() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    user.reset();
    IOManager.saveFile(this);
    startActivity(intent);
    finish();
  }

  private void setTextViews() {
    TextView num1 = findViewById(R.id.num1);
    textViews.add(num1);
    TextView num2 = findViewById(R.id.num2);
    textViews.add(num2);
    TextView num3 = findViewById(R.id.num3);
    textViews.add(num3);
  }

  @Override
  public void printRankingText(List<String> userStatementList, int size) {
    String userStatement;
    TextView textView;
    for (int i = 0; i < size; i++) {
      userStatement = userStatementList.get(i);
      textView = textViews.get(i);
      textView.setText(userStatement);
    }
  }

  @Override
  public void setUserText(int userText) {
    yourScore.setText("You received " + userText + " scores");
  }

  public void setText(TextView textView,EditText editText){
    String x = editText.getText().toString()+" received " + user.getScore()+" scores";
    textView.setText(x);
    setNickNameOnBoard(x);

  }

  public void setNickNameOnBoard(String sentence){
    for(int i=0;i<textViews.size();i++){
      if(textViews.get(i).getText().toString().contains(user.getUsername())){
        textViews.get(i).setText(sentence);
      }
    }


  }
}