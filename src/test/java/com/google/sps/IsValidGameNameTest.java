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
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.sps.GameUtils;

/** tests the IsValidGameName function */
@RunWith(JUnit4.class)
public final class IsValidGameNameTest {
  
  private GameUtils gameUtils;

  private static String userID1 = "1";
  private static String userID2 = "2";

  // helper variable allows the use of entities in testing 
  private final LocalServiceTestHelper helper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setUp() {
    helper.setUp();
    gameUtils = new GameUtils();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  // Given a user has no games, the function should return true (any game name would be valid)
  @Test
  public void noGames() {

    Entity userEntity2 = new Entity("user");
    ArrayList<String> gameNames2 = new ArrayList<>();

    userEntity2.setProperty("gameNames",gameNames2);
    userEntity2.setProperty("userID",userID2);

    boolean actual = gameUtils.IsValidGameName("game1", userEntity2);

    Assert.assertEquals(true, actual);
  }

  // given a game name the user already has, the function should return false 
  @Test
  public void duplicateGameName() {

    Entity userEntity1 = new Entity("user");
    ArrayList<String> gameNames1 = new ArrayList<>();

    gameNames1.add("game1");
    gameNames1.add("game2");
    gameNames1.add("Game3");
    userEntity1.setProperty("gameNames",gameNames1);
    userEntity1.setProperty("userID",userID1);

    boolean actual = gameUtils.IsValidGameName("game1", userEntity1);

    Assert.assertEquals(false, actual);
  }

  // when the user has games, but the given game name is new, the function should return true
  @Test
  public void newGameName() {

    Entity userEntity1 = new Entity("user");
    ArrayList<String> gameNames1 = new ArrayList<>();
    
    gameNames1.add("game1");
    gameNames1.add("game2");
    gameNames1.add("Game3");
    userEntity1.setProperty("gameNames",gameNames1);
    userEntity1.setProperty("userID",userID1);

    boolean actual = gameUtils.IsValidGameName("game4", userEntity1);

    Assert.assertEquals(true, actual);
  }

  // when the user has games, and the given name is a duplicate name with different capitalization, the function should return true
  @Test
  public void sameNameDifferentCapitalization() {

    Entity userEntity1 = new Entity("user");
    ArrayList<String> gameNames1 = new ArrayList<>();
    
    gameNames1.add("game1");
    gameNames1.add("game2");
    gameNames1.add("Game3");
    userEntity1.setProperty("gameNames",gameNames1);
    userEntity1.setProperty("userID",userID1);

    boolean actual = gameUtils.IsValidGameName("Game1", userEntity1);

    Assert.assertEquals(true, actual);
  }

  // given a blank game name, function should return false 
  @Test
  public void emptyGameName() {

    Entity userEntity1 = new Entity("user");
    ArrayList<String> gameNames1 = new ArrayList<>();
    
    gameNames1.add("game1");
    gameNames1.add("game2");
    gameNames1.add("Game3");
    userEntity1.setProperty("gameNames",gameNames1);
    userEntity1.setProperty("userID",userID1);

    boolean actual = gameUtils.IsValidGameName("", userEntity1);

    Assert.assertEquals(false, actual);
  }

  // given a null user entity, function should return false 
  @Test
  public void nullUserEntity() {

    boolean actual = gameUtils.IsValidGameName("game1", null);

    Assert.assertEquals(false, actual);
  }
}