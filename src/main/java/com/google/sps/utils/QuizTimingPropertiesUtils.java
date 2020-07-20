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
import com.google.appengine.api.datastore.Key;
import java.util.List;
import java.util.ArrayList;
import com.google.appengine.api.datastore.FetchOptions;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public final class QuizTimingPropertiesUtils {

    private static final Logger log = Logger.getLogger(QuizTimingPropertiesUtils.class.getName());

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
    public Long getTimestampProperty(String entity, DatastoreService datastore) {
        Query query = new Query(entity);
        PreparedQuery pq;
        try {
            pq = datastore.prepare(query);
        } catch(NullPointerException e) {
            log.log(Level.SEVERE, "Null result for parameter {0}", datastore);
            return null;
        }
        if(pq.asList(FetchOptions.Builder.withLimit(1)).size() > 0) {
            Entity fetched_item = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
            return (Long) fetched_item.getProperty("quiz_timestamp");
        } 
        log.log(Level.SEVERE, "No results for query {0}", entity);
        return null;
    }
 
    //This function checks if the user has taken the quiz yet by comparing their timestamp with the quiz's timestamp
    public Boolean userTookQuiz(Long usersQuizTime, Long currentQuizTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String users_quiz_time;
        String current_quiz_time;
        try {
            users_quiz_time = sdf.format(usersQuizTime);
            current_quiz_time = sdf.format(currentQuizTime);
        } catch(IllegalArgumentException e ) {
            log.log(Level.SEVERE, "Null result for parameter");
            return null;
        }  
        return (users_quiz_time.compareTo(current_quiz_time) > 0);
    }

    //This function checks to see if the quiz is outdated
    public Boolean isQuizOutdated(Long current_quiz_time) {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String quiz_date;
        try {
            quiz_date = sdf.format(current_quiz_time);
        } catch(IllegalArgumentException e) {
            log.log(Level.SEVERE, "Given a null Parameter for {0}", current_quiz_time);
            return null;
        }
        String today_date = sdf.format(new Date());
        return today_date.compareTo(quiz_date) > 0;
    }
    
    //This function gets a new quiz question if the quiz is outdated
    public String getNewQuestion(Entity game_entity, DatastoreService datastore) {
        Random rand = new Random();
        int rand_number = rand.nextInt(quiz_questions.size());
        Key game_entity_key;
 
        try {
            game_entity_key = game_entity.getKey();
        } catch(NullPointerException e) {
            log.log(Level.SEVERE, "Null value given for parameter {0}", game_entity);
            return null;
        } 
 
        datastore.delete(game_entity_key);
            
        Entity update_game_entity = new Entity(game_entity_key);
        update_game_entity.setProperty("quiz_timestamp", System.currentTimeMillis());
        update_game_entity.setProperty("quizQuestion", quiz_questions.get(rand_number));
        datastore.put(update_game_entity);
        return (update_game_entity.getProperty("quizQuestion")).toString();
    }

    public Boolean giveUserPoints(Boolean userQuizStatus, Entity currentUser, DatastoreService datastore) {
        if(currentUser == null || datastore == null) {
            log.log(Level.SEVERE, "Given a null value in parameters");
            return null;
        }
        if(userQuizStatus) {
            Key user_key = currentUser.getKey();
            datastore.delete(user_key);
 
            Entity updated_user = new Entity("user");
            updated_user.setProperty("quiz_timestamp", System.currentTimeMillis());
            updated_user.setProperty("userID", currentUser.getProperty("userID"));
            updated_user.setProperty("score", ((int) currentUser.getProperty("score")) + 20);
            datastore.put(updated_user);
            return true;
        }
        return false;
    }


}