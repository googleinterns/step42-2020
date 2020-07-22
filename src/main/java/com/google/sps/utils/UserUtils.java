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

package com.google.sps.utils;

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

public final class UserUtils {
    public static final String SESSION_ID_COOKIE_NAME = "SessionID";
    private static final Logger log = Logger.getLogger(UserUtils.class.getName());

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

    if(userEntity == null){
        log.severe("found null user entity trying to add game to user");
        return false;
    }
    if(datastore == null){
        log.severe("found null datastore trying to add game to user " + (String) userEntity.getProperty("userID"));
        return false;
    }
    if(gameId == ""){
        log.severe("found empty gameId trying to add game to user " + (String) userEntity.getProperty("userID"));
        return false;
    }

    userEntity.setProperty("gameId", gameId);
    datastore.put(userEntity);
 
    return true;
  }

  /**
  * adds a photo to the user entity
  */
  public static boolean addBlobKey(String blobKey, Entity userEntity, DatastoreService datastore) {

    if(userEntity == null){
        log.severe("found null user entity trying to add blobkey to user");
        return false;
    }
    if(datastore == null){
        log.severe("found null datastore trying to add blobkey to user " + (String) userEntity.getProperty("userID"));
        return false;
    }
    if(blobKey == ""){
        log.severe("found empty blobkey trying to add blobkey to user " + (String) userEntity.getProperty("userID"));
        return false;
    }
    
    userEntity.setProperty("blobKey", blobKey);
    datastore.put(userEntity);
 
    return true; 
  }

  /**
    adds a specified number of points to the user's points
  */
  public static void addPoints(Entity userEntity, int numPoints, DatastoreService datastore){
    try {
      userEntity.setProperty("score", ((Number) userEntity.getProperty("score")).intValue() + numPoints);
    } catch (NullPointerException e) {
      userEntity.setProperty("score", numPoints);                
    }
    datastore.put(userEntity);
  }
}
