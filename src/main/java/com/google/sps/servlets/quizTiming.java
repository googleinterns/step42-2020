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
    quiz.setProperty("timestamp", System.currentTimeMillis());
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(quiz);

    response.sendRedirect("/gameBoard.html");      

  }
}