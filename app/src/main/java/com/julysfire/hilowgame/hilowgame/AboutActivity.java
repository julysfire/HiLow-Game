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

public class AboutActivity extends AppCompatActivity
{
    private AdView madView;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Ads
        MobileAds.initialize(this, "ca-app-pub-4179709266227495~4985068774");
        madView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView.loadAd(adRequest);

        final ScrollView background = (ScrollView)findViewById(R.id.rulesbackground);
        final TextView rules = (TextView)findViewById(R.id.ruleLabel);
        final TextView points = (TextView)findViewById(R.id.pointsLabel);
        final TextView questions = (TextView)findViewById(R.id.questionsLabel);

        final Resources res = getResources();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int resID = res.getIdentifier(prefs.getString("activebackground","feltbackground"),"drawable",getPackageName());
        background.setBackgroundResource(resID);

        int resID2 = res.getIdentifier(prefs.getString("activelayer","feltlayer"),"drawable",getPackageName());
        rules.setBackgroundResource(resID2);
        points.setBackgroundResource(resID2);
        questions.setBackgroundResource(resID2);
    }
}
