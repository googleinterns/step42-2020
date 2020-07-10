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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.sps.UserUtils;

/** tests the IsValidGameName function */
@RunWith(JUnit4.class)
public final class QueryByUserIdTest {
  
  private DatastoreService datastore;
  private UserUtils query;

  // helper variable allows the use of entities in testing 
  private final LocalServiceTestHelper helper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setUp() {
    helper.setUp();
    query = new UserUtils();
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  // given a user id and a datastore instance, the function should return the entity for the given user
  @Test
  public void findEntityByUserId() {

    Entity userEntity = new Entity("Game");
    userEntity.setProperty("userID", "123");
    datastore.put(userEntity);

    Entity userEntity2 = new Entity("Game");
    userEntity2.setProperty("userID", "456");
    datastore.put(userEntity2);

    Entity userEntity3 = new Entity("Game");
    userEntity3.setProperty("userID", "789");
    datastore.put(userEntity3);

    Entity actual = query.QueryByUserId("123", datastore);
    Entity expected = userEntity;

    Assert.assertEquals(expected, actual);
  }
}