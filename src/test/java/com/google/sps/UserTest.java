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

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.sps.utils.User;

/** tests the user class functions */
@RunWith(JUnit4.class)
public final class UserTest {

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
 
    Entity userEntity = new Entity("user");
    User user = new User(userEntity);
    user.setGame("game");
    user.setId("id");
    user.setName("name");
    user.setScore(20);
    user.setQuizTiming(0);
    user.setBlobKey("blob");

    Assert.assertEquals("game", user.getGame());
    Assert.assertEquals("id", user.getId());
    Assert.assertEquals("name", user.getName());
    Assert.assertEquals(20, user.getScore());
    Assert.assertEquals(0, user.getQuizTiming());
    Assert.assertEquals("blob", user.getBlobKey());
  }

}
