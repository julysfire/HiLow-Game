package com.julysfire.hilowgame.hilowgame;

import java.util.Random;

public class Game {
    public static int[] valueByCard = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public static int[] playedCards = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    public static int gameStart;
    public static int points;
    public static int streak;

    //Stats
    public static int totalCorrect;
    public static int totalWrong;
    public static int totalHighs;
    public static int totalLows;
    public static int totalTies;
    public static int totalPointsWon;
    public static int totalPointsLost;

    public static int cardsLeft = 0;

    //Naming scheme: [suit][value]
    //Value for face cards: j = jack, q = queen, k = king
    //Suits: s = spade, c = club, h = hearts, d = diamond
    public static String currentCardPic = "";
    public static String newCardPic = "";

    public Random rand = new Random();
    public int rndPos = 0;

    public boolean beenPlayed = true;

    public static int currentCardValue;
    public static int oldCardValue;

    public static int currentCardLoc;

    public static int level;

    String highLow = "";


    public String startGame() {
        rndPos = rand.nextInt(207);
        beenPlayed = playedAlreadyTest(rndPos);
        currentCardLoc = rndPos;
        currentCardValue = getCardValue(rndPos);
        currentCardPic = getCardPicture(rndPos);
        points = 100;
        gameStart = 1;
        return currentCardPic;
    }

    public String beginRound() {
        rndPos = rand.nextInt(207);
        beenPlayed = playedAlreadyTest(rndPos);
        currentCardLoc = rndPos;
        currentCardValue = getCardValue(rndPos);
        currentCardPic = getCardPicture(rndPos);
        return currentCardPic;
    }

    public String playRound() {
        int cardCount = 0;

        oldCardValue = currentCardValue;
        do {
            rndPos = rand.nextInt(207);
            beenPlayed = playedAlreadyTest(rndPos);

            cardCount++;
            if (cardCount == 207) {
                shuffle(currentCardLoc);
            }
        } while (beenPlayed == true);

        currentCardLoc = rndPos;
        currentCardValue = getCardValue(rndPos);
        currentCardPic = getCardPicture(rndPos);

        return currentCardPic;
    }

    public String testWin(String buttChoice) {
        int aceTest = oldCardValue;
        int aceCurrent = currentCardValue;
        boolean bothAces = false;

        if(aceCurrent == aceTest)
            bothAces = true;

        highLow = highLowTest(currentCardValue, oldCardValue);

        switch(buttChoice)
        {
            case "tieButt":
                if(highLow.equals("tie"))
                {
                    totalCorrect++;
                    points = points + 20;
                    totalPointsWon = totalPointsWon+20;
                    streak = streak + 1;
                    if (streak % 5 == 0) {
                        points = points + (20 * (streak / 5));
                        totalPointsWon = totalPointsWon + (20 * (streak/5));
                    }
                    return "Win!";
                }
                else
                {
                    totalWrong++;
                    points = points - 10;
                    totalPointsLost = totalPointsLost + 10;
                    streak=0;
                    if(points < 0)
                        points = 0;
                    return "Lose!";
                }
            case "lowButt":
                if(bothAces == true)
                {
                    totalWrong++;
                    points = points - 10;
                    totalPointsLost = totalPointsLost + 10;
                    streak=0;
                    if(points < 0)
                        points = 0;
                    return "Lose!";
                }
                else if(highLow.equals("low") || aceTest == 1 || aceCurrent == 1)
                {
                    totalCorrect++;
                    points = points + 5;
                    totalPointsWon = totalPointsWon+5;
                    streak = streak+1;
                    if(streak % 5 == 0)
                    {
                        points = points + (10 * (streak/5));
                        totalPointsWon = totalPointsWon + (10 * (streak/5));
                    }
                    return "Win!";
                }
                else
                {
                    totalWrong++;
                    points = points - 10;
                    totalPointsLost = totalPointsLost + 10;
                    streak=0;
                    if(points < 0)
                        points = 0;
                    return "Lose!";
                }
            case "highbutt":
                if(bothAces == true)
                {
                    totalWrong++;
                    points = points - 10;
                    totalPointsLost = totalPointsLost + 10;
                    streak=0;
                    if(points < 0)
                        points = 0;
                    return "Lose!";
                }
                else if(highLow.equals("high") || aceTest == 1 || aceCurrent == 1)
                {
                    totalCorrect++;
                    points = points + 5;
                    totalPointsWon = totalPointsWon+5;
                    streak = streak+1;
                    if(streak % 5 == 0)
                    {
                        points = points + (10 * (streak/5));
                        totalPointsWon = totalPointsWon + (10 * (streak/5));
                    }
                    return "Win!";
                }
                else
                {
                    totalWrong++;
                    points = points - 10;
                    totalPointsLost = totalPointsLost + 10;
                    streak=0;
                    if(points < 0)
                        points = 0;
                    return "Lose!";
                }

            default:
                totalWrong++;
                points = points - 10;
                totalPointsLost = totalPointsLost+10;
                streak = 0;
                if (points < 0)
                    points = 0;
                return "Broke!";
        }
    }

