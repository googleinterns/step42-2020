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
        Long quizTime = timing_properties.getTimestampProperty("Game", datastore);

        //First calls Hannah's helper function to get the specific user, will return user entity
        //Once that call is made, lines 32 through 34 will not be needed
        Query query = new Query("user");
        PreparedQuery pq = datastore.prepare(query);
        Entity user = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);

        Boolean value = timing_properties.giveUserPoints(timing_properties.userTookQuiz(userTime, quizTime), user, datastore);

        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(user));
        
    }
}
