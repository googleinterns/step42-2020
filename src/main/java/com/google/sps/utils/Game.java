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
 
    public void setGameId(String gameId) {
        entity.setProperty("gameId", gameId);
        datastore.put(entity);
    }
 
    public void setGameName(String game_name) {
        entity.setProperty("gameName", game_name);
        datastore.put(entity);
    }
 
    public void setQuizQuestion(String quiz_question) {
        entity.setProperty("quizQuestion", quiz_question);
        datastore.put(entity);
    }
 
    public void setQuizTimestamp(long quiz_timestamp) {
        entity.setProperty("quiz_timestamp", quiz_timestamp);
        datastore.put(entity);
    }
 
    public void setUserIds(ArrayList<String> userIds) {
        entity.setProperty("userIds", userIds);
        datastore.put(entity);
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
