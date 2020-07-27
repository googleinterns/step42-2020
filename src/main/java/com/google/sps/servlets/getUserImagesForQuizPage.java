package com.google.sps.servlets;
 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.sps.utils.QuizTimingPropertiesUtils;
import com.google.sps.utils.UserUtils;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.HashMap;
 
//This servlet loads user images to the quiz page for a logged-in user 
//The return will be a JSON map from userIDs to image blobkey for all the users playing a game minus the logged in user
//The logged in user is excluded because we don't want the loggedin users photo to appear on the quiz page
// If the logged in user's photo was on the quiz page then they would be able to vote for their own image in the game
@WebServlet("/get-user-images")
public class getUserImagesForQuizPage extends HttpServlet {
 
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
 
        HashMap<String,String> user_ids_and_pictures = new HashMap<String, String>();
 
        Entity current_game = UserUtils.getEntityFromDatastore("Game", "gameId", (userEntity.getProperty("gameId")).toString(), datastore);
        for(String player : (ArrayList<String>) current_game.getProperty("userIds")) {
            if(player.getProperty("userID").equals(playerID) && !userEntity.getProperty("userID").equals(playerID)) {
                user_ids_and_pictures.put((player.getProperty("userID")).toString(), (player.getProperty("blobkey")).toString());
            }
        }
 
        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(user_ids_and_pictures));
    }
}
