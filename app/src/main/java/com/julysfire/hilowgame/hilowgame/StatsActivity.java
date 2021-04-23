package com.julysfire.hilowgame.hilowgame;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;

public class StatsActivity extends AppCompatActivity
{
    private AdView madView;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //Ads
        MobileAds.initialize(this, "ca-app-pub-4179709266227495~4985068774");
        madView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView.loadAd(adRequest);

        Game hiLow = new Game();

        //Background
        final ScrollView background = (ScrollView)findViewById(R.id.statsbackground);
        final Resources res = getResources();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int resID = res.getIdentifier(prefs.getString("activebackground","feltbackground"),"drawable",getPackageName());
        background.setBackgroundResource(resID);

        //Stats
        final TextView wonText = (TextView)findViewById(R.id.totalWonText);
        final TextView lostText = (TextView)findViewById(R.id.totalLostText);
        final TextView currentPoints = (TextView)findViewById(R.id.currentPointsText);
        final TextView winPercText = (TextView) findViewById(R.id.winPerc);
        final TextView totHighText = (TextView) findViewById(R.id.totHighText);
        final TextView totLowText = (TextView) findViewById(R.id.totLowText);
        final TextView totTieText = (TextView) findViewById(R.id.totTieText);
        final TextView percHighText = (TextView) findViewById(R.id.percHighText);
        final TextView percLowText = (TextView) findViewById(R.id.percLowText);
        final TextView percTieText = (TextView) findViewById(R.id.percTieText);
        final TextView totalpointswon = (TextView) findViewById(R.id.totalpointswontext);
        final TextView totalpointslost = (TextView) findViewById(R.id.totalpointslosttext);

        double totalRoundsPlayed = ((double)hiLow.getTotalCorrect()) + ((double)hiLow.getTotalWrong());

        double winPerc = (((double)hiLow.getTotalCorrect()) /totalRoundsPlayed*100);
        double highPerc = (((double)hiLow.getTotalHighs()) /totalRoundsPlayed)*100;
        double lowPerc = (((double)hiLow.getTotalLows()) /totalRoundsPlayed)*100;
        double tiePerc = (((double)hiLow.getTotalTies())/totalRoundsPlayed)*100;

        currentPoints.setText(hiLow.getPoints() + "");
        wonText.setText(hiLow.getTotalCorrect() + "");
        lostText.setText( hiLow.getTotalWrong() + "");
        winPercText.setText(String.format("%.2f",winPerc) + "%");
        totHighText.setText(hiLow.getTotalHighs() + "");
        totLowText.setText(hiLow.getTotalLows() + "");
        totTieText.setText(hiLow.getTotalTies() + "");
        percHighText.setText(String.format("%.2f",highPerc)+ "%");
        percLowText.setText(String.format("%.2f", lowPerc) + "%");
        percTieText.setText(String.format("%.2f", tiePerc) + "%");
        totalpointswon.setText(hiLow.getTotalPointsWon() + "");
        totalpointslost.setText(hiLow.getTotalPointsLost() + "");
    }
}
