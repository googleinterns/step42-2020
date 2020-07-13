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
import com.google.appengine.api.datastore.KeyFactory;
import java.util.ArrayList;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.util.List;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


public final class GameUtils {

  /**
  * Checks to see whether the user already has a game by the given name, and returns false if so
  */
  public boolean IsValidGameName(String gameName, Entity userEntity) {
    
    if(userEntity == null || gameName == ""){
        return false;
    }
    
    // list of game names for the given user
    ArrayList<String> names = (ArrayList<String>) userEntity.getProperty("gameNames");

    //compares game names to the prospective game name, and returns false if there's a match
    for(String name : names){
        if(name == gameName){
            return false;
        }
    }
    return true;
  }
}