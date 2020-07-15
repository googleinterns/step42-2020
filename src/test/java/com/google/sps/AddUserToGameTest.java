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

/** tests the addUserToGame function */
@RunWith(JUnit4.class)
public final class AddUserToGameTest {
  
  private DatastoreService datastore;

  // helper variable allows the use of entities in testing 
  private final LocalServiceTestHelper helper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setUp() {
    helper.setUp();
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

// Given an empty string for user id, the function should return false
  @Test
  public void emptyUserIdFails() {

    Entity gameEntity = new Entity("Game");
    ArrayList<String> userIds = new ArrayList<>();
    gameEntity.setProperty("userIds", userIds);

    boolean actual = GameUtils.addUserToGame("", "1", gameEntity, datastore);

    Assert.assertEquals(false, actual);
  }

  // Given an empty string for username, the function should return false
  @Test
  public void emptyUserNameFails() {

    Entity gameEntity = new Entity("Game");
    ArrayList<String> userIds = new ArrayList<>();
    gameEntity.setProperty("userIds", userIds);

    boolean actual = GameUtils.addUserToGame("user1", "", gameEntity, datastore);

    Assert.assertEquals(false, actual);
  }

  // Given a null for datastore instance, the function should return false
  @Test
  public void nullDatastoreFails() {

    Entity gameEntity = new Entity("Game");
    ArrayList<String> userIds = new ArrayList<>();
    gameEntity.setProperty("userIds", userIds);

    boolean actual = GameUtils.addUserToGame("user1", "1", gameEntity, null);

    Assert.assertEquals(false, actual);
  }

  // Given a null for game entity, the function should return false
  @Test
  public void nullGameEntityFails() {

    boolean actual = GameUtils.addUserToGame("user1", "1", null, datastore);

    Assert.assertEquals(false, actual);
  }

  // Given an game entity without a username list, the function should return false
  @Test
  public void invalidGameFails() {

    Entity gameEntity = new Entity("Game");
    gameEntity.setProperty("gameName", "game1");

    boolean actual = GameUtils.addUserToGame("user1", "1", gameEntity, datastore);

    Assert.assertEquals(false, actual);
  }
 
  // given a valid parameters, adds user to game, creates a score entity and returns true
  @Test
  public void addUserToGameSuccess() {

    Entity gameEntity = new Entity("Game");
    ArrayList<String> userIds = new ArrayList<>();
    gameEntity.setProperty("userIds", userIds);

    boolean actual = GameUtils.addUserToGame("user1", "1", gameEntity, datastore);

    Assert.assertEquals(true, actual);
  }
}