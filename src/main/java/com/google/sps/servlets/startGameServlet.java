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
 
/** Create game entity and connect it to the user, then update the user entity to include the game id
    and store both in datastore when the user creates a new game.
 */
@WebServlet("/start-game")
public class startGameServlet extends HttpServlet {
 
  DatastoreService datastore;
 
  public startGameServlet(){
    datastore = DatastoreServiceFactory.getDatastoreService();
  }
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
 
    String gameName = request.getParameter("game-name");
    
    // query user 
    Cookie cookies[] = request.getCookies();
    Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);
 
    String newGame = GameUtils.joinGame(userEntity, gameName, datastore, null);
 
    if(newGame == "noGameName"){
      response.sendRedirect("/start.html");
      return;
    }
 
    if(newGame == "nullUserEntity" || newGame == "createGameFailed" || newGame == "userAddedFailed" || newGame == "gameAddedFailed"){
      response.sendRedirect("/index.html");
      return;
    }
 
    if(newGame == "alreadyHasGame"){
      PrintWriter out = response.getWriter();
      out.println("<p>You are already in a game, see the gameboard here:</p>");
      out.println("<a href = \"gameBoard.html\"><h3>Your game</h3></a>");
      return;
    }
 
    if(newGame == "success"){
      response.sendRedirect("/gameBoard.html");
      return;
    }
  }
}
