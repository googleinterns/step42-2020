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
import com.google.sps.QuizTimingPropertiesUtils;

@WebServlet("/answer-quiz-question")
public class AnswerQuizQuestion extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QuizTimingPropertiesUtils timing_properties = new QuizTimingPropertiesUtils();
        Long userTime = timing_properties.getTimestampProperty("user", datastore);
        String quizTime = (timing_properties.getTimestampProperty("Game", datastore)).toString();

        Query query = new Query("user");
        PreparedQuery pq = datastore.prepare(query);
        Entity user = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);

        Entity score = new Entity("Score");
        score.setProperty("userID", 12345);
        score.setProperty("gameID", 67890);
        score.setProperty("score", 0);
        datastore.put(score);
    
        Boolean value = timing_properties.giveUserPoints(timing_properties.userTookQuiz(userTime.toString(), quizTime), user, score, datastore);

        Query query = new Query("Score");
        PreparedQuery pq = datastore.prepare(query);
        Entity score_entity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);

        Gson gson = new Gson();
        response.setContentType("application/json;");
        //First calls Hannah's helper function to get the specific user, will return user entity
        //Then get userEntity.getParameter("userID") and convert this to a string;
        response.getWriter().println(gson.toJson(score_entity));

        //Get game by ID from gameScores

        //Ask for the best way to get the game from a user and the best way to get the score entity from the user
        
    }
}
