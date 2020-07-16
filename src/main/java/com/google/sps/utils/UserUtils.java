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

    private static final Logger log = Logger.getLogger(GameUtils.class.getName());

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
  * Adds a game id to a user's list of games
  */
  public static boolean addGameToUser(Entity userEntity, DatastoreService datastore, String gameId) {

    if(userEntity == null){
        log.severe("null user entity");
        return false;
    }
    if(datastore == null){
        log.severe("null datastore");
        return false;
    }
    if(gameId == ""){
        log.severe("found empty gameId trying to add game to user" + (String) userEntity.getProperty("userId"));
        return false;
    }
 
    ArrayList<String> games = (ArrayList<String>) userEntity.getProperty("games");
 
    if(games == null){
        return false;
    }
 
    games.add(gameId);
    userEntity.setProperty("games", games);
    datastore.put(userEntity);
 
    return true;
  }

  /**
  * adds a photo to the user entity
  */
  public static boolean addBlobKey(String blobKey, Entity userEntity, DatastoreService datastore) {

    if(userEntity == null){
        log.severe("null user entity");
        return false;
    }
    if(datastore == null){
        log.severe("null datastore");
        return false;
    }
    if(blobKey == ""){
        log.severe("found empty blobkey trying to add blobkey to user" + (String) userEntity.getProperty("userId"));
        return false;
    }
    
    userEntity.setProperty("blobKey", blobKey);
    datastore.put(userEntity);
 
    return true; 
  }
}
