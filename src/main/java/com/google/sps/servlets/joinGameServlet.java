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
import com.google.sps.utils.GameUtils;
import com.google.sps.utils.UserUtils; 
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
 
/** When a user joins a game, check if the given game id exists
    if so, get the game entity and add the user,
    then add the game to the user entity.
    If it doesn't exist, redirect back to the join game page.
*/
@WebServlet("/join-game")
public class joinGameServlet extends HttpServlet {
 
  DatastoreService datastore;
 
  public joinGameServlet(){
    datastore = DatastoreServiceFactory.getDatastoreService();
  }
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String gameId = request.getParameter("game-id");
 
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

    // prevent users from joining more than one game
    if(userEntity.getProperty("gameId") != null){
      response.sendRedirect("/gameBoard.html");
      return;
    }

    Entity gameEntity = UserUtils.getEntityFromDatastore("Game", "gameId", gameId, datastore);
    if(gameEntity == null){
      PrintWriter out = response.getWriter();
      out.println("<p>We couldn't find a game by that code, <a href = \"join.html\"><h3>press here</h3></a> and enter a different game code.</p>");
      return;
    }
    boolean setGame = GameUtils.setGame(userEntity, datastore, gameEntity);

    if(!setGame){
      // connecting the game to the user failed because the user was not logged in, send back to login
      response.sendRedirect("/index.html");
      return;
    }else{
      response.sendRedirect("/gameBoard.html");
      return;
    }
  }
}
