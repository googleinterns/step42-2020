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
import com.google.sps.utils.QuizTimingPropertiesUtils;
import com.google.sps.utils.UserUtils;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

//This servelet returns the quiz question of the day to the logged in user as a json string
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

        Long current_quiz_stamp = QuizTimingPropertiesUtils.getQuizTimestampProperty("Game", "gameId", userEntity.getProperty("gameId").toString(), datastore);
        Entity current_game = UserUtils.getEntityFromDatastore("Game", "gameId", userEntity.getProperty("gameId").toString(), datastore);

        Gson gson = new Gson();
        response.setContentType("application/json;");

        if(QuizTimingPropertiesUtils.isTimestampOutdated(current_quiz_stamp)) {
            response.getWriter().println(gson.toJson(QuizTimingPropertiesUtils.getNewQuestion(current_game, datastore)));
            return;
        }
        
        response.getWriter().println(gson.toJson(current_game.getProperty("quizQuestion")));
    }
}