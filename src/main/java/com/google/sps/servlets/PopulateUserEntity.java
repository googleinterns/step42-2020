
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

@WebServlet("/populate-user-entity")
public class PopulateUserEntity extends HttpServlet {
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

     /**
    * Populates the datastore with "user" entities. 
    * This doPost must be called at least once for every new user.
    * It creates an entity that contains user's full name, user id,
    * the timestamp of the last quiz they took, and a mapping of the game scores. 
    *
    * If a user with the ID passed in already exists, this function does nothing. 
    *<p>
    *@param  name  the name of the user.
    *@param  id    the user's user ID (from Google Users API)
    */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String username = request.getParameter("name");
      String userID = request.getParameter("id");
      long initialTime = 0L;

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
        userEntity.setProperty("timeQuizTaken",initialTime);
        datastore.put(userEntity);
      }
  }
}
