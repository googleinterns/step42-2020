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
import java.util.ArrayList;
 
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

      // TODO: get username and add it to the list
      ArrayList<String> userNames = new ArrayList<>();
      // TODO: add list of scores to game object 
      // ArrayList<scores> scores = new ArrayList<>()
 
      // create entity
      Entity gameEntity = new Entity("Game");
      gameEntity.setProperty("gameName", gameName);
      gameEntity.setProperty("userNames", userNames);
      // gameEntity.setProperty("scores", scores);
    
      // TODO: update user entity 
      // Query query = new Query("User");
      // PreparedQuery results = datastore.prepare(query);
      // TODO: add game to user entity 
 
      // store entity
      // datastore.put(userEntity);
      datastore.put(gameEntity);
 
      response.sendRedirect("/gameBoard.html");
  }
}
