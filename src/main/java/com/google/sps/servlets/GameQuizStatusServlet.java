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
        Cookie cookies[] = request.getCookies();
        if(cookies == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);
        if(userEntity == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        QuizTimingPropertiesUtils timing_properties = new QuizTimingPropertiesUtils();
        //Add cookies for current user then get the current game that way
        //replace the above code for lines 29 through 32 
   
        // Query query = new Query("Game");
        // PreparedQuery pq = datastore.prepare(query);
 
        // Entity game_entity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
        Long current_quiz_stamp = timing_properties.getQuizTimestampProperty("Game", "currentGame", userEntity.getProperty("currentGame"), datastore);

        Gson gson = new Gson();
        response.setContentType("application/json;");

        if(timing_properties.isTimestampOutdated(current_quiz_stamp)) {
            response.getWriter().println(gson.toJson(timing_properties.getNewQuestion(game_entity, datastore)));
        }

        response.getWriter().println(gson.toJson(game_entity.getProperty("quizQuestion")));
    }
}