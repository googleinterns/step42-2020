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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.util.List;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public final class UserUtils {

  /**
  * Queries a user entity of a given specific user Id from datastore
  */
  public Entity QueryByUserId(String givenUser, DatastoreService datastore) {

    if(givenUser == "" || datastore == null){
        return null;
    }

    Filter getCorrectUser = new FilterPredicate("userID", FilterOperator.EQUAL, givenUser);
    Query query = new Query("Game").setFilter(getCorrectUser);
    PreparedQuery results = datastore.prepare(query);
    
    List<Entity> resultsList = results.asList(FetchOptions.Builder.withLimit(1));
    if(resultsList.size() == 0){
        return null;
    }

    Entity entity = resultsList.get(0);
    return entity;
  }
}