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
import com.google.sps.utils.UserUtils;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

//This servlet's request comes from the quiz.html page; the request is the ID of the user who was voted for in a quiz
//This servelt's functionality adds points the user who took a quiz and adds points the person who is voted for in a quiz
@WebServlet("/quiz-points")
public class QuizPoints extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

        //Adds points to the user who took the quiz
        Long quiz_time = QuizTimingPropertiesUtils.getQuizTimestampProperty("Game", "gameID", userEntity.getProperty("currentGame").toString(), datastore);
        Long user_time = QuizTimingPropertiesUtils.getQuizTimestampProperty("user", "userID", userEntity.getProperty("userID").toString(), datastore);
        QuizTimingPropertiesUtils.giveUserQuizTakenPoints(QuizTimingPropertiesUtils.userTookQuiz(user_time, quiz_time), userEntity, datastore);        

        //Adds points to the user who got voted for in the quiz 
        String clicked_user_id = getParameter(request, "user_picture", "");
        Entity user_clicked = UserUtils.getEntityFromDatastore("user", "userID", clicked_user_id, datastore);
        user_clicked.setProperty("score", ((Number) user_clicked.getProperty("score")).intValue() + 20);
        datastore.put(user_clicked);

       response.sendRedirect("/gameBoard.html");
    }

    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
      String value = request.getParameter(name);
      if (value == null) {
          return defaultValue;
      }
      return value;
    }
}
