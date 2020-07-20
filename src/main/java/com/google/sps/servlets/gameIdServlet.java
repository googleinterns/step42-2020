// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
 
package com.google.sps.servlets;
 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
 
/** Gets the game id of a particular user from datastore and returns the key as a string and the game name */
@WebServlet("/game-id")
public class gameIdServlet extends HttpServlet {
 
  DatastoreService datastore;
 
  public gameIdServlet(){
    datastore = DatastoreServiceFactory.getDatastoreService();
  }
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
 
    //create and prepare a query
    Query query = new Query("Game");
    PreparedQuery results = datastore.prepare(query);
 
    // TODO: return the game id for the game attached to the given username
    // currently just gets a random game
    List<Entity> resultsList = results.asList(FetchOptions.Builder.withLimit(1));
    Entity entity = resultsList.get(0);
    String key = KeyFactory.keyToString((entity.getKey()));
    String name = (String) entity.getProperty("gameName");

    // list to return with both game name and key
    ArrayList<String> gameProperties = new ArrayList<>();
    gameProperties.add(name);
    gameProperties.add(key);
 
    Gson gson = new Gson();
 
    // Send JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(gameProperties)); 
  }
}
