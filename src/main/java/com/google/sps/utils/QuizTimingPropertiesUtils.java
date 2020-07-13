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
 
public final class QuizTimingPropertiesUtils {
 
    public Object getTimestampProperty(String entity, DatastoreService datastore) {
        try {
            Query query = new Query(entity);
            PreparedQuery pq = datastore.prepare(query);
 
            try {
                Entity fetched_item = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
                Object timestamp_of_fetcheditem = fetched_item.getProperty("quiz_timestamp");
                return timestamp_of_fetcheditem;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        } catch(NullPointerException e) {
            return null;
        }
    }
 
    public Boolean userTookQuiz(String usersQuizTime, String CurrentQuizTime) {
        if(usersQuizTime.compareTo(CurrentQuizTime) > 0) {
            return true;
        } 
        return false;
    }
}

