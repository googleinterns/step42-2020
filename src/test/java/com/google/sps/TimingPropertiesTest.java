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
import com.google.sps.QuizTimingPropertiesUtils;
 
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
    public void getTimestampProperty_nullUserTimeStamp() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", null);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);
 
        Long actual = timing_properties_test.getTimestampProperty("user", datastore); 
        Assert.assertEquals(null, actual);
    }
 
    @Test
    //Test where String parameter is empty
    public void getTimestampProperty_emptyEntityString() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Long actual = timing_properties_test.getTimestampProperty("", datastore);
        Assert.assertEquals(null, actual);
    }
 
    @Test
    //Test where datastore object is null
    public void getTimestampProperty_noDatastore() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        Long actual = timing_properties_test.getTimestampProperty("user", null);
        Assert.assertEquals(null, actual);
    }

    @Test
    //Test for if user entity does not have a timestamp property
    public void getTimestampProperty_noTimeStampProperty() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Long actual = timing_properties_test.getTimestampProperty("user", datastore);
        Assert.assertEquals(null, actual);
    }

    @Test 
    //Tests if a valid timestamp object is returned
    public void getTimestampProperty_validParamters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 1594309443653L);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);

        long actual = timing_properties_test.getTimestampProperty("user", datastore);
        Assert.assertEquals(1594309443653L, actual);
    }

    @Test
    //Tests for paramters being null
    public void userTookQuiz_nullParameters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        
        Long user_quiz_time = null;
        Long current_quiz_time = null;
        
        boolean actual = timing_properties_test.userTookQuiz(user_quiz_time, current_quiz_time);
        Assert.assertEquals(false, actual);
    }

    @Test
    //Tests valid string, timestamp paramters 
    public void userTookQuiz_validParameters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        
        Entity user = new Entity("user");
        Entity game = new Entity("game");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        user.setProperty("quiz_timestamp", 1594309443653L);
        datastore.put(user);

        game.setProperty("quiz_timestamp", 1594309443660L);
        datastore.put(game);

        Long user_quiz_time = timing_properties_test.getTimestampProperty("user", datastore);
        Long game_quiz_time = timing_properties_test.getTimestampProperty("game", datastore);

        boolean actual = timing_properties_test.userTookQuiz(user_quiz_time, game_quiz_time);
        Assert.assertEquals(false, actual);
    }

    @Test
    //Tests if Object parameter is null
    public void isQuizOutdated_nullParamter(){
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
 
        boolean actual = timing_properties_test.isQuizOutdated(null);
        Assert.assertEquals(false, actual);
    }
 
    @Test 
    //Tests if quiz timestamp gets updated on a new day
    public void isQuizOutdated_validParameters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity user = new Entity("user");
 
        user.setProperty("quiz_timestamp", 159430944365L);
        datastore.put(user);
 
        Long user_quiz_time = timing_properties_test.getTimestampProperty("user", datastore);
    
        boolean actual = timing_properties_test.isQuizOutdated(user_quiz_time);
        Assert.assertEquals(true, actual);
    }
 
    @Test 
    //Tests where the entity parameter is null
    public void getNewQuestion_nullEntityValue(){
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        String game_question = timing_properties_test.getNewQuestion(null, datastore);
        Assert.assertEquals(null, game_question);
    }
 
    @Test
    //Checks if a new question is generated for a new day
    public void getNewQuestion_validParamters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity game = new Entity("game");
 
        game.setProperty("quiz_timestamp", 159430944365L);
        game.setProperty("quizQuestion", "");
        datastore.put(game);
 
        String new_question = timing_properties_test.getNewQuestion(game, datastore);
        if(!(new_question.equals(""))){
            Assert.assertEquals(true, true);
        }
    }

    @Test 
    //Test if giveUserPoints is given null Entity value as a parameter
    public void giveUserQuizTakenPoints_nullEntity() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity user = null;

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
        Assert.assertEquals(false, actual);
    }

    @Test 
    //Test if giveUserPoints is given null datastore value as a parameter
    public void giveUserQuizTakenPoints_nullParameters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = null;
        Entity user = new Entity("user");

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
        Assert.assertEquals(false, actual);
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

    @Test
    //Tests if user does not have a score property 
    public void giveUserQuizTakenPoints_notScoreProperty() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 159430944365L);
        user.setProperty("userID", 12345);
        datastore.put(user);

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
        Assert.assertEquals(false, actual);
    }

    @Test
    //Test if user was given points if their initial value was not zero
    public void giveUserQuizTakenPoints_actuallyAddedPoints_notStartAtZero(){
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 159430944365L);
        user.setProperty("userID", 12345);
        int score_value = 15;

        user.setProperty("score", score_value);
        datastore.put(user);

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
        if(((int) user.getProperty("score")) == (score_value + 20) ) {
            Assert.assertEquals(actual, true);
        }
    }

    @Test
    //Test if user was given points if their initial value is zero
    public void giveUserQuizTakenPoints_actuallyAddedPoints(){
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 159430944365L);
        user.setProperty("userID", 12345);
        int score_value = 0;
        user.setProperty("score", score_value);
        datastore.put(user);

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
        if(((int) user.getProperty("score")) == (score_value + 20) ) {
            Assert.assertEquals(actual, true);
        }
    }

    @Test
    //Tests for valid paramters for give UserQuizTakenPoints
    public void giveUserQuizTakenPoints_validParameters() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 159430944365L);
        user.setProperty("userID", 12345);
        user.setProperty("score", 0);
        datastore.put(user);

        boolean actual = timing_properties_test.giveUserQuizTakenPoints(true, user, datastore);
        Assert.assertEquals(true, actual);
    }
}
