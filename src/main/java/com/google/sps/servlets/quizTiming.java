package com.google.sps.servlets;
 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
 
@WebServlet("/quizTiming")
public class quizTiming extends HttpServlet {
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 
    Entity quiz = new Entity("Quiz");
    quiz.setProperty("quiz_timestamp", System.currentTimeMillis());
 
    Long defaultTime = 0L;
    String defaultQuestion = "";
 
    // Entity fake_game = new Entity("Game");
    // fake_game.setProperty("quiz_timestamp", defaultTime);
    // fake_game.setProperty("quizQuestion", defaultQuestion);
    // fake_game.setProperty("gameID", 67890);
    // fake_game.setProperty("userIDs", users);

    // Entity user = new Entity("user");
    // user.setProperty("quiz_timestamp", System.currentTimeMillis());
    // user.setProperty("userID", 12345);
    // user.setProperty("score", 0);

    Entity userOne = new Entity("user");
    userOne.setProperty("quiz_timestamp", System.currentTimeMillis());
    userOne.setProperty("currentGame", 67890);
    userOne.setProperty("blobkey", 12345);
    userOne.setProperty("userID", "abc");
    datastore.put(userOne);

    Entity userTwo = new Entity("user");
    userTwo.setProperty("quiz_timestamp", System.currentTimeMillis());
    userTwo.setProperty("currentGame", 67890);
    userTwo.setProperty("blobkey", 55555);
    userTwo.setProperty("userID", "def");
    datastore.put(userTwo);

    Entity userThree = new Entity("user");
    userThree.setProperty("quiz_timestamp", System.currentTimeMillis());
    userThree.setProperty("currentGame", 67890);
    userThree.setProperty("blobkey", 11111);
    userThree.setProperty("userID", "hij");
    datastore.put(userThree);

    List<String> usersList = new ArrayList<String>();
    usersList.add((userOne.getProperty("userID")).toString());
    usersList.add((userTwo.getProperty("userID")).toString());
    usersList.add((userThree.getProperty("userID")).toString());

    Entity fake_game = new Entity("Game");
    fake_game.setProperty("quiz_timestamp", defaultTime);
    fake_game.setProperty("quizQuestion", defaultQuestion);
    fake_game.setProperty("gameID", 67890);
    fake_game.setProperty("userIDs", usersList);
    
    datastore.put(quiz);
    datastore.put(fake_game);
    //datastore.put(user);
 
    response.sendRedirect("/gameBoard.html");      
 
  }
}
