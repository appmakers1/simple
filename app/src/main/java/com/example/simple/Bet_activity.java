package com.example.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Bet_activity extends AppCompatActivity {

    private String mteam1;
    private String mteam2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
         mteam1 = extras.getString("team1");
         mteam2 = extras.getString("team2");
        }

        TextView team1q1 = (TextView) findViewById(R.id.teamA);
        TextView team2q1 = (TextView) findViewById(R.id.teamB);
        TextView team1q2 = (TextView) findViewById(R.id.teamA1);
        TextView team2q2 = (TextView) findViewById(R.id.teamB1);

        team1q1.setText(mteam1);
        team2q1.setText(mteam2);
        team1q2.setText(mteam1);
        team2q2.setText(mteam2);


    }

}
