package com.google.sps.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.plantasy.utils.UserUtils;
import com.google.plantasy.utils.CookieUtils;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      Cookie[] cookies = request.getCookies();
      Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);
      userEntity.setProperty("SessionID", "");
      datastore.put(userEntity);

      HttpSession session = request.getSession(false);
      if(session != null){
          session.invalidate();
      }

      Cookie cookie = CookieUtils.getCookieFromName(cookies,"SessionID");
      cookie.setMaxAge(0); 
      response.addCookie(cookie);
    }
}