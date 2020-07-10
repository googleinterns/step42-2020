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

@WebServlet("/user-quiz-status-servlet")
public class UserQuizStatusServlet extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        boolean status = false;
        Object quiz_queried = getTimestampProperty("Quiz");

        // PreparedQuery pq = datastore.prepare(query);

        // Object user_quiz_status = getTimestampProperty("user");
        //TODO - Get game id from user entity
        // Object current_quiz_time = getTimestampProperty("Game");

        Gson gson = new Gson();
        response.setContentType("application/json;");

        // if(user_quiz_status > current_quiz_time) {
        //     status = true;
        //     response.getWriter().println(gson.toJson(status));
        // } else {
        //     response.getWriter().println(gson.toJson(quiz_queried));
        // }
        String val = Long.toString(1594309443653L);
        String quiz = quiz_queried.toString();
        if(val.compareTo(quiz) > 0) {
            status = true;
            response.getWriter().println(gson.toJson(status));
        } else {
            response.getWriter().println(gson.toJson(status));
        }
    }

    public Object getTimestampProperty(String entity) {
        Query query = new Query(entity);
        PreparedQuery pq = datastore.prepare(query);

        Entity fetched_item = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
        Object timestamp_of_fetcheditem = fetched_item.getProperty("timestamp");
        
        return timestamp_of_fetcheditem;
    }

}