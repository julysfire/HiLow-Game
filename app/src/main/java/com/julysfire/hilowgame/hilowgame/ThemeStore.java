package com.julysfire.hilowgame.hilowgame;

import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import android.content.SharedPreferences;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ThemeStore extends AppCompatActivity
{
    private AdView madView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themestore);
        final Resources res = getResources();

        //Ads
        MobileAds.initialize(this, "ca-app-pub-4179709266227495~4985068774");
        madView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView.loadAd(adRequest);

        //Variables
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor mEditor = prefs.edit();

        final ScrollView background = (ScrollView) findViewById(R.id.themeStoreBackground);
        int resID = res.getIdentifier(prefs.getString("activebackground","feltbackground"),"drawable",getPackageName());
        background.setBackgroundResource(resID);

        //Points
        Game game = new Game();
        int points = game.getPoints();

        //Buttons
        final Button feltImage = (Button) findViewById(R.id.feltButton);

        //Unlocks
        if(points >= 3000) {
            final Button woodImage = (Button) findViewById(R.id.woodButton);
            final Button woodBorder = (Button)findViewById(R.id.woodborder);

            int resIDcurrent = res.getIdentifier("themewood","drawable",getPackageName());
            woodBorder.setBackgroundResource(resIDcurrent);

            woodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditor.putString("activebackground","woodbackground").commit();
                    mEditor.putString("activelayer","woodlayer").commit();
                    int resid = res.getIdentifier("woodbackground","drawable",getPackageName());
                    background.setBackgroundResource(resid);
                }
            });
        }
        if(points >= 6000)
        {
            final Button metalImage = (Button)findViewById(R.id.metalbutton);
            final Button metalborder = (Button)findViewById(R.id.metalborder);
            int resIDcurrent = res.getIdentifier("thememetal","drawable",getPackageName());
            metalborder.setBackgroundResource(resIDcurrent);

            metalImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditor.putString("activebackground","metalbackground").commit();
                    mEditor.putString("activelayer","metallayer").commit();
                    int resid = res.getIdentifier("metalbackground","drawable",getPackageName());
                    background.setBackgroundResource(resid);
                }
            });
        }

        if(points >= 9000)
        {
            final Button beachImage = (Button)findViewById(R.id.beachbutton);
            final Button beachBorder = (Button)findViewById(R.id.beachborder);
            int resIDcurrent = res.getIdentifier("themebeach","drawable",getPackageName());
            beachBorder.setBackgroundResource(resIDcurrent);

            beachImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditor.putString("activebackground","beachbackground").commit();
                    mEditor.putString("activelayer","beachlayer").commit();
                    int resid = res.getIdentifier("beachbackground","drawable",getPackageName());
                    background.setBackgroundResource(resid);
                }
            });
        }
        if(points >= 12000)
        {
            final Button aridImage = (Button)findViewById(R.id.rockbutton);
            final Button aridBorder = (Button)findViewById(R.id.rockborder);
            int residCurrent = res.getIdentifier("themerock","drawable",getPackageName());
            aridBorder.setBackgroundResource(residCurrent);

            aridImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditor.putString("activebackground","rockbackground").commit();
                    mEditor.putString("activelayer","rocklayer").commit();
                    int resid = res.getIdentifier("rockbackground","drawable",getPackageName());
                    background.setBackgroundResource(resid);
                }
            });
        }
        if(points >= 15000)
        {
            final Button goldImage = (Button)findViewById(R.id.goldbutton);
            final Button goldBorder = (Button)findViewById(R.id.goldborder);
            int residCurrent = res.getIdentifier("goldtheme","drawable",getPackageName());
            goldBorder.setBackgroundResource(residCurrent);

            goldImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditor.putString("activebackground","goldbackground").commit();
                    mEditor.putString("activelayer","goldlayer").commit();
                    int resid = res.getIdentifier("goldbackground","drawable",getPackageName());
                    background.setBackgroundResource(resid);
                }
            });
        }
        if(points >= 18000)
        {
            final Button digitalImage = (Button)findViewById(R.id.digitalbutton);
            final Button digitalBorder = (Button)findViewById(R.id.digitalborder);
            int residCurrent = res.getIdentifier("themedigital","drawable",getPackageName());
            digitalBorder.setBackgroundResource(residCurrent);

            digitalImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditor.putString("activebackground","digitalbackground").commit();
                    mEditor.putString("activelayer","digitallayer").commit();
                    int resid = res.getIdentifier("digitalbackground","drawable",getPackageName());
                    background.setBackgroundResource(resid);
                }
            });
        }
        if(points >= 21000)
        {
            final Button jokerImage = (Button)findViewById(R.id.jokerbutton);
            final Button jokerBorder = (Button)findViewById(R.id.jokerborder);
            int residCurrent = res.getIdentifier("themejoker","drawable",getPackageName());
            jokerBorder.setBackgroundResource(residCurrent);

            jokerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditor.putString("activebackground","jokerbackground").commit();
                    mEditor.putString("activelayer","jokerlayer").commit();
                    int resid = res.getIdentifier("jokerbackground","drawable",getPackageName());
                    background.setBackgroundResource(resid);
                }
            });
        }

        feltImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putString("activebackground","feltbackground").commit();
                mEditor.putString("activelayer","feltlayer").commit();
                int resid = res.getIdentifier("feltbackground","drawable",getPackageName());
                background.setBackgroundResource(resid);
            }
        });
    }
}