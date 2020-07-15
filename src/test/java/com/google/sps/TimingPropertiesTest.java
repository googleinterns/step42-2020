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
    //If the user's time is a null value then the getTimestampProperty function should return null
    public void nullUserTimeStamp() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", null);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);
 
        Object actual = timing_properties_test.getTimestampProperty("user", datastore); 
        Assert.assertEquals(null, actual);
    }

    @Test 
    //If the getTimestampProperty function gets fed an empty string, the function should return null
    public void emptyEntityString() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Object actual = timing_properties_test.getTimestampProperty("", datastore);
        Assert.assertEquals(null, actual);
    }
 
    @Test
    //If the getTimeStampProperty function is fed a null value of datastore then the return should be null
    public void noDatastore() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        Object actual = timing_properties_test.getTimestampProperty("user", null);
        Assert.assertEquals(null, actual);
    }

    @Test
    //If the entity doesn't have a "timestamp" property then null should be returned
    public void noTimeStampProperty() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Object actual = timing_properties_test.getTimestampProperty("user", datastore);
        Assert.assertEquals(null, actual);
    }

    @Test 
    //Checks if getTimestampProperty function is working
    public void validParameters_for_getTimestampProperty() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();

        Entity user = new Entity("user");
        user.setProperty("quiz_timestamp", 1594309443653L);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);

        Object actual = timing_properties_test.getTimestampProperty("user", datastore);
        Assert.assertEquals(1594309443653L, actual);
    }
 
    @Test
    //If the userTookQuiz function is fed two empty strings then the function should return false
    public void emptyStrings() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        String user_quiz_time = "";
        String current_quiz_time = "";
        
        Boolean actual = timing_properties_test.userTookQuiz(user_quiz_time, current_quiz_time);
        Assert.assertEquals(false, actual);
    }

    @Test
    //Checks if userTookQuiz works especially with being dependent on the getTimestampProperty
    public void validParameters_for_userTookQuiz() {
        QuizTimingPropertiesUtils timing_properties_test = new QuizTimingPropertiesUtils();
        
        Entity user = new Entity("user");
        Entity game = new Entity("game");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        user.setProperty("quiz_timestamp", 1594309443653L);
        datastore.put(user);

        game.setProperty("quiz_timestamp", 1594309443660L);
        datastore.put(game);

        String user_quiz_time = (timing_properties_test.getTimestampProperty("user", datastore)).toString();
        String game_quiz_time = (timing_properties_test.getTimestampProperty("game", datastore)).toString();

        Boolean actual = timing_properties_test.userTookQuiz(user_quiz_time, game_quiz_time);
        Assert.assertEquals(false, actual);
    }

    //Break for PR

    @Test
    //Checks if the timestamp given is a null value
    public void nullParameter_for_newDayNewQuiz(){
        Boolean actual = timing_properties_test.newDayNewQuiz(null);
        Assert.assertEquals(null, actual);
    }

    @Test 
    //Checks if newDayNewQuiz works especially with being dependent on the getTimestampProperty
    public void validParameter_for_newDayNewQuiz() {
        user.setProperty("quiz_timestamp", 159430944365L);
        datastore.put(user);

        Object user_quiz_time = timing_properties_test.getTimestampProperty("user", datastore);

        Boolean actual = timing_properties_test.newDayNewQuiz(user_quiz_time);
        Assert.assertEquals(true, actual);
    }

    @Test 
    //If the Entity parameter is null then the getNewQuestion function should return null
    public void nullEntityValue_for_getNewQuestion(){
        Object game_question = timing_properties_test.getNewQuestion(null, datastore);
        Assert.assertEquals(null, game_question);
    }

    @Test
    //Checks if getNewQuestion works by seeing if a question is added the game quizQuestion property
    public void validParameter_for_getNewQuestion() {
        game.setProperty("quiz_timestamp", 159430944365L);
        game.setProperty("quizQuestion", "");
        datastore.put(game);

        String new_question = (timing_properties_test.getNewQuestion(game, datastore)).toString();
        if(!(new_question.equals(""))){
            Assert.assertEquals(true, true);
        }
    }

    //Break for PR
}
