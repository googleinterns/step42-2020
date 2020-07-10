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

@WebServlet("/getDailyQuiz")
public class getDailyQuiz extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Query query = new Query("Game");
        PreparedQuery pq = datastore.prepare(query);

        Entity game_entity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
        Object current_quiz_stamp = game_entity.getProperty("quizTime");     

        String quiz_date = DateFormat.getDateInstance().format(current_quiz_stamp);
        String today_date = DateFormat.getDateInstance().format(new Date());

        Gson gson = new Gson();
        response.setContentType("application/json;");

        if(today_date.compareTo(quiz_date) > 0) {
            Key game_key = game_entity.getKey();
            datastore.delete(game_key);
            Entity update_game_entity = new Entity(game_key);
            update_game_entity.setProperty("quizTime", System.currentTimeMillis());
            datastore.put(update_game_entity);
            response.getWriter().println(gson.toJson())
        }

        // Query query = new Query("Quiz");
        // PreparedQuery pq = datastore.prepare(query);

        // Entity quiz = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
        // Object current_quiz_stamp = quiz.getProperty("timestamp");     

        // String quiz_date = DateFormat.getDateInstance().format(current_quiz_stamp);
        // String today_date = DateFormat.getDateInstance().format(new Date());

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