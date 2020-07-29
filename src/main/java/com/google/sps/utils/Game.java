package com.google.sps.utils;
import com.google.appengine.api.datastore.Entity;
import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.ArrayList;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public final class Game {
    DatastoreService datastore;
    private Entity entity;

    public Game() {
        datastore = DatastoreServiceFactory.getDatastoreService();
        entity = new Entity("Game");
    }

    //Sets the individual game Id for a game entity
    public void setGameId(String gameId) {
        entity.setProperty("gameId", gameId);
    }

    //Sets the game name for the game entity
    public void setGameName(String game_name) {
        entity.setProperty("gameName", game_name);
    }

    //sets the quiz question for the game entity
    public void setQuizQuestion(String quiz_question) {
        entity.setProperty("quizQuestion", quiz_question);
    }

    //sets the timestamp of the quiz in the game entity 
    public void setQuizTimestamp(long quiz_timestamp) {
        entity.setProperty("quiz_timestamp", quiz_timestamp);
    }

    //sets a list of users that are apart of a game 
    public void setUserIds(ArrayList<String> userIds) {
        entity.setProperty("userIds", userIds);
    }

    public Entity getGameEntity() {
        return entity;
    }

    public String getGameId() {
        return (String) entity.getProperty("gameId");
    }

    public String getGameName() {
        return (String) entity.getProperty("gameName");
    }

    public String getQuizQuestion() {
        return (String) entity.getProperty("quizQuestion");
    }

    public long getQuizTimestamp() {
        return (long) entity.getProperty("quiz_timestamp");
    }

    public ArrayList<String> getUserIds() {
        return (ArrayList<String>) entity.getProperty("userIds");
    }

}