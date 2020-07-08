
package com.google.sps.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

@WebServlet("/user")
public class popUserEntity extends HttpServlet {
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String username = request.getParameter("name");
      String userID = request.getParameter("id");
      ArrayList<GameScores> userScoresArrayList = new ArrayList<GameScores>(); //this gets populated when the user joins a game.

    //check if user entity is already in system
     Filter getCorrectUser = new FilterPredicate("userID", FilterOperator.EQUAL, userID);
     Query query = new Query("user");
     query.setFilter(getCorrectUser);

     PreparedQuery results = datastore.prepare(query);
      
      List<Entity> limitedResults = results.asList(FetchOptions.Builder.withLimit(1)); //should never be more than 1, no 2 user IDs are ever the same

      if(limitedResults.size() == 0){
        Entity userEntity = new Entity("user");
        userEntity.setProperty("username",username);
        userEntity.setProperty("userID",userID);
        userEntity.setProperty("gameScores",userScoresArrayList);
        datastore.put(userEntity);
      }
  }
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      //check the logged in user and get the photo of the logged in user.
    String userID = request.getParameter("id");

     Filter getCorrectUser = new FilterPredicate("userID", FilterOperator.EQUAL, userID);
     Query query = new Query("user");
     query.setFilter(getCorrectUser);
     PreparedQuery results = datastore.prepare(query);
     
     //this should always work, as long as doPost is always called upon login
     try{
         Entity userEntity = results.asList(FetchOptions.Builder.withLimit(1)).get(0);
     } catch (Exception e){
         System.err.println("user entity uninitialized. cannot return information.");
     }
  }
}
