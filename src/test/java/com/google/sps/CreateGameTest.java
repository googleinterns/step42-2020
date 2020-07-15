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
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.sps.utils.GameUtils;

/** tests the createGameEntity function */
@RunWith(JUnit4.class)
public final class CreateGameTest {

  private DatastoreService datastore;

  // helper variable allows the use of datastore in testing 
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

  // Given an empty string for game name, the function should return a null entity
  @Test
  public void emptyGameNameFails() {

    Entity actual = GameUtils.createGameEntity("", datastore);

    Assert.assertEquals(null, actual);
  }

  // Given a null for datastore instance, the function should return a null entity
  @Test
  public void nullDatastoreFails() {

    Entity actual = GameUtils.createGameEntity("game", null);

    Assert.assertEquals(null, actual);
  }

  // Given valid datastore and gamename, the function should create a game entity, add it to datastore and return the entity
  @Test
  public void createValidGame() {

    Entity actual = GameUtils.createGameEntity("game", datastore);

    long timestamp = 0;

    Assert.assertEquals("game", actual.getProperty("gameName"));
    Assert.assertEquals("", actual.getProperty("quizQuestion"));
    Assert.assertEquals(timestamp, actual.getProperty("quiz_timestamp"));
  }
}