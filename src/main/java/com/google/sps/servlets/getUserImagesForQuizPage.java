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
import com.google.sps.QuizTimingPropertiesUtils;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/get-user-images")
public class getUserImagesForQuizPage extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Cookie cookies[] = request.getCookies();
        // Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);

        Query query = new Query("user");
        PreparedQuery pq = datastore.prepare(query);
 
        Entity user_entity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);
        System.out.println(user_entity.getProperty("currentGame"));

        Query queryTwo = new Query("game");
        PreparedQuery pqTwo = datastore.prepare(queryTwo);

        List<Object> users;

        for(Entity game : pqTwo.asIterable()){
            if(game.getProperty("gameID") == user_entity.getProperty("currentGame")){
                users = game.getProperty("userIDs");
            }
        }

        // String blobKey = "noKey";
        // if(userEntity != null){
        // blobKey = (String) userEntity.getProperty("blobKey");
        // }
        // if(blobKey == null){
        //     // if the user hasn't uploaded a picture yet, nothing is printed
        //     blobKey = "noKey";
        // }
    
        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(users));
    }
}