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
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import com.google.sps.utils.User;
import com.google.sps.utils.UserUtils;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
@WebServlet("/populate-leaderboard")
public class populateLeaderboardServlet extends HttpServlet {
 
  DatastoreService datastore;
 
  public populateLeaderboardServlet(){
    datastore = DatastoreServiceFactory.getDatastoreService();
  }
  
  /**
  * Gets a list of users (user objects with score, name, id) that are playing the same game 
  */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
 
    // get user entity
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
 
    ArrayList<User> users = UserUtils.userList((String) userEntity.getProperty("gameId"), datastore);
 
    // translate to JSON for loadComments function
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(users));
  }
}
