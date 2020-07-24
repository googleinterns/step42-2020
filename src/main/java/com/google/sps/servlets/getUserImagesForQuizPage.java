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
import com.google.appengine.api.datastore.Key;
import com.google.sps.utils.QuizTimingPropertiesUtils;
import com.google.sps.utils.UserUtils;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
 
//This servlet loads user images to the quiz page for a logged-in user 
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
 
        current_game = UserUtils.getEntityFromDatastore("Game", "gameId", (userEntity.getProperty("gameId")).toString(), datastore);
        for(String playerID : (ArrayList<String>) current_game.getProperty("userIDs")) {
            if(player.getProperty("userID").equals(playerID) && !user_entity.getProperty("userID").equals(playerID)) {
                user_ids_and_pictures.put((player.getProperty("userIds")).toString(), (player.getProperty("blobkey")).toString());
            }
        }
 
        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(user_ids_and_pictures));
    }
}
