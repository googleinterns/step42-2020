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
//import com.google.sps.utils.UserUtils;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

//This servlet gets the quiz question from the Game entity 
//The quiz question is updated if the quiz timestamp is outdated
@WebServlet("/game-quiz-status-servlet")
public class GameQuizStatusServlet extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Cookie cookies[] = request.getCookies();
        // if(cookies == null){
        //     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //     return;
        // }
        // Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);
        // if(userEntity == null){
        //     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //     return;
        // }

        Query query = new Query("user");
        PreparedQuery pq = datastore.prepare(query);
        Entity userEntity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);

        Query queryTwo = new Query("Game");
        PreparedQuery pqTwo = datastore.prepare(queryTwo);

        //QuizTimingPropertiesUtils timing_properties = new QuizTimingPropertiesUtils();
        //UserUtils user_utils_class = new UserUtils();
        Long current_quiz_stamp = QuizTimingPropertiesUtils.getQuizTimestampProperty("Game", "gameID", userEntity.getProperty("currentGame").toString(), datastore);

        //Entity current_game = getEntityFromDatastore("Game", "gameID", userEntity.getProperty("currentGame").toString(), datastore);        

        Gson gson = new Gson();
        response.setContentType("application/json;");

        Entity current_game = null;
        for(Entity game_en : pqTwo.asIterable()) {
            if(game_en.getProperty("gameID").equals(userEntity.getProperty("currentGame"))) {
                current_game = game_en;
            }
        }

        if(QuizTimingPropertiesUtils.isTimestampOutdated(current_quiz_stamp)) {
            //response.getWriter().println(gson.toJson(timing_properties.getNewQuestion(game_entity, datastore)));
            response.getWriter().println(gson.toJson(QuizTimingPropertiesUtils.getNewQuestion(current_game, datastore)));

        }
        
        //Entity current_game = user_utils_class.getEntityFromDatastore("Game", "currentGame", userEntity.getProperty("currentGame").toString(), datastore);
        response.getWriter().println(gson.toJson(current_game.getProperty("quizQuestion")));
    }
}