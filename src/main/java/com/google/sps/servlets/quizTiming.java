package com.google.sps.servlets;
 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet("/quizTiming")
public class quizTiming extends HttpServlet {
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
 
    Entity quiz = new Entity("Quiz");
    quiz.setProperty("quiz_timestamp", System.currentTimeMillis());
 
    Long defaultTime = 0L;
    String defaultQuestion = "";
 
    Entity fake_game = new Entity("Game");
    fake_game.setProperty("quiz_timestamp", defaultTime);
    fake_game.setProperty("quizQuestion", defaultQuestion);

    Entity score = new Entity("Score");
    score.setProperty("userID", 12345);
    score.setProperty("gameID", 67890)
    score.setProperty("score", 0)

    Entity user = new Entity("user");
    user.setProperty("quiz_timestamp", System.currentTimeMillis());
    user.
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(quiz);
    datastore.put(fake_game);
    datastore.put(score);
 
    response.sendRedirect("/gameBoard.html");      
 
  }
}
