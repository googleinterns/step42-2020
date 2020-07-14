package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.FetchOptions;
import java.text.DateFormat;
import java.util.Date;
import com.google.appengine.api.datastore.Key;
import java.util.Random;
import java.util.*;

@WebServlet("/game-quiz-status-servlet")
public class GameQuizStatusServlet extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

        Random rand = new Random();
        int rand_number = rand.nextInt(quiz_questions.size());
        
        Query query = new Query("Game");
        PreparedQuery pq = datastore.prepare(query);

        Entity game_entity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
        Object current_quiz_stamp = game_entity.getProperty("quiz_timestamp");     

        // String quiz_date = DateFormat.getDateInstance().format(current_quiz_stamp);
        // String today_date = DateFormat.getDateInstance().format(new Date());

        Gson gson = new Gson();
        response.setContentType("application/json;");

        if(newDayNewQuiz(current_quiz_stamp))

        // if(today_date.compareTo(quiz_date) > 0) {
        //     Key game_key = game_entity.getKey();
        //     datastore.delete(game_key);
        //     Entity update_game_entity = new Entity(game_key);
        //     update_game_entity.setProperty("quiz_timestamp", System.currentTimeMillis());
        //     update_game_entity.setProperty("quizQuestion", quiz_questions.get(rand_number));
        //     datastore.put(update_game_entity);
        // }

        response.getWriter().println(gson.toJson(game_entity.getProperty("quizQuestion")));
    }
}