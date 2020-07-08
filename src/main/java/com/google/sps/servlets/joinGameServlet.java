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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
 
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
    
    String gameName = request.getParameter("game-id");
    
    // Check if the game id exists
    Key gameKey = null;
    try{
        gameKey = KeyFactory.stringToKey(gameName);
    }catch(Exception e){ 
        // TODO: add an error message when an invalid code is given
        response.sendRedirect("/join.html");
    }

    // Get the game entity and connect the user to the game
    try{
        Entity gameEntity = datastore.get(gameKey);
        ArrayList<String> userNames = (ArrayList<String>) gameEntity.getProperty("userNames");
        // TODO:
        // userNames.add() -- add their username!
        gameEntity.setProperty("userNames", userNames);
        // TODO: update user entity to include the game
        datastore.put(gameEntity);
        response.sendRedirect("/gameBoard.html");  
    }catch(Exception e){
        response.sendRedirect("/join.html");
    }
  }
}
