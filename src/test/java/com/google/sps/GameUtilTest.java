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
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.sps.utils.GameUtils;

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

  // Test create game with null for datastore instance
  @Test
  public void createGame_NullDatastore() {

    Assertions.assertThrows(NullPointerException.class, () -> {
        Entity actual = GameUtils.createGameEntity("game", null);
    });
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

    Assertions.assertThrows(NullPointerException.class, () -> {
        boolean actual = GameUtils.addUserToGame("user1", gameEntity, null);
    });
  }

  // Test addUserToGame with a null for game entity
  @Test
  public void addUserToGame_NullGameEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Assertions.assertThrows(NullPointerException.class, () -> {
        boolean actual = GameUtils.addUserToGame("user1", null, datastore);
    });
  }

  // Test addUserToGame with a game entity without an initialized username list
  @Test
  public void addUserToGame_InvalidGame() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity gameEntity = new Entity("Game");
    gameEntity.setProperty("gameName", "game1");

    boolean actual = GameUtils.addUserToGame("user1", gameEntity, datastore);

    Assert.assertEquals(true, actual);
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

  // Test setGame with null datastore 
  @Test
  public void setGame_NullDatastore() {

    Entity userEntity = new Entity("User");
    userEntity.setProperty("userID", "user1");
    Entity gameEntity = new Entity("Game");

    Assertions.assertThrows(NullPointerException.class, () -> {
        boolean actual = GameUtils.setGame(userEntity, null, gameEntity);
    });
  }

  // Test setGame with null user entity
  @Test
  public void joinGame_NullUserEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity gameEntity = new Entity("Game");

    Assertions.assertThrows(NullPointerException.class, () -> {
        boolean actual = GameUtils.setGame(null, datastore, gameEntity);
    });
  }

  // Test setGame with null game Entity 
  @Test
  public void setGame_NullGameEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity userEntity = new Entity("User");
    userEntity.setProperty("userID", "user1");

    Assertions.assertThrows(NullPointerException.class, () -> {
        boolean actual = GameUtils.setGame(userEntity, datastore, null);
    });
  }

  // Test setGame with valid parameters 
  @Test
  public void setGame_NewGameSuccess() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity userEntity = new Entity("User");
    userEntity.setProperty("userID", "user1");
    Entity gameEntity = GameUtils.createGameEntity("game1", datastore);

    boolean actual = GameUtils.setGame(userEntity, datastore, gameEntity);

    Assert.assertEquals(true, actual);
  }
}