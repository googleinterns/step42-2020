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
  
    private DatastoreService datastore;
    private QuizTimingPropertiesUtils timing_properties_test;
    public Entity user;
    public Entity game;
 
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
 
    @Before
    public void setUp() {
        helper.setUp();
        timing_properties_test = new QuizTimingPropertiesUtils();
        datastore = DatastoreServiceFactory.getDatastoreService();
        user = new Entity("user");
        game = new Entity("game");
    }
 
    @After
    public void tearDown() {
        helper.tearDown();
    }
 
    @Test
    //If the user's time is a null value then the getTimestampProperty function should return null
    public void nullUserTimeStamp() {
        user.setProperty("quiz_timestamp", null);
        datastore.put(user);
 
        Object actual = timing_properties_test.getTimestampProperty("user", datastore); 
        Assert.assertEquals(null, actual);
    }

    @Test 
    //If the getTimestampProperty function gets fed an empty string, the function should return null
    public void emptyEntityString() {
        Object actual = timing_properties_test.getTimestampProperty("", datastore);
        Assert.assertEquals(null, actual);
    }
 
    @Test
    //If the getTimeStampProperty function is fed a null value of datastore then the return should be null
    public void noDatastore() {
        Object actual = timing_properties_test.getTimestampProperty("user", null);
        Assert.assertEquals(null, actual);
    }

    @Test 
    //Checks if getTimestampProperty function is working
    public void validParameters_for_getTimestampProperty() {
        user.setProperty("quiz_timestamp", 1594309443653L);
        datastore.put(user);

        Object actual = timing_properties_test.getTimestampProperty("user", datastore);
        Assert.assertEquals(1594309443653L, actual);
    }
 
    @Test
    //If the userTookQuiz function is fed two empty strings then the function should return false
    public void emptyStrings() {
        String user_quiz_time = "";
        String current_quiz_time = "";
        
        Boolean actual = timing_properties_test.userTookQuiz(user_quiz_time, current_quiz_time);
        Assert.assertEquals(false, actual);
    }

    @Test
    //Checks if userTookQuiz works especially with being dependent on the getTimestampProperty
    public void validParameters_for_userTookQuiz() {
        user.setProperty("quiz_timestamp", 1594309443653L);
        datastore.put(user);

        game.setProperty("quiz_timestamp", 1594309443660L);
        datastore.put(game);

        String user_quiz_time = (timing_properties_test.getTimestampProperty("user", datastore)).toString();
        String game_quiz_time = (timing_properties_test.getTimestampProperty("game", datastore)).toString();

        Boolean actual = timing_properties_test.userTookQuiz(user_quiz_time, game_quiz_time);
        Assert.assertEquals(false, actual);
    }
}
