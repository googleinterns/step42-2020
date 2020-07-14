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
import java.util.ArrayList;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Key;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
 
public final class QuizTimingPropertiesUtils {
 
    List<String> quiz_questions = new ArrayList<String>(List.of(
        "Which plant has the most food growing from it?",
        "Which plant has the prettiest colors?",
        "Which plant is likely to grow the fastest?",
        "Which plant is likely to require the most maintenance to take care of?",
        "Which plant is most likely to be poisonous?",
        "Which plant is most likely to survive really dry weather?",
        "Which plant is the largest?",
        "Which plant looks edible?",
        "Which plant looks the healthiest?",
        "Which plant looks the most serene?",
        "Which plant needs the most sunlight?",
        "Which plant needs the most water?",
        "Which plant represents you?",
        "Which plant represents your best friend?",
        "Which plant will most likely impress your friends and family?",
        "Which plant would look the best inside as a houseplant?",
        "Which plant would look the best outside in a garden?",
        "Which plant would you give as a gift?"
    ));

    //This function gets the the "quiz_timestamp" property of the entity that is fed into the function
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
 
    //This function checks if the user has taken the quiz yet by comparing their timestamp with the quiz's timestamp
    public Boolean userTookQuiz(String usersQuizTime, String CurrentQuizTime) {
        if(usersQuizTime.compareTo(CurrentQuizTime) > 0) {
            return true;
        } 
        return false;
    }

     public Boolean newDayNewQuiz(Object current_quiz_time) {
        String quiz_date = DateFormat.getDateInstance().format(current_quiz_time);
        String today_date = DateFormat.getDateInstance().format(new Date());
 
        if(today_date.compareTo(quiz_date) > 0) {
            return true;
        }
        return false;
    }
 
    public Object getNewQuestion(Entity game_entity, DatastoreService datastore) {
        Random rand = new Random();
        int rand_number = rand.nextInt(quiz_questions.size());
 
        Key game_entity_key = game_entity.getKey();
        datastore.delete(game_entity_key);
        
        Entity update_game_entity = new Entity(game_entity_key);
        update_game_entity.setProperty("quiz_timestamp", System.currentTimeMillis());
        update_game_entity.setProperty("quizQuestion", quiz_questions.get(rand_number));
        datastore.put(update_game_entity);

        return update_game_entity.getProperty("quizQuestion");
    }

}