    public boolean playedAlreadyTest(int randomPos) {
        if (playedCards[randomPos] == 0) {
            playedCards[randomPos] = 1;
            return false;
        }

        return true;
    }

    public String highLowTest(int newCardValue, int oldCardValue) {
        if (newCardValue > oldCardValue) {
            totalHighs++;
            return "high";
        }
        else if (newCardValue < oldCardValue) {
            totalLows++;
            return "low";
        }
        totalTies++;
        return "tie";
    }

    public void shuffle(int currentLoc) {
        for (int x = 0; x < playedCards.length; x++) {
            playedCards[x] = 0;
        }

        playedCards[currentLoc] = 1;
    }

    public static boolean isBetween(int x, int lowerBound, int upperBound) {
        return lowerBound <= x && x <= upperBound;
    }

    //GETS
    public String getCardPicture(int position) {
        String cardString = "";

        //Suit
        if (isBetween(position, 0, 12) || isBetween(position, 52, 64) || isBetween(position, 104, 116) || isBetween(position, 156, 168))
            cardString = cardString + "c";
        else if (isBetween(position, 13, 25) || isBetween(position, 65, 77) || isBetween(position, 117, 129) || isBetween(position, 169, 181))
            cardString = cardString + "s";
        else if (isBetween(position, 26, 38) || isBetween(position, 78, 90) || isBetween(position, 130, 142) || isBetween(position, 182, 194))
            cardString = cardString + "h";
        else if (isBetween(position, 39, 51) || isBetween(position, 91, 103) || isBetween(position, 143, 155) || isBetween(position, 195, 207))
            cardString = cardString + "d";

        //FaceCards
        switch (position) {
            case 10:
            case 23:
            case 36:
            case 49:
            case 62:
            case 75:
            case 88:
            case 101:
            case 114:
            case 127:
            case 140:
            case 153:
            case 166:
            case 179:
            case 192:
            case 205:
                cardString = cardString + "j";
                break;
            case 11:
            case 24:
            case 37:
            case 50:
            case 63:
            case 76:
            case 89:
            case 102:
            case 115:
            case 128:
            case 141:
            case 154:
            case 167:
            case 180:
            case 193:
            case 206:
                cardString = cardString + "q";
                break;
            case 12:
            case 25:
            case 38:
            case 51:
            case 64:
            case 77:
            case 90:
            case 103:
            case 116:
            case 129:
            case 142:
            case 155:
            case 168:
            case 181:
            case 194:
            case 207:
                cardString = cardString + "k";
                break;
            default:
                cardString = cardString + valueByCard[position];
                break;
        }


        return cardString;
    }

    public int getPoints() {
        return points;
    }

    public int getCardValue(int randomPos) {
        return valueByCard[randomPos];
    }

    public int getStreak() {
        return streak;
    }

    public int getPlayedCards(int x) {
        return playedCards[x];
    }

    public int getCurrentCardLoc() {
        return currentCardLoc;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public int getTotalWrong() {
        return totalWrong;
    }

    public int getCardsLeft()
    {
        return cardsLeft;
    }

    public int getTotalHighs()
    {
        return totalHighs;
    }

    public int getTotalLows()
    {
        return totalLows;
    }

    public int getTotalTies()
    {
        return totalTies;
    }

    public String getNewCardPic()
    {
        return newCardPic;
    }

    public int getTotalPointsWon()
    {
        return totalPointsWon;
    }

    public int getTotalPointsLost()
    {
        return totalPointsLost;
    }

    public int getLevel()
    {
        int tc = totalCorrect;
        int counter = 0;

        do {
            counter++;
            tc = tc - 500;
        }while (tc >500);

        level = counter+1;

        return level;
    }

    //SETS
    public void setPoints(int oldGoints)
    {
        points = oldGoints;
    }

    public void setTotalCorrect(int oldTotal)
    {
        totalCorrect = oldTotal;
    }

    public void setTotalWrong(int oldTotal)
    {
        totalWrong = oldTotal;
    }

    public void setStreak(int str)
    {
        streak = str;
    }

    public void setCardsLeft(int cl)
    {
        cardsLeft = cl;
    }

    public void setPlayedCards(int x, int played)
    {
        playedCards[x] = played;
    }

    public void setTotalHighs(int tot)
    {
        totalHighs = tot;
    }

    public void setTotalLows(int tot)
    {
        totalLows = tot;
    }

    public void setTotalTies(int tot)
    {
        totalTies = tot;
    }

    public void setNewCardPic(String ncp)
    {
        newCardPic = ncp;
    }

    public void setTotalPointsWon(int tot)
    {
        totalPointsWon = tot;
    }

    public void setTotalPointsLost(int tot)
    {
        totalPointsLost = tot;
    }

    public void setLevel(int lev)
    {
        level = lev;
    }
}
