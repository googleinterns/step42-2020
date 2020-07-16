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

package com.google.sps;

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
import com.google.sps.utils.GameUtils;

/** tests the game util functions */
@RunWith(JUnit4.class)
public final class GameUtilTest {

  private static String userID1 = "1";
  private static String userID2 = "2";

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

  //  Test where user has no games
  @Test
  public void isValidGame_UserHasNoGames() {

    Entity userEntity2 = new Entity("user");
    ArrayList<String> gameNames2 = new ArrayList<>();

    userEntity2.setProperty("gameNames",gameNames2);
    userEntity2.setProperty("userID",userID2);

    boolean actual = GameUtils.IsValidGameName("game1", userEntity2);

    Assert.assertEquals(true, actual);
  }

  // Test a game name the user already has 
  @Test
  public void isValidGame_UserHasDuplicateGameName() {

    Entity userEntity1 = new Entity("user");
    ArrayList<String> gameNames1 = new ArrayList<>();

    gameNames1.add("game1");
    gameNames1.add("game2");
    gameNames1.add("Game3");
    userEntity1.setProperty("gameNames",gameNames1);
    userEntity1.setProperty("userID",userID1);

    boolean actual = GameUtils.IsValidGameName("game1", userEntity1);

    Assert.assertEquals(false, actual);
  }

  // The user has games, but the given game name is new
  @Test
  public void isValidGame_NewGameName() {

    Entity userEntity1 = new Entity("user");
    ArrayList<String> gameNames1 = new ArrayList<>();
    
    gameNames1.add("game1");
    gameNames1.add("game2");
    gameNames1.add("Game3");
    userEntity1.setProperty("gameNames",gameNames1);
    userEntity1.setProperty("userID",userID1);

    boolean actual = GameUtils.IsValidGameName("game4", userEntity1);

    Assert.assertEquals(true, actual);
  }

  // The user has games, and the given name is a duplicate name with different capitalization
  @Test
  public void isValidGame_SameNameDifferentCapitalization() {

    Entity userEntity1 = new Entity("user");
    ArrayList<String> gameNames1 = new ArrayList<>();
    
    gameNames1.add("game1");
    gameNames1.add("game2");
    gameNames1.add("Game3");
    userEntity1.setProperty("gameNames",gameNames1);
    userEntity1.setProperty("userID",userID1);

    boolean actual = GameUtils.IsValidGameName("Game1", userEntity1);

    Assert.assertEquals(true, actual);
  }

  // Test IsValidGameName with a blank game name
  @Test
  public void isValidGame_EmptyGameName() {

    Entity userEntity1 = new Entity("user");
    ArrayList<String> gameNames1 = new ArrayList<>();
    
    gameNames1.add("game1");
    gameNames1.add("game2");
    gameNames1.add("game3");
    userEntity1.setProperty("gameNames",gameNames1);
    userEntity1.setProperty("userID",userID1);

    boolean actual = GameUtils.IsValidGameName("", userEntity1);

    Assert.assertEquals(false, actual);
  }

  // Test IsValidGameName with a null user entity
  @Test
  public void isValidGame_NullUserEntity() {

    boolean actual = GameUtils.IsValidGameName("game1", null);

    Assert.assertEquals(false, actual);
  }

  // Test create game with an empty string for game name
  @Test
  public void createGame_EmptyGameName() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity actual = GameUtils.createGameEntity("", datastore);

    Assert.assertEquals(null, actual);
  }

  // Test create game with null for datastore instance
  @Test
  public void createGame_NullDatastore() {

    Entity actual = GameUtils.createGameEntity("game", null);

    Assert.assertEquals(null, actual);
  }

  // Test create game with valid datastore and gamename
  @Test
  public void createGame_AllValid() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity actual = GameUtils.createGameEntity("game", datastore);

    long timestamp = 0;

    Assert.assertEquals("game", actual.getProperty("gameName"));
    Assert.assertEquals("", actual.getProperty("quizQuestion"));
    Assert.assertEquals(timestamp, actual.getProperty("quiz_timestamp"));
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

    boolean actual = GameUtils.addUserToGame("user1", gameEntity, datastore);

    Assert.assertEquals(false, actual);
  }
 
  // Test addUserToGame with valid parameters
  @Test
  public void addUserToGame_Success() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity gameEntity = new Entity("Game");
    ArrayList<String> userIds = new ArrayList<>();
    gameEntity.setProperty("userIds", userIds);

    boolean actual = GameUtils.addUserToGame("user1", gameEntity, datastore);

    Assert.assertEquals(true, actual);
  }
}