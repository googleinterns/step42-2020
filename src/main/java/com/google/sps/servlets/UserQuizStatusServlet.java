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
import com.google.sps.UserUtils;
 
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

        QuizTimingPropertiesUtils timing_utils = new QuizTimingPropertiesUtils();
        Long current_quiz_time = timing_utils.getQuizTimestampProperty("Game", "currentGame", userEntity.getProperty("currentGame").toString(), datastore);
        Long user_quiz_status = timing_utils.getQuizTimestampProperty("user", "userID", userEntity.getProperty("userID").toString(), datastore);
 
        Gson gson = new Gson();
        response.setContentType("application/json;");
 
        response.getWriter().println(gson.toJson(timing_utils.userTookQuiz(user_quiz_status, current_quiz_time)));
    }
}
