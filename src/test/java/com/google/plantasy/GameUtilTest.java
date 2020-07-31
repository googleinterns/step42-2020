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

package com.google.plantasy;

import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.plantasy.utils.GameUtils;
import com.google.plantasy.utils.Game;

/** tests the game util functions */
@RunWith(JUnit4.class)
public final class GameUtilTest {

  // helper variable allows the use of entities in testing 
  private final LocalServiceTestHelper helper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setUp() {
    helper.setUp();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  // Test create game with an empty string for game name
  @Test
  public void createGame_EmptyGameName() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Game actual = GameUtils.createGame("", datastore);

    Assert.assertEquals(null, actual);
  }

  // Test create game with null for datastore instance
  @Test
  public void createGame_NullDatastore() {

    Game actual = GameUtils.createGame("game", null);

    Assert.assertEquals(null, actual);
  }

  // Test create game with valid datastore and gamename
  @Test
  public void createGame_AllValid() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Game game = GameUtils.createGame("game", datastore);

    long timestamp = 0;

    Assert.assertEquals("game", game.getGameName());
    Assert.assertEquals("", game.getQuizQuestion());
    Assert.assertEquals(timestamp, game.getQuizTimestamp());
  }

  // Test addUserToGame with an empty string for user id
  @Test
  public void addUserToGame_EmptyUserId() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity gameEntity = new Entity("Game");
    ArrayList<String> userIds = new ArrayList<>();
    gameEntity.setProperty("userIds", userIds);

    boolean actual = GameUtils.addUserToGame("", gameEntity, datastore);

    Assert.assertEquals(false, actual);
  }

  // Test addUserToGame with null for datastore instance
  @Test
  public void addUserToGame_NullDatastore() {

    Entity gameEntity = new Entity("Game");
    ArrayList<String> userIds = new ArrayList<>();
    gameEntity.setProperty("userIds", userIds);

    boolean actual = GameUtils.addUserToGame("user1", gameEntity, null);

    Assert.assertEquals(false, actual);
  }

  // Test addUserToGame with a null for game entity
  @Test
  public void addUserToGame_NullGameEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    boolean actual = GameUtils.addUserToGame("user1", null, datastore);

    Assert.assertEquals(false, actual);
  }

  // Test addUserToGame with a game entity without an initialized username list
  @Test
  public void addUserToGame_InvalidGame() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity gameEntity = new Entity("Game");
    gameEntity.setProperty("gameName", "game1");
    Game game = new Game(gameEntity);

    boolean actual = GameUtils.addUserToGame("user1", gameEntity, datastore);

    Assert.assertEquals("user1", game.getUserIds().get(0));
    Assert.assertEquals(true, actual);
  }
 
  // Test addUserToGame with valid parameters
  @Test
  public void addUserToGame_Success() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity gameEntity = new Entity("Game");
    ArrayList<String> userIds = new ArrayList<>();
    gameEntity.setProperty("userIds", userIds);
    Game game = new Game(gameEntity);

    boolean actual = GameUtils.addUserToGame("user1", gameEntity, datastore);

    Assert.assertEquals("user1", game.getUserIds().get(0));
    Assert.assertEquals(true, actual);
  }

  // Test setGame with null datastore 
  @Test
  public void setGame_NullDatastore() {

    Entity userEntity = new Entity("user");
    userEntity.setProperty("userID", "user1");
    Entity gameEntity = new Entity("Game");
    Game game = new Game(gameEntity);

    boolean actual = false;
    try{
        actual = GameUtils.setGame(userEntity, null, game);
    }catch(NullPointerException e){
        Assert.assertEquals(false, actual);
    }
  }

  // Test setGame with null user entity
  @Test
  public void joinGame_NullUserEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity gameEntity = new Entity("Game");
    Game game = new Game(gameEntity);

    boolean actual = false;
    try{
        actual = GameUtils.setGame(null, datastore, game);
    }catch(NullPointerException e){
        Assert.assertEquals(false, actual);
    }
  }

  // Test setGame with null game Entity 
  @Test
  public void setGame_NullGameEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity userEntity = new Entity("user");
    userEntity.setProperty("userID", "user1");

    boolean actual = false;
    try{
        actual = GameUtils.setGame(userEntity, datastore, null);
    }catch(NullPointerException e){
        Assert.assertEquals(false, actual);
    }
  }

  // Test setGame with valid parameters 
  @Test
  public void setGame_NewGameSuccess() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity userEntity = new Entity("user");
    userEntity.setProperty("userID", "user1");
    Game game = GameUtils.createGame("game1", datastore);

    boolean actual = GameUtils.setGame(userEntity, datastore, game);

    Assert.assertEquals(true, actual);
  }
}