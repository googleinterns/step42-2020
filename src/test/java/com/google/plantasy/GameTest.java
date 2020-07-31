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
import com.google.plantasy.utils.Game;
import com.google.plantasy.utils.UserUtils;
import java.util.ArrayList;

// tests the usergame class functions 
@RunWith(JUnit4.class)
public final class GameTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
    helper.setUp();
    }

    @After
    public void tearDown() {
    helper.tearDown();
    }

    @Test 
    //Tests if the properties are set right for the game class 
    public void SetsGameEntityCorrectly() {
        Entity game_entity = new Entity("Game");
        Game game = new Game(new Entity("Game"));
        game.setGameId("1234");
        game.setGameName("Plants");
        game.setQuizQuestion("Which plant looks the healthiest?");
        game.setQuizTimestamp(1594309443653L);
        ArrayList<String> users = new ArrayList<String>();
        users.add("a");
        users.add("b");
        game.setUserIds(users);

        Assert.assertEquals("1234", game.getGameId());
        Assert.assertEquals("Plants", game.getGameName());
        Assert.assertEquals("Which plant looks the healthiest?", game.getQuizQuestion());
        Assert.assertEquals(1594309443653L, game.getQuizTimestamp());
        Assert.assertEquals(users, game.getUserIds());
    }

    @Test
    //Tests if the entity created by the game class gets put into datastore
    public void putInDatastore() {
        Game game = new Game(new Entity("Game"));
        game.setGameId("1234");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(game.getGameEntity());

        Entity datastore_game_entity = UserUtils.getEntityFromDatastore("Game", "gameId", game.getGameId(), datastore);
        Assert.assertEquals(game.getGameId(), datastore_game_entity.getProperty("gameId"));
    }
}
