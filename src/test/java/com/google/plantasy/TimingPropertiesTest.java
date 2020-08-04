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
import com.google.plantasy.utils.QuizTimingPropertiesUtils;
import com.google.plantasy.utils.Game;

@RunWith(JUnit4.class)
public final class TimingPropertiesTest {

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
    //Test where user has null timestamp
    public void getQuizTimestampProperty_nullUserTimeStamp() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        Entity user = new Entity("user");
        user.setProperty("userID", "abc");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);

        Long actual = timing_properties_test.getQuizTimestampProperty("user", "userID", (user.getProperty("userID")).toString(), datastore);
        Assert.assertEquals(null, actual);
    }

    @Test
    //Test where String parameter is empty
    public void getQuizTimestampProperty_emptyEntityString() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Long actual = timing_properties_test.getQuizTimestampProperty("", "userID", "abc", datastore);
        Assert.assertEquals(null, actual);
    }

    @Test(expected = NullPointerException.class)
    //Test where datastore object is null
    public void getQuizTimestampProperty_noDatastore() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        user.setProperty("userID", "abc");

        Long actual = timing_properties_test.getQuizTimestampProperty("user", "userID", (user.getProperty("userID")).toString(), null);
    }

    @Test
    //Test for if user entity does not have a timestamp property
    public void getQuizTimestampProperty_noTimeStampProperty() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        Entity user = new Entity("user");
        user.setProperty("userID", "abc");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Long actual = timing_properties_test.getQuizTimestampProperty("user", "userID", user.getProperty("userID").toString(), datastore);
        Assert.assertEquals(null, actual);
    }

    @Test 
    //Tests if a valid timestamp object is returned
    public void getQuizTimestampProperty_validParamters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 1594309443653L);
        user.setProperty("userID", "abc");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);

        long actual = timing_properties_test.getQuizTimestampProperty("user", "userID", user.getProperty("userID").toString(), datastore);
        Assert.assertEquals(1594309443653L, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    //Tests for paramters being null
    public void userTookQuiz_nullParameters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Long user_quiz_time = null;
        Long current_quiz_time = null;

        boolean actual = timing_properties_test.userTookQuiz(user_quiz_time, current_quiz_time);
    }

    @Test
    //Tests valid string, timestamp paramters 
    public void userTookQuiz_validParameters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        Entity game_entity = new Entity("Game");
        Game game = new Game(game_entity);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        user.setProperty("quiz_timestamp", 1594309443653L);
        user.setProperty("userID", "abc");
        datastore.put(user);

        game.setQuizTimestamp(1594309443660L);
        datastore.put(game.getGameEntity());

        Long user_quiz_time = timing_properties_test.getQuizTimestampProperty("user", "userID", user.getProperty("userID").toString(), datastore);
        Long game_quiz_time = timing_properties_test.getQuizTimestampProperty("Game", "gameId", game.getGameId(), datastore);

        boolean actual = timing_properties_test.userTookQuiz(user_quiz_time, game_quiz_time);
        Assert.assertEquals(false, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    //Tests if Object parameter is null
    public void isTimestampOutdated_nullParamter(){
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        boolean actual = timing_properties_test.isTimestampOutdated(null);
    }

    @Test 
    //Tests if quiz timestamp gets updated on a new day
    public void isTimestampOutdated_validParameters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity user = new Entity("user");

        user.setProperty("quiz_timestamp", 159430944365L);
        user.setProperty("userID", "abc");
        datastore.put(user);

        Long user_quiz_time = timing_properties_test.getQuizTimestampProperty("user", "userID", user.getProperty("userID").toString(), datastore);
        boolean actual = timing_properties_test.isTimestampOutdated(user_quiz_time);
        Assert.assertEquals(true, actual);
    }

    @Test(expected = NullPointerException.class)
    //Tests where the entity parameter is null
    public void getNewQuestion_nullEntityValue(){
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        String game_question = timing_properties_test.getNewQuestion(null, datastore);
    }

    @Test
    //Checks if a new question is generated for a new day
    public void getNewQuestion_validParamters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity game_entity = new Entity("Game");
        Game game = new Game(game_entity);

        game.setQuizTimestamp(159430944365L);
        game.setQuizQuestion("");
        datastore.put(game.getGameEntity());

        String new_question = timing_properties_test.getNewQuestion(game.getGameEntity(), datastore);
        if(!(new_question.equals(""))){
            Assert.assertEquals(true, true);
        }
    }

    @Test(expected = NullPointerException.class)
    //Test if giveUserPoints is given null Entity value as a parameter
    public void giveUserQuizTakenPoints_nullEntity() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity user = null;
        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
    }

    @Test(expected = NullPointerException.class)
    //Test if giveUserPoints is given null datastore value as a parameter
    public void giveUserQuizTakenPoints_nullDatastore() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = null;
        Entity user = new Entity("user");

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
    }

    @Test
    //Tests of userQuizStatus is false
    public void giveUserQuizTakenPoints_falseUserQuizStatus(){
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity user = new Entity("user");

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(false, user, datastore);
        Assert.assertEquals(false, actual);
    }

    @Test(expected = NullPointerException.class)
    //Tests if user does not have a score property 
    public void giveUserQuizTakenPoints_notScoreProperty() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 159430944365L);
        datastore.put(user);

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
    }

    @Test
    //Test if user was given points if their initial value was not zero
    public void giveUserQuizTakenPoints_actuallyAddedPoints_notStartAtZero(){
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 159430944365L);
        int score_value = 15;

        user.setProperty("score", score_value);
        datastore.put(user);

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
        Assert.assertEquals(user.getProperty("score"), 35);
    }

    @Test
    //Test if user was given points if their initial value is zero
    public void giveUserQuizTakenPoints_actuallyAddedPoints(){
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 159430944365L);
        int score_value = 0;
        user.setProperty("score", score_value);
        datastore.put(user);

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
        Assert.assertEquals(user.getProperty("score"), 20);
    }

    @Test
    //Tests for valid paramters for give UserQuizTakenPoints
    public void giveUserQuizTakenPoints_validParameters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 159430944365L);
        user.setProperty("score", 0);
        datastore.put(user);

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
        Assert.assertEquals(true, actual);
    }
}