package com.google.plantasy.servlets;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
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
      //set the cookie lifetime to 0 sec, removing it from the front end
      Cookie cookie = CookieUtils.getCookieFromName(cookies,"SessionID");
      cookie.setMaxAge(0); 
      response.addCookie(cookie);
    }
}
