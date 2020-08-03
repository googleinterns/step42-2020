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
 
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import java.util.ArrayList;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.plantasy.utils.UserUtils;
import com.google.plantasy.utils.Game;
import java.util.logging.Logger;
 
public final class GameUtils {
 
  private static final Logger log = Logger.getLogger(GameUtils.class.getName());
 
  /**
  * Creates a game entity and returns the entity if successful
  */
  public static Game createGame(String gameName, DatastoreService datastore) {
    
    // intializing game property values
    Entity gameEntity = new Entity("Game");
    Game game = new Game(gameEntity);
    ArrayList<String> userIds = new ArrayList<String>();
    String quizQuestion = "";
    long quiz_timestamp = 0;

    game.setGameName(gameName);
    game.setUserIds(userIds);
    game.setQuizQuestion(quizQuestion);
    game.setQuizTimestamp(quiz_timestamp);

    datastore.put(game.getGameEntity());
    return game;
  }
 
  /**
  * add user to user list in game entity, and creates a score entity for the given user and game
  */
  public static boolean addUserToGame(String userId, Entity gameEntity, DatastoreService datastore) {
 
    if(userId == ""){
      return false;
    }

    Game game = new Game(gameEntity);

    ArrayList<String> userIds = game.getUserIds();
    if(userIds == null){
      userIds = new ArrayList<String>();
      game.setUserIds(userIds);
    }
 
    // add user to game entity
    userIds.add(userId);
    datastore.put(game.getGameEntity());
    
    return true;
  }
 
  /**
    Connects the given game entity to the given user entity by calling the addUserToGame
    and addGameToUser functions
  */
  public static boolean setGame(Entity userEntity, DatastoreService datastore, Game game) {
 
    // add user to game entity + vice versa
    boolean userAdded = GameUtils.addUserToGame((String) userEntity.getProperty("userID"), game.getGameEntity(), datastore);
    if(!userAdded){
        log.severe("failed to add user to game " + game.getGameName());
        return false;
    }
    boolean gameAdded = UserUtils.addGameToUser(userEntity, datastore, game.getGameId());
    if(!gameAdded){
        log.severe("failed to add game "  + game.getGameName() + " to user");
        return false;
    }
    
    return true;
  }
 
}
