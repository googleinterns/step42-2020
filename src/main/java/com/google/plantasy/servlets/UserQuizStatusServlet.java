package com.google.plantasy.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.plantasy.utils.QuizTimingPropertiesUtils;
import com.google.plantasy.utils.UserUtils;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

//This servlet checks to see if the user took the quiz that was available today
//If they did take the quiz the true boolean value will be returned 
@WebServlet("/user-quiz-status-servlet")
public class UserQuizStatusServlet extends HttpServlet {

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

        //These variable gets the timestamp property of the user
        Long latest_time_user_took_quiz = QuizTimingPropertiesUtils.getQuizTimestampProperty("user", "userID", userEntity.getProperty("userID").toString(), datastore);

        Gson gson = new Gson();
        response.setContentType("application/json;");

        response.getWriter().println(gson.toJson(QuizTimingPropertiesUtils.userTookQuiz(latest_time_user_took_quiz)));
    }
}

