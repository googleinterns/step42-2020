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
import com.google.plantasy.HttpRequestUtils;
import com.google.plantasy.utils.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;

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
 
        //Adds points to the user who took the quiz & update their timestamp
        User current_user = new User(userEntity);
        UserUtils.addPoints(current_user, 20, datastore);
        current_user.setQuizTiming(System.currentTimeMillis());
        datastore.put(current_user.getEntity());
        
        //Adds points to the user who got voted for in the quiz 
        String clicked_user_id = HttpRequestUtils.getParameterWithDefault(request, "user_picture", "");
        Entity user_clicked = UserUtils.getEntityFromDatastore("user", "userID", clicked_user_id, datastore);
        User selected_user = new User(user_clicked); 
        UserUtils.addPoints(selected_user, 20, datastore);

       response.sendRedirect("/loggedin/gameBoard");
    }
}
