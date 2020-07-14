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
import com.google.appengine.api.datastore.Key;
import com.google.sps.QuizTimingPropertiesUtils;

@WebServlet("/game-quiz-status-servlet")
public class GameQuizStatusServlet extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        QuizTimingPropertiesUtils timing_properties = new QuizTimingPropertiesUtils();
  
        //TODO: Move lines 28-31 to be in a helper function 
        Query query = new Query("Game");
        PreparedQuery pq = datastore.prepare(query);
 
        Entity game_entity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
        //May have to check to make sure we query the right game and not the whole entity
        Object current_quiz_stamp = timing_properties.getTimestampProperty("Game", datastore);

        Gson gson = new Gson();
        response.setContentType("application/json;");

        if(timing_properties.newDayNewQuiz(current_quiz_stamp)) {
            response.getWriter().println(gson.toJson(timing_properties.getNewQuestion(game_entity, datastore)));
        }

        response.getWriter().println(gson.toJson(game_entity.getProperty("quizQuestion")));
    }
}