
package com.google.sps;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.sps.CookieUtils; 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.sps.UserUtils;


/** tests the getEntityFromDatastore function (from UserUtils)*/
@RunWith(JUnit4.class)
public final class GetUserFromCookieTest{
    private DatastoreService datastore;
    private UserUtils userUtils;

    // helper variable allows the use of entities in testing 
  private final LocalServiceTestHelper helper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setUp() {
    helper.setUp();
    userUtils = new UserUtils();
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

//given a list of entities and a few cookies, can return the right entity
  @Test
  public void findEntityByCookie(){
      Entity user1 = new Entity("user");
      user1.setProperty("SessionID", "hell yea");
      datastore.put(user1);

      Entity user2 = new Entity("user");
      user2.setProperty("SessionID", "hell no");
      datastore.put(user2);

      Entity user3 = new Entity("user");
      user3.setProperty("SessionID", "hell maybe");
      datastore.put(user3);
      
      Cookie cookie1 = new Cookie("SessionID", "hell yea");
      Cookie cookie2 = new Cookie("hello", "olleh");
      Cookie cookie3 = new Cookie("salam", "malas");
      Cookie cookies[] = new Cookie[]{cookie1, cookie2, cookie3};

      Entity actual = userUtils.getUserFromCookie(cookies, datastore);
      Entity expected = user1;

      Assert.assertEquals(expected, actual); 
  }

  //given a cookie with the wrong name, returns null
  @Test
  public void wrongCookieName(){
      Entity user1 = new Entity("user");
      user1.setProperty("SessionID", "hellyea");
      datastore.put(user1);

      Entity user2 = new Entity("user");
      user2.setProperty("SessionID", "hell no");
      datastore.put(user2);

      Entity user3 = new Entity("user");
      user3.setProperty("SessionID", "hell maybe");
      datastore.put(user3);

    Cookie cookie1 = new Cookie("hellyea", "aey lleh");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies, datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual); 
  }

  //given a cookie w the wrong value, return null
  @Test
  public void wrongCookieValue(){
      Entity user1 = new Entity("user");
      user1.setProperty("SessionID", "hell yea");
      datastore.put(user1);

      Entity user2 = new Entity("user");
      user2.setProperty("SessionID", "hell no");
      datastore.put(user2);

      Entity user3 = new Entity("user");
      user3.setProperty("SessionID", "hell maybe");
      datastore.put(user3);

    Cookie cookie1 = new Cookie("SessionID", "aey lleh");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies, datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual); 
  }

  //given one user and one cookie, returns correct
  @Test
  public void oneUserOneCookie(){
    Entity user1 = new Entity("user");
    user1.setProperty("SessionID", "hell yea");
    datastore.put(user1);

    Cookie cookie1 = new Cookie("SessionID", "hell yea");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies,datastore);
    Entity expected = user1;
    
    Assert.assertEquals(expected, actual);
  }

  //everything else works, but no datastore passed in
  @Test
  public void noDatastore(){
    Entity user1 = new Entity("user");
    user1.setProperty("SessionID", "hell yea");
    datastore.put(user1);

    Cookie cookie1 = new Cookie("SessionID", "hell yea");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies, null);
    Entity expected = null;
    
    Assert.assertEquals(expected, actual);
  }

  //no entries in the datastore, everything else is fine
  @Test
  public void noEntities(){
    Cookie cookie1 = new Cookie("SessionID", "hell yea");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies, datastore);
    Entity expected = null;
    
    Assert.assertEquals(expected, actual);
  }

 //everything else works, but no cookies passed in
  @Test
  public void noCookies(){
    Entity user1 = new Entity("user");
    user1.setProperty("SessionID", "hell yea");
    datastore.put(user1);

    Cookie cookies[] = new Cookie[0];

    Entity actual = userUtils.getUserFromCookie(cookies, null);
    Entity expected = null;
    
    Assert.assertEquals(expected, actual);
  }
}