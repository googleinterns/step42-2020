
package com.google.sps.servlets;

//class used in userEntity to track a user's current games and their respective scores in each game.

public class GameScores{
    String gameKey;
    int score;

    public GameScores(String gameKey, int score){
        this.gameKey = gameKey;
        this.score = score;
    }

    public String getGameKey(){
        return this.gameKey;
    }
    public int getScore(){
        return this.score;
    }
}