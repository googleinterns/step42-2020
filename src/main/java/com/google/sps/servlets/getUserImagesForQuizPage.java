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
import java.util.HashMap;

@WebServlet("/get-user-images")
public class getUserImagesForQuizPage extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Cookie cookies[] = request.getCookies();
        // Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);

        //Get cookies of user and then the current game from the user 

        Query query = new Query("user");
        PreparedQuery pq = datastore.prepare(query);
 
        Entity user_entity = pq.asList(FetchOptions.Builder.withLimit(1)).get(0);

        Query queryTwo = new Query("Game");
        PreparedQuery pqTwo = datastore.prepare(queryTwo);

        HashMap<String, HashMap> user_photo_info = new HashMap<String, HashMap>();
        HashMap<String,String> user_ids_and_pictures = new HashMap<String, String>();

        for(Entity game : pqTwo.asIterable()){
            if(game.getProperty("gameID").equals(user_entity.getProperty("currentGame"))){
                for(String playerID : (ArrayList<String>) game.getProperty("userIDs")) {
                    for(Entity player : pq.asIterable()) {
                        if(player.getProperty("userID").equals(playerID) && !user_entity.getProperty("userID").equals(playerID)) {
                            System.out.println("twice");
                            user_ids_and_pictures.put((player.getProperty("userID")).toString(), (player.getProperty("blobkey")).toString());
                            // users_playing_the_game.add((player.getProperty("userID")).toString());
                            // users_playing_blob_keys.add((player.getProperty("blobkey")).toString());
                        }
                    }
                    // user_ids_and_pictures.put(users_playing_the_game, users_playing_blob_keys);
                    // user_photo_info.put("user", user_ids_and_pictures);
                }
            }
        }

        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(user_ids_and_pictures));
    }
}