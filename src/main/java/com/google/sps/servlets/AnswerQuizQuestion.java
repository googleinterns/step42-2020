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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clicked_user_id = getParameter(request, "user_picture", "");

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

        QuizTimingPropertiesUtils timing_properties = new QuizTimingPropertiesUtils();

        //First calls Hannah's helper function to get the specific user, will return user entity
        //Once that call is made, lines 32 through 34 will not be needed
        Query query = new Query("user");
        PreparedQuery pq = datastore.prepare(query);
        Entity userEntity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);

        Long quiz_time = timing_properties.getQuizTimestampProperty("Game", "gameID", userEntity.getProperty("currentGame").toString(), datastore);
        Long user_time = timing_properties.getQuizTimestampProperty("user", "userID", userEntity.getProperty("userID").toString(), datastore);

        boolean value = timing_properties.giveUserQuizTakenPoints(timing_properties.userTookQuiz(user_time, quiz_time), userEntity, datastore);        

        Query queryTwo = new Query("user");
        PreparedQuery pqTwo = datastore.prepare(queryTwo);
        for(Entity user_clicked : pqTwo.asIterable()){
            if(user_clicked.getProperty("userID").equals(clicked_user_id)) {
                user_clicked.setProperty("score", ((Number) user_clicked.getProperty("score")).intValue() + 20);
                datastore.put(user_clicked);
            }
        }
       response.sendRedirect("/gameBoard.html");
    }
    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
      String value = request.getParameter(name);
      if (value == null) {
          return defaultValue;
      }
      return value;
    }

    // public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //     // Cookie cookies[] = request.getCookies();
    //     // if(cookies == null){
    //     //     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //     //     return;
    //     // }
    //     // Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);
    //     // if(userEntity == null){
    //     //     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //     //     return;
    //     // }

    //     QuizTimingPropertiesUtils timing_properties = new QuizTimingPropertiesUtils();

    //     //First calls Hannah's helper function to get the specific user, will return user entity
    //     //Once that call is made, lines 32 through 34 will not be needed
    //     Query query = new Query("user");
    //     PreparedQuery pq = datastore.prepare(query);
    //     Entity userEntity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);

    //     Long quiz_time = timing_properties.getQuizTimestampProperty("Game", "gameID", userEntity.getProperty("currentGame").toString(), datastore);
    //     Long user_time = timing_properties.getQuizTimestampProperty("user", "userID", userEntity.getProperty("userID").toString(), datastore);

    //     boolean value = timing_properties.giveUserQuizTakenPoints(timing_properties.userTookQuiz(user_time, quiz_time), userEntity, datastore);

    //     Gson gson = new Gson();
    //     response.setContentType("application/json;");
    //     response.getWriter().println(gson.toJson(userEntity));    
    // }
}
