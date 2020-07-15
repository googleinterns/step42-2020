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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.sps.utils.UserUtils;

/** tests the IsValidGameName function */
@RunWith(JUnit4.class)
public final class UserUtilTest {
  
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

  // given a user id and a datastore instance, the function should return the entity for the given user
  @Test
  public void findEntityByUserId() {
    UserUtils userUtils = new UserUtils();
 
    Entity userEntity = new Entity("Game");
    userEntity.setProperty("userID", "123");
    datastore.put(userEntity);

    Entity userEntity2 = new Entity("Game");
    userEntity2.setProperty("userID", "456");
    datastore.put(userEntity2);

    Entity userEntity3 = new Entity("Game");
    userEntity3.setProperty("userID", "789");
    datastore.put(userEntity3);

    Entity actual = userUtils.getEntityFromDatastore("Game","userID","123", datastore);
    Entity expected = userEntity;

    Assert.assertEquals(expected, actual);
  }

  // given a user id and an empty datastore instance, the function should return null
  @Test
  public void findEntityWithEmptyDatastore() {
    UserUtils userUtils = new UserUtils();
    
    Entity actual = userUtils.getEntityFromDatastore("Game","userID","123", datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual);
  }

  // given a user id that isn't in datastore and a datastore instance, the function should return null
  @Test
  public void UserIdNotInDatastore() {
    UserUtils userUtils = new UserUtils();
    
    Entity userEntity = new Entity("Game");
    userEntity.setProperty("userID", "123");
    datastore.put(userEntity);

    Entity userEntity2 = new Entity("Game");
    userEntity2.setProperty("userID", "456");
    datastore.put(userEntity2);

    Entity userEntity3 = new Entity("Game");
    userEntity3.setProperty("userID", "789");
    datastore.put(userEntity3);

    Entity actual = userUtils.getEntityFromDatastore("Game","userID","000", datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual);
  }

  // given an empty string and a datastore instance, the function should return null
  @Test
  public void EmptyStringUserId() {
    UserUtils userUtils = new UserUtils();
    
    Entity userEntity = new Entity("Game");
    userEntity.setProperty("userID", "123");
    datastore.put(userEntity);

    Entity userEntity2 = new Entity("Game");
    userEntity2.setProperty("userID", "456");
    datastore.put(userEntity2);

    Entity userEntity3 = new Entity("Game");
    userEntity3.setProperty("userID", "789");
    datastore.put(userEntity3);

    Entity actual = userUtils.getEntityFromDatastore("Game","userID","", datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual);
  }

  // given null instead of datastore, the function should return null
  @Test
  public void nullDatastore() {
    UserUtils userUtils = new UserUtils();
    
    Entity actual = userUtils.getEntityFromDatastore("Game","userID","123", null);
    Entity expected = null;

    Assert.assertEquals(expected, actual);
  }
  
  //without given a entity class, the function should return null
  @Test
  public void nullUserEntity(){
      UserUtils userUtils = new UserUtils();
    
      Entity actual = userUtils.getEntityFromDatastore("","userID","123", datastore);
      Entity expected = null;
      Assert.assertEquals(expected, actual);
  }

  //without given a entity title, the function should return null
  @Test
  public void nullUserEntityTitle(){
      UserUtils userUtils = new UserUtils();
    
      Entity actual = userUtils.getEntityFromDatastore("Game","","123", datastore);
      Entity expected = null;
      Assert.assertEquals(expected, actual);
  }

    // Given an empty string for game id, addGameToUser should return false
  @Test
  public void emptyGameIdFails() {

    Entity userEntity = new Entity("User");
    ArrayList<String> gameIds = new ArrayList<>();
    userEntity.setProperty("games", gameIds);

    boolean actual = UserUtils.addGameToUser(userEntity, datastore, "");

    Assert.assertEquals(false, actual);
  }

  // Given a null for datastore instance, addGameToUser should return false
  @Test
  public void nullDatastoreFails() {

    Entity userEntity = new Entity("User");
    ArrayList<String> gameIds = new ArrayList<>();
    userEntity.setProperty("games", gameIds);

    boolean actual = UserUtils.addGameToUser(userEntity, null, "gameId");

    Assert.assertEquals(false, actual);
  }

  // Given a null for user entity, addGameToUser should return false
  @Test
  public void nullUserEntityFails() {

    boolean actual = UserUtils.addGameToUser(null, datastore, "gameId");

    Assert.assertEquals(false, actual);
  }

  // Given an user entity without a gameid list, addGameToUser should return false
  @Test
  public void invalidUserFails() {

    Entity userEntity = new Entity("User");
    userEntity.setProperty("userName", "user1");

    boolean actual = UserUtils.addGameToUser(userEntity, datastore, "gameId");

    Assert.assertEquals(false, actual);
  }
 
  // given all valid parameters, addGameToUser adds game to user entity and returns true
  @Test
  public void addGameToUserSuccess() {

    Entity userEntity = new Entity("User");
    ArrayList<String> gameIds = new ArrayList<>();
    userEntity.setProperty("games", gameIds);

    boolean actual = UserUtils.addGameToUser(userEntity, datastore, "gameId");

    Assert.assertEquals(true, actual);
  }

    // Given an empty string for blob key, addBlobKey should return false
  @Test
  public void emptyBlobKeyFails() {

    Entity userEntity = new Entity("User");

    boolean actual = UserUtils.addBlobKey("", userEntity, datastore);

    Assert.assertEquals(false, actual);
  }

  // Given a null for datastore instance, addBlobKey should return false
  @Test
  public void blobKeyNullDatastoreFails() {

    Entity userEntity = new Entity("User");

    boolean actual = UserUtils.addBlobKey("blobkey", userEntity, null);

    Assert.assertEquals(false, actual);
  }

  // Given a null for user entity, addBlobKey should return false
  @Test
  public void blobKeyNullUserEntityFails() {

    boolean actual = UserUtils.addBlobKey("blobkey", null, datastore);

    Assert.assertEquals(false, actual);
  }
 
  // given a valid blobkey, datastore and user entity, addBlobKey adds the blobkey to the user and returns true
  @Test
  public void addBlobKeySucess() {

    Entity userEntity = new Entity("User");

    boolean actual = UserUtils.addBlobKey("blobkey", userEntity, datastore);

    Assert.assertEquals(true, actual);
  }
}
