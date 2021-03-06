// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.plantasy.utils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.util.List;
import java.util.ArrayList;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import java.util.logging.Logger;
import com.google.plantasy.utils.User;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.plantasy.utils.QuizTimingPropertiesUtils;
import java.lang.IllegalArgumentException;
import java.util.Comparator;
import java.util.Collections;

public final class UserUtils {
    public static final String SESSION_ID_COOKIE_NAME = "SessionID";
    public static final int ADDED_POINTS = 20;
    private static final Logger log = Logger.getLogger(UserUtils.class.getName());
 
    /**
      This comparator sorts users by score in descending order
    */
    static final Comparator<User> RANK = new Comparator<User>() {
        @Override
        public int compare(User a, User b) {
            return Long.compare(b.getScore(), a.getScore());
        }
    };

    /**
    * Takes user's information and creates an entity from it. 
    * This function inputs all the parameters, while also initializing
    * some parameters to null values. The entity returned can be stored in the
    * datastore.
    * 
    * @param  name          the user's name
    * @param  sessionID     the user's current session id (should match with a cookie client-side)
    * @return               a single entity that has contains all the parameters passed in. 
    */

public static Entity initializeUser(String userId, String name, String sessionID){
        //these values are always empty upon initialization
        Entity userEntity = new Entity("user");
        userEntity.setProperty("username",name);
        userEntity.setProperty("userID",userId);
        userEntity.setProperty("quiz_timestamp", 0L);
        userEntity.setProperty("gameId", "");
        userEntity.setProperty("blobKey", null);
        userEntity.setProperty("score", 0);
        userEntity.setProperty("lastAwardedUploadPoints", 0L);
        return userEntity;
  }
 
     /**
    * Returns a single Entity object that can then be used in a servlet. 
    * The entityPropertyTitle and entityPropertyValue
    * arguments are relative to the entityName argument. 
    * <p>
    * This method always returns immediately, whether or not the 
    * datastore object exists. If the object does not exist, the entity 
    * returned is null.
    * 
    * @param  entityName           the name of an entity class in the datastore (ex.user, game)
    * @param  entityPropertyTitle  the name of a specific property that the entity class has (usually sessionID or userID)
    * @param  entityPropertyValue  the value of the property that is filtered for
    * @return                      a single entity that has the same value for the property in the parameter 
    */
    
  public static Entity getEntityFromDatastore(String entityName, String entityPropertyTitle, String entityPropertyValue, DatastoreService datastore) {

    if(entityPropertyValue == "" || entityName == "" || entityPropertyTitle == "" || datastore == null){
        return null;
    }
 
    Filter queryFilter = new FilterPredicate(entityPropertyTitle, FilterOperator.EQUAL, entityPropertyValue);
    Query query = new Query(entityName).setFilter(queryFilter);
     PreparedQuery results = datastore.prepare(query);
     List<Entity> resultsList = results.asList(FetchOptions.Builder.withLimit(1));
     if(resultsList.size() == 0){
        return null;
     }
     Entity entity = resultsList.get(0);
     return entity;
  }
 
     /**
    * This function takes in a list of cookies and matches a sessionID cookie
    * with a specific name/value pair to a user entity with the same name/value
    * pair as one of its properties. This user entity is then returned. 
    * 
    * <p>
    * 
    * @param  cookies    an array of cookies (usually all cookies on front end)
    * @param  datastore  the database where entities are stored
    * @return            a single entity that has the cookie name/value pair as a property
    */

  public static Entity getUserFromCookie(Cookie cookies[], DatastoreService datastore){
      if(datastore == null){
          log.severe("Error in function getUserFromCookie(): Datastore passed in was null");
          return null;
      }
    Cookie cookie = CookieUtils.getCookieFromName(cookies, SESSION_ID_COOKIE_NAME); //returns null if it doesn't exist
    if(cookie == null){ 
        log.info("Error in function getUserFromCookie(): Cookie with name: " + SESSION_ID_COOKIE_NAME + " not found.");
        return null;
    }
    return getEntityFromDatastore("user", SESSION_ID_COOKIE_NAME, cookie.getValue(), datastore); //returns null if it doesn't exist
  }

  /**
  * Adds a game id to a user's list of games
  */
  public static boolean addGameToUser(Entity userEntity, DatastoreService datastore, String gameId) {

    if(gameId == ""){
        log.severe("found empty gameId trying to add game to user " + (String) userEntity.getProperty("userID"));
        return false;
    }

    User user = new User(userEntity);
    user.setGame(gameId);
    datastore.put(user.getEntity());
 
    return true;
  }

  /**
  * adds a photo to the user entity
  */
  public static boolean addBlobKey(String blobKey, Entity userEntity, DatastoreService datastore) {
    
    if(blobKey == ""){
        log.severe("found empty blobkey trying to add blobkey to user " + (String) userEntity.getProperty("userID"));
        return false;
    }
    
    User user = new User(userEntity);
    user.setBlobKey(blobKey);
    datastore.put(user.getEntity());

    return true; 
  }

  /**
    adds a specified number of points to the user's points
  */
  public static void addPoints(User user, int numPoints, DatastoreService datastore){

    try {
      user.setScore(user.getScore() + numPoints);
    } catch (NullPointerException e) {
      user.setScore(numPoints);                
    }
    datastore.put(user.getEntity());
  }

  /**
    gets the users of a particular game to populate the leaderboard
  */
  public static ArrayList<User> userList(String gameId, DatastoreService datastore){
    //create and prepare a query
    Filter queryFilter = new FilterPredicate("gameId", FilterOperator.EQUAL, gameId);
    Query query = new Query("user").setFilter(queryFilter);
    PreparedQuery results = datastore.prepare(query);
    
    // stores each user in a User object 
    ArrayList<User> users = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
        User user = new User(entity);
        users.add(user);
    }

    Collections.sort(users, RANK);
    return users;
  }

  /**
    Adds 20 points to a user for uploading if it has been more than a day since they last uploaded
  */
  public static void addUploadPoints(Entity userEntity, DatastoreService datastore){

    User user = new User(userEntity);
    if(user.getLastAwardedUploadPoints() == 0L || QuizTimingPropertiesUtils.isTimestampOutdated(user.getLastAwardedUploadPoints())){
        addPoints(user, ADDED_POINTS, datastore);
        user.setLastAwardedUploadPoints(System.currentTimeMillis());
        datastore.put(user.getEntity());
    }
  }
}
 
