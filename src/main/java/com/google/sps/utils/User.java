  package com.google.sps.utils;
  
  
  /** A user and the user's current score */
  public final class User {
    public static String userID;
    public static String userName;
    public static long score;
 
    public User(String userID, String userName, long score) {
        this.userID = userID;
        this.userName = userName;
        this.score = score;
    }
  }