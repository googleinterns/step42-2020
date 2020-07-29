package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.util.Collections;
import java.util.Enumeration;
import com.google.sps.utils.UserUtils;
import com.google.sps.utils.CookieUtils;

     /**
    * Verifies that the Google token is legitimate. Then creates a 
    * cookie storing the Session ID and stores the login information in the user entity in datastore.
    *
    * User Entity:
    *    username      The user's full name (from their google account)
    *    userID        A unique string that can identify each user (from their google account)
    *    SessionID     A unique ID that changes every time a user logs in
    *    quiz_timing   A value that tracks the last time the user took a quiz. (Used for game)
    *    blobkey       A value that can be used to access a picture uploaded by the user
    *    currentGame   A string representing the game the user is currently in
    *    score         The score of the user (int)
    *
    *
    * If a User Entity already exists in the database, the only part of the entry that is updated
    * is the SessionID property. 
    *
    *<p>
    *@param  idToken  a token used to identify the user.
    *@return          a cookie that contains the session ID
    *@return          (ONLY IF ONE DOESNT ALREADY EXIST) a user entity in datastore
    */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
     
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    JsonFactory jsonFactory = new JacksonFactory();
    HttpTransport transport = new NetHttpTransport(); 

    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            .setAudience(Collections.singletonList("610251294652-9ojdhjhh8kpcdvdbmrvp9nkevgr2n2d8.apps.googleusercontent.com"))
            .build();

    String idTokenString = request.getHeaders("idtoken").nextElement(); 
       //request.getHeaders("idtoken") returns an Enumeration, but you only need the
       //first (and only) value. By putting the "idtoken" parameter, it only returns 
       //the value of the header with the name "idtoken".

    try{
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
          //create coookie w session ID
          String sessionID = request.getSession(true).getId(); 
          Cookie sessionIDcookie = CookieUtils.createSessionIDCookie(request.getSession(false), sessionID);
          response.addCookie(sessionIDcookie);

          Payload payload = idToken.getPayload();
          //get user info for user entity
          String userId = payload.getSubject();
          String username = (String) payload.get("name");

          //send user data to datastore
          Entity userEntity = UserUtils.getEntityFromDatastore("user", "userID", userId, datastore);
          if(userEntity == null){
            datastore.put(UserUtils.initializeUser(userId, username, sessionID));
          } else {
            userEntity.setProperty("SessionID",sessionID);
            datastore.put(userEntity);
        }
        } else {
          System.out.println("Invalid ID token.");
       }
    } catch(Exception e){
        e.printStackTrace();
    }
    }
}
