package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.QueryResultList;
import java.text.DateFormat;
import java.util.Date;
import com.google.appengine.api.datastore.Key;

@WebServlet("/getQuizStatus")
public class getDailyQuiz extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Query query = new Query("Quiz");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery pq = datastore.prepare(query);

        Entity quiz = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
        Object time = quiz.getProperty("timestamp");     

        String quiz_date = DateFormat.getDateInstance().format(time);
        String today_date = DateFormat.getDateInstance().format(new Date());

        Gson gson = new Gson();
        response.setContentType("application/json;");
        if(today_date.compareTo(quiz_date) > 0) {
            Key key_of_entity = quiz.getKey();
            datastore.delete(key_of_entity);
            Entity updated_quiz = new Entity(key_of_entity);
            updated_quiz.setProperty("timestamp", System.currentTimeMillis());
            datastore.put(updated_quiz);
            response.getWriter().println(gson.toJson(quiz));
        } else {
            response.getWriter().println(gson.toJson("NoNewButton"));
        }
    }
}