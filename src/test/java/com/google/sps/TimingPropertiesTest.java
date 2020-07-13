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
    public void nullUserTimeStamp() {
        user.setProperty("quiz_timestamp", null);
        datastore.put(user);
 
        Object actual = timing_properties_test.getTimestampProperty("user", datastore); 
        Assert.assertEquals(null, actual);
    }
 
    @Test 
    public void emptyEntityString() {
        Object actual = timing_properties_test.getTimestampProperty("", datastore);
        Assert.assertEquals(null, actual);
    }
 
    @Test
    public void noDatastore() {
        Object actual = timing_properties_test.getTimestampProperty("user", null);
        Assert.assertEquals(null, actual);
    }
 
    @Test
    public void emptyStrings() {
        String user_quiz_time = "";
        String current_quiz_time = "";
        
        Boolean actual = timing_properties_test.userTookQuiz(user_quiz_time, current_quiz_time);
        Assert.assertEquals(false, actual);
    }
 
}
