  package com.google.sps.utils;
  
  
  /** A user and the user's current score */
  public final class User {
    String userID;
    String userName;
    int score;
    public static final String SESSION_ID_COOKIE_NAME = "SessionID";
    long quiz_timing;
    String blobKey;
    String gameId;
 
    public User(String userID, String userName, int score) {
        this.userID = userID;
        this.userName = userName;
        this.score = score;
    }

    public String getId(){
        return userID;
    }
    public String getName(){
        return userName;
    }
    public long getScore(){
        return score;
    }
    public long getQuizTiming(){
        return quiz_timing;
    }
    public String getBlobKey(){
        return blobKey;
    }
    public String getGameId(){
        return gameId;
    }   

    public void setScore(int score){
        score = score;
    }
    public void setQuizTiming(long quiz_timing){
        quiz_timing = quiz_timing;
    }
    public void setBlobKey(String blobKey){
        blobKey = blobKey;
    }
    public void setGameId(String gameId){
        gameId = gameId;
    }  
  }