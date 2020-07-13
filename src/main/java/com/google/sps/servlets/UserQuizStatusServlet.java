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

@WebServlet("/user-quiz-status-servlet")
public class UserQuizStatusServlet extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        QuizTimingPropertiesUtils timing_utils = new QuizTimingPropertiesUtils();
        String current_quiz_time = (timing_utils.getTimestampProperty("Game", datastore)).toString();
        String user_quiz_status = (timing_utils.getTimestampProperty("user", datastore)).toString();

        Gson gson = new Gson();
        response.setContentType("application/json;");

        response.getWriter().println(gson.toJson(timing_utils.userTookQuiz(user_quiz_status, current_quiz_time)));
    }
}