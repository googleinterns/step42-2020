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
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.sps.utils.UserUtils;

/** tests the user util functions */
@RunWith(JUnit4.class)
public final class UserUtilTest {

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

  // Test a working user id and a datastore instance
  @Test
  public void findEntityByUserId() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 
    Entity userEntity = new Entity("Game");
    userEntity.setProperty("userID", "123");
    datastore.put(userEntity);

    Entity userEntity2 = new Entity("Game");
    userEntity2.setProperty("userID", "456");
    datastore.put(userEntity2);

    Entity userEntity3 = new Entity("Game");
    userEntity3.setProperty("userID", "789");
    datastore.put(userEntity3);

    Entity actual = UserUtils.getEntityFromDatastore("Game","userID","123", datastore);
    Entity expected = userEntity;

    Assert.assertEquals(expected, actual);
  }

  // Test id and an empty datastore instance
  @Test
  public void findEntityWithEmptyDatastore() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Entity actual = UserUtils.getEntityFromDatastore("Game","userID","123", datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual);
  }

  // Test a user id that isn't in datastore and a datastore instance
  @Test
  public void findEntityWithUserIdNotInDatastore() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Entity userEntity = new Entity("Game");
    userEntity.setProperty("userID", "123");
    datastore.put(userEntity);

    Entity userEntity2 = new Entity("Game");
    userEntity2.setProperty("userID", "456");
    datastore.put(userEntity2);

    Entity userEntity3 = new Entity("Game");
    userEntity3.setProperty("userID", "789");
    datastore.put(userEntity3);

    Entity actual = UserUtils.getEntityFromDatastore("Game","userID","000", datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual);
  }

  // Test an empty string for EntityPropertyValue and a datastore instance
  @Test
  public void findEntityWithEmptyStringUserId() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Entity userEntity = new Entity("Game");
    userEntity.setProperty("userID", "123");
    datastore.put(userEntity);

    Entity userEntity2 = new Entity("Game");
    userEntity2.setProperty("userID", "456");
    datastore.put(userEntity2);

    Entity userEntity3 = new Entity("Game");
    userEntity3.setProperty("userID", "789");
    datastore.put(userEntity3);

    Entity actual = UserUtils.getEntityFromDatastore("Game","userID","", datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual);
  }

  // Test null instance of datastore
  @Test
  public void findEntityWithNullDatastore() {
    
    Entity actual = UserUtils.getEntityFromDatastore("Game","userID","123", null);
    Entity expected = null;

    Assert.assertEquals(expected, actual);
  }
  
  // test without an entity class
  @Test
  public void findEntityWithNullUserEntity(){
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
      Entity actual = UserUtils.getEntityFromDatastore("","userID","123", datastore);
      Entity expected = null;
      Assert.assertEquals(expected, actual);
  }

  // test without an entity title
  @Test
  public void findEntityWithNullUserEntityTitle(){
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
      Entity actual = UserUtils.getEntityFromDatastore("Game","","123", datastore);
      Entity expected = null;
      Assert.assertEquals(expected, actual);
  }

  // test an empty string for game id
  @Test
  public void addGameToUserEmptyGameId() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity userEntity = new Entity("user");
    ArrayList<String> gameIds = new ArrayList<>();
    userEntity.setProperty("games", gameIds);

    boolean actual = UserUtils.addGameToUser(userEntity, datastore, "");

    Assert.assertEquals(false, actual);
  }

  // test a null for datastore instance
  @Test
  public void addGameToUserNullDatastore() {

    Entity userEntity = new Entity("user");
    ArrayList<String> gameIds = new ArrayList<>();
    userEntity.setProperty("games", gameIds);

    boolean actual = UserUtils.addGameToUser(userEntity, null, "gameId");

    Assert.assertEquals(false, actual);
  }

  // test a null for user entity
  @Test
  public void addGameToUserNullUserEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    boolean actual = UserUtils.addGameToUser(null, datastore, "gameId");

    Assert.assertEquals(false, actual);
  }

  // test an user entity without a gameid list
  @Test
  public void addGameToUserInvalidUser() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity userEntity = new Entity("user");
    userEntity.setProperty("userName", "user1");

    boolean actual = UserUtils.addGameToUser(userEntity, datastore, "gameId");

    Assert.assertEquals(false, actual);
  }
 
  // test given all correct valid parameters
  @Test
  public void addGameToUserSuccess() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity userEntity = new Entity("user");
    ArrayList<String> gameIds = new ArrayList<>();
    userEntity.setProperty("games", gameIds);

    boolean actual = UserUtils.addGameToUser(userEntity, datastore, "gameId");

    Assert.assertEquals(true, actual);
  }

  // test an empty string for blob key
  @Test
  public void emptyBlobKeyFails() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity userEntity = new Entity("user");

    boolean actual = UserUtils.addBlobKey("", userEntity, datastore);

    Assert.assertEquals(false, actual);
  }

  // test null for datastore instance
  @Test
  public void blobKeyNullDatastoreFails() {

    Entity userEntity = new Entity("user");

    boolean actual = UserUtils.addBlobKey("blobkey", userEntity, null);

    Assert.assertEquals(false, actual);
  }

  // test null for user entity
  @Test
  public void blobKeyNullUserEntityFails() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    boolean actual = UserUtils.addBlobKey("blobkey", null, datastore);

    Assert.assertEquals(false, actual);
  }
 
  // test a valid blobkey, datastore and user entity
  @Test
  public void addBlobKeySucess() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity userEntity = new Entity("user");

    boolean actual = UserUtils.addBlobKey("blobkey", userEntity, datastore);

    Assert.assertEquals(true, actual);
  }
  //Test given a list of entities, and a list of cookies
  @Test
  public void findEntityByCookie(){
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      Entity user1 = new Entity("user");
      user1.setProperty(UserUtils.sessionIdCookieName, "value1");
      datastore.put(user1);

      Entity user2 = new Entity("user");
      user2.setProperty(UserUtils.sessionIdCookieName, "value2");
      datastore.put(user2);

      Entity user3 = new Entity("user");
      user3.setProperty(UserUtils.sessionIdCookieName, "value3");
      datastore.put(user3);
      
      Cookie cookie1 = new Cookie(UserUtils.sessionIdCookieName, "value1");
      Cookie cookie2 = new Cookie("name2", "value2");
      Cookie cookie3 = new Cookie("name3", "value3");
      Cookie cookies[] = new Cookie[]{cookie1, cookie2, cookie3};

      Entity actual = UserUtils.getUserFromCookie(cookies, datastore);

      Assert.assertEquals(user1, actual); 
  }

  //Test given a cookie with a name that doesn't match an entity
  @Test
  public void wrongCookieName(){
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      Entity user1 = new Entity("user");
      user1.setProperty(UserUtils.sessionIdCookieName, "value1");
      datastore.put(user1);

      Entity user2 = new Entity("user");
      user2.setProperty(UserUtils.sessionIdCookieName, "value2");
      datastore.put(user2);

      Entity user3 = new Entity("user");
      user3.setProperty(UserUtils.sessionIdCookieName, "value3");
      datastore.put(user3);

    Cookie cookie1 = new Cookie("value1", "name1");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = UserUtils.getUserFromCookie(cookies, datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual); 
  }

  //Test where a cookie's value doesn't match any entities values
  @Test
  public void wrongCookieValue(){
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      Entity user1 = new Entity("user");
      user1.setProperty(UserUtils.sessionIdCookieName, "value1");
      datastore.put(user1);

      Entity user2 = new Entity("user");
      user2.setProperty(UserUtils.sessionIdCookieName, "value2");
      datastore.put(user2);

      Entity user3 = new Entity("user");
      user3.setProperty(UserUtils.sessionIdCookieName, "value3");
      datastore.put(user3);

    Cookie cookie1 = new Cookie(UserUtils.sessionIdCookieName, "wrongvalue");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = UserUtils.getUserFromCookie(cookies, datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual); 
  }

  //Test given a single cookie and a single entity
  @Test
  public void oneUserOneCookie(){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity user1 = new Entity("user");
    user1.setProperty(UserUtils.sessionIdCookieName, "value1");
    datastore.put(user1);

    Cookie cookie1 = new Cookie(UserUtils.sessionIdCookieName, "value1");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = UserUtils.getUserFromCookie(cookies,datastore);
    Entity expected = user1;
    
    Assert.assertEquals(expected, actual);
  }

  //Test where the datastore is not passed into the function
  @Test
  public void noDatastore(){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity user1 = new Entity("user");
    user1.setProperty(sessionIdCookieName, "value1");
    datastore.put(user1);

    Cookie cookie1 = new Cookie(UserUtils.sessionIdCookieName, "value1");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = UserUtils.getUserFromCookie(cookies, null);
    Entity expected = null;
    
    Assert.assertEquals(expected, actual);
  }

  //Test where there are no entities in the datastore
  @Test
  public void noEntities(){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Cookie cookie1 = new Cookie(UserUtils.sessionIdCookieName, "value1");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = UserUtils.getUserFromCookie(cookies, datastore);
    Entity expected = null;
    
    Assert.assertEquals(expected, actual);
  }

 //Test where no cookies are passed into the function
  @Test
  public void noCookies(){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity user1 = new Entity("user");
    user1.setProperty(UserUtils.sessionIdCookieName, "value1");
    datastore.put(user1);

    Cookie cookies[] = new Cookie[0];

    Entity actual = UserUtils.getUserFromCookie(cookies, null);
    Entity expected = null;
    
    Assert.assertEquals(expected, actual);
  }
}
