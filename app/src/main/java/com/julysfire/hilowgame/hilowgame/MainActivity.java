package com.julysfire.hilowgame.hilowgame;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity
{
    private AdView madView;
    private InterstitialAd shuffleAd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //OnCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get hiLo java file
        Game hiLowGame = new Game();

		//Variables
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor mEditor = prefs.edit();

        //LOAD VARIABLES
        loadVariables();
        loadBackgroundsLayers();

        //Ads
        MobileAds.initialize(this, "ca-app-pub-4179709266227495~4985068774");
        madView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView.loadAd(adRequest);
        shuffleAd = new InterstitialAd(this);
        shuffleAd.setAdUnitId("ca-app-pub-4179709266227495/9201615576");
        shuffleAd.loadAd(new AdRequest.Builder().build());

        //XML Items
        final ImageButton highButt = (ImageButton)findViewById(R.id.hiButton);
        final ImageButton lowButt = (ImageButton)findViewById(R.id.lowButton);
        final ImageButton tieButt = (ImageButton)findViewById(R.id.tieButton);
        final ImageView currentImage = (ImageView)findViewById(R.id.currentImage);
        final ImageView drawnImage = (ImageView)findViewById(R.id.drawnImage);
        final TextView moneyText = (TextView)findViewById(R.id.moneyText);
        final TextView streakText = (TextView)findViewById(R.id.steakText);
        final TextView winLoseText = (TextView)findViewById(R.id.winLoseText);
        final TextView cardsLeftText = (TextView)findViewById(R.id.cardsLeftText);
        final ProgressBar levelProgress = (ProgressBar)findViewById(R.id.levelBar);
        final TextView levelText = (TextView)findViewById(R.id.levelText);

        //Level
        levelProgress.setMax(500);
        levelText.setText("Level " + hiLowGame.getLevel());

        int tc = hiLowGame.getTotalCorrect();
        if(tc > 500)
        {
            do{
                tc = tc - 500;
            }while(tc > 500);
        }
        levelProgress.setProgress(tc);

        String drawnCardPic = prefs.getString("drawnCardPic","");

        //Animation
        final TranslateAnimation moveCard = new TranslateAnimation(350,0,0,0);
        moveCard.setDuration(150);
        moveCard.setFillAfter(false);
        moveCard.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        final TranslateAnimation drawCard = new TranslateAnimation(300,0,0,0);
        drawCard.setDuration(50);
        drawCard.setFillAfter(false);
        drawCard.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        //Setup, first time opening the app, or just begin the round
        int firstStart = prefs.getInt("firstStart", 0);
        if(firstStart == 0)
        {
            mEditor.putString("activebackground","feltbackground").commit();
            mEditor.putString("activelayer","feltlayer").commit();
            drawnCardPic = hiLowGame.startGame();
            hiLowGame.setCardsLeft(208);
            mEditor.putInt("firstStart", 1).commit();
            mEditor.putInt("money", hiLowGame.getPoints()).commit();
            moneyText.setText("Points: " + hiLowGame.getPoints());
            cardsLeftText.setText("Cards Left: " + hiLowGame.getCardsLeft());
        }
        else if(drawnCardPic.equals(""))
        {
            drawnCardPic = hiLowGame.beginRound();
        }

        //Set textboxes
        moneyText.setText("Points : " + hiLowGame.getPoints());
        streakText.setText("Current Streak: " + hiLowGame.getStreak());
        cardsLeftText.setText("Cards Left: " + hiLowGame.getCardsLeft());

        //Get the drawn card and display it
        final Resources res = getResources();
        int resID = res.getIdentifier(drawnCardPic,"drawable",getPackageName());
        drawnImage.setImageResource(resID);

        //Actual Gameplay, high or low buttons
        highButt.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Game hiLowGame = new Game();
                String drawnCardPic = hiLowGame.getNewCardPic();

                String currentCardPic = drawnCardPic;

                //Draw card picture
                int resIDcurrent = res.getIdentifier(currentCardPic,"drawable",getPackageName());
                currentImage.setImageResource(resIDcurrent);

                drawnCardPic = hiLowGame.playRound();
                hiLowGame.setNewCardPic(drawnCardPic);

                //Animate
                drawnImage.startAnimation(moveCard);
                currentImage.startAnimation(drawCard);

                //Draw card picture
                int resIDdrawn = res.getIdentifier(drawnCardPic,"drawable",getPackageName());
                drawnImage.setImageResource(resIDdrawn);

                //Set cards -1
                hiLowGame.setCardsLeft(hiLowGame.getCardsLeft()-1);

                if(hiLowGame.getCardsLeft() <= 0) {
                    showShuffleAd();
                    hiLowGame.setCardsLeft(208);
                }
                cardsLeftText.setText("Cards Left: " + hiLowGame.getCardsLeft());
                String winLoseTie = hiLowGame.testWin("highbutt");

                int streak = hiLowGame.getStreak();

                //Streak
                if(streak > 0)
                {
                    if(streak % 5 == 0)
                        winLoseText.setText(winLoseTie + " STREAK BONUS");
                    else
                        winLoseText.setText(winLoseTie);
                }
                else
                    winLoseText.setText(winLoseTie);
                streakText.setText("Current Streak: " + streak);

                moneyText.setText("Points: " + hiLowGame.getPoints());

                //Level
                if(hiLowGame.getTotalCorrect() % 500 == 0 && hiLowGame.getTotalCorrect() != 0)
                {
                    hiLowGame.setLevel(hiLowGame.getLevel());
                    levelText.setText("Level " + (hiLowGame.getLevel() +1));
                    levelProgress.setProgress(0);
                }
                else
                {
                    int tc = hiLowGame.getTotalCorrect();

                    if(tc > 500)
                    {
                        do{
                            tc = tc - 500;
                        }while(tc > 500);
                    }
                    levelProgress.setProgress(tc);
                }
            }
        });

        lowButt.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
        {
            Game hiLowGame = new Game();
            String drawnCardPic = hiLowGame.getNewCardPic();

            String currentCardPic = drawnCardPic;

            //Draw card picture
            int resIDcurrent = res.getIdentifier(currentCardPic,"drawable",getPackageName());
            currentImage.setImageResource(resIDcurrent);

            drawnCardPic = hiLowGame.playRound();
            hiLowGame.setNewCardPic(drawnCardPic);

            //Animate
            drawnImage.startAnimation(moveCard);
            currentImage.startAnimation(drawCard);

            //Draw card picture
            int resIDdrawn = res.getIdentifier(drawnCardPic,"drawable",getPackageName());
            drawnImage.setImageResource(resIDdrawn);

            //Set cards -1
            hiLowGame.setCardsLeft(hiLowGame.getCardsLeft()-1);

            if(hiLowGame.getCardsLeft() <= 0) {
                showShuffleAd();
                hiLowGame.setCardsLeft(208);
            }
            cardsLeftText.setText("Cards Left: " + hiLowGame.getCardsLeft());
            String winLoseTie = hiLowGame.testWin("lowButt");

            //Streak
            int streak = hiLowGame.getStreak();
            if(streak > 0)
            {
                if(streak % 5 == 0)
                    winLoseText.setText(winLoseTie + " STREAK BONUS");
                else
                    winLoseText.setText(winLoseTie);
            }
            else
                winLoseText.setText(winLoseTie);
            streakText.setText("Current Streak: " + streak);

            moneyText.setText("Points: " + hiLowGame.getPoints());

            //Level
            if(hiLowGame.getTotalCorrect() % 500 == 0 && hiLowGame.getTotalCorrect() != 0)
            {
                hiLowGame.setLevel(hiLowGame.getLevel()+1);
                levelText.setText("Level " + (hiLowGame.getLevel() + 1));
                levelProgress.setProgress(0);
            }
            else
            {
                int tc = hiLowGame.getTotalCorrect();

                if(tc > 500)
                {
                    do{
                        tc = tc - 500;
                    }while(tc > 500);
                }
                levelProgress.setProgress(tc);
            }
        }
        });

        tieButt.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Game hiLowGame = new Game();
                String drawnCardPic = hiLowGame.getNewCardPic();
                String currentCardPic = drawnCardPic;

                //Draw card picutre
                int resIDcurrent = res.getIdentifier(currentCardPic,"drawable",getPackageName());
                currentImage.setImageResource(resIDcurrent);

                drawnCardPic = hiLowGame.playRound();
                hiLowGame.setNewCardPic(drawnCardPic);

                //Animate
                drawnImage.startAnimation(moveCard);
                currentImage.startAnimation(drawCard);

                //Draw card picture
                int resIDdrawn = res.getIdentifier(drawnCardPic,"drawable",getPackageName());
                drawnImage.setImageResource(resIDdrawn);

                //Set cards-1
                hiLowGame.setCardsLeft(hiLowGame.getCardsLeft()-1);

                if(hiLowGame.getCardsLeft() <= 0) {
                    showShuffleAd();
                    hiLowGame.setCardsLeft(208);
                }
                cardsLeftText.setText("Cards Left: " + hiLowGame.getCardsLeft());
                String winLoseTie = hiLowGame.testWin("tieButt");

                //Streak
                int streak = hiLowGame.getStreak();
                if(streak > 0)
                {
                    if(streak % 5 == 0)
                        winLoseText.setText(winLoseTie + " STREAK BONUS");
                    else
                        winLoseText.setText(winLoseTie);
                }
                else
                    winLoseText.setText(winLoseTie);
                streakText.setText("Current Streak: " + streak);
                moneyText.setText("Points: " + hiLowGame.getPoints());

                //Level
                if(hiLowGame.getTotalCorrect() % 500 == 0 && hiLowGame.getTotalCorrect() != 0)
                {
                    hiLowGame.setLevel(hiLowGame.getLevel()+1);
                    levelText.setText("Level " + (hiLowGame.getLevel() + 1));
                    levelProgress.setProgress(0);
                }
                else
                {
                    int tc = hiLowGame.getTotalCorrect();

                    if(tc > 500)
                    {
                        do{
                            tc = tc - 500;
                        }while(tc > 500);
                    }
                    levelProgress.setProgress(tc);
                }
            }
        });

        shuffleAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                shuffleAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveVariables();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVariables();
        loadBackgroundsLayers();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();
        final Context context = getApplicationContext();
        final int Duration = Toast.LENGTH_LONG;

        if (id == R.id.suggestions)
        {
            Toast toast = Toast.makeText(context, "Please send suggestions to julysfire@gmail.com", Duration);
            toast.show();
            return true;
        }
        else if (id == R.id.shuffle)
        {
            Game hiLow = new Game();
            int x = hiLow.getCurrentCardLoc();
            hiLow.shuffle(x);

            hiLow.setCardsLeft(208);
            final TextView cardsLeftText = (TextView)findViewById(R.id.cardsLeftText);
            cardsLeftText.setText("Cards Left: " + hiLow.getCardsLeft());

            shuffleAd.show();
        }
        else if (id == R.id.upcoming)
        {
            Intent intent = new Intent(this, UpcomingActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.about)
        {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.stats)
        {
            Intent intent = new Intent(this, StatsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.themeStore)
        {
            Intent intent = new Intent(this, ThemeStore.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showShuffleAd()
    {
        if(shuffleAd != null && shuffleAd.isLoaded())
        {
            shuffleAd.show();
        }
    }

    public void saveVariables()
    {
        Game hiLo = new Game();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor mEditor = prefs.edit();

        mEditor.putInt("money", hiLo.getPoints()).commit();
        mEditor.putInt("totalCorrect", hiLo.getTotalCorrect()).commit();
        mEditor.putInt("totalWrong", hiLo.getTotalWrong()).commit();
        mEditor.putInt("totalHighs", hiLo.getTotalHighs()).commit();
        mEditor.putInt("totalLows", hiLo.getTotalLows()).commit();
        mEditor.putInt("totalTies", hiLo.getTotalTies()).commit();
        mEditor.putInt("streak", hiLo.getStreak()).commit();
        mEditor.putInt("cardsLeft", hiLo.getCardsLeft()).commit();
        mEditor.putString("drawnCardPic", hiLo.getNewCardPic()).commit();
        mEditor.putInt("totalPointsWon", hiLo.getTotalPointsWon()).commit();
        mEditor.putInt("totalPointsLost", hiLo.getTotalPointsLost()).commit();
        mEditor.putInt("level",hiLo.getLevel()).commit();

        for(int x = 0; x < 208; x++)
        {
            mEditor.putInt("playedCards_" + x, hiLo.getPlayedCards(x)).commit();
        }
    }

    public void loadVariables()
    {
        Game hiLo = new Game();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int points = prefs.getInt("money", 0);
        hiLo.setPoints(points);
        hiLo.setTotalCorrect(prefs.getInt("totalCorrect",0));
        hiLo.setTotalWrong(prefs.getInt("totalWrong", 0));
        hiLo.setTotalHighs(prefs.getInt("totalHighs",0));
        hiLo.setTotalLows(prefs.getInt("totalLows",0));
        hiLo.setTotalTies(prefs.getInt("totalTies",0));
        hiLo.setStreak(prefs.getInt("streak", 0));
        hiLo.setCardsLeft(prefs.getInt("cardsLeft", 0));
        hiLo.setNewCardPic(prefs.getString("drawnCardPic",""));
        hiLo.setTotalPointsWon(prefs.getInt("totalPointsWon",0));
        hiLo.setTotalPointsLost(prefs.getInt("totalPointsLost",0));
        hiLo.setLevel(prefs.getInt("level",1));

        for(int x = 0; x < 208; x++) {
            int playedCards = prefs.getInt("playedCards_" + x, 0);
            hiLo.setPlayedCards(x, playedCards);
        }
    }

    public void loadBackgroundsLayers()
    {
        final RelativeLayout background = (RelativeLayout)findViewById(R.id.backgroundmain);
        final TextView moneyText = (TextView)findViewById(R.id.moneyText);
        final TextView currentCardText = (TextView)findViewById(R.id.currentCardText);
        final TextView previousCardText = (TextView)findViewById(R.id.previousCardText);
        final TextView cardsLeftText = (TextView)findViewById(R.id.cardsLeftText);
        final TextView steakTextBorder = (TextView)findViewById(R.id.steakTextBorder);

        final Resources res = getResources();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int resID = res.getIdentifier(prefs.getString("activebackground","feltbackground"),"drawable",getPackageName());
        int resID2 = res.getIdentifier(prefs.getString("activelayer","feltbackground"),"drawable",getPackageName());

        background.setBackgroundResource(resID);
        moneyText.setBackgroundResource(resID2);
        currentCardText.setBackgroundResource(resID2);
        previousCardText.setBackgroundResource(resID2);
        cardsLeftText.setBackgroundResource(resID2);
        steakTextBorder.setBackgroundResource(resID2);
    }
}
