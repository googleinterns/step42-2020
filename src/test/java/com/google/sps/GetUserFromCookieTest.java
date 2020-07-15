
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

    // helper variable allows the use of entities in testing 
  private final LocalServiceTestHelper helper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setUp() {
    helper.setUp();
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

//Test given a list of entities, and a list of cookies
  @Test
  public void findEntityByCookie(){
      UserUtils userUtils = new UserUtils();
      Entity user1 = new Entity("user");
      user1.setProperty("SessionID", "value1");
      datastore.put(user1);

      Entity user2 = new Entity("user");
      user2.setProperty("SessionID", "value2");
      datastore.put(user2);

      Entity user3 = new Entity("user");
      user3.setProperty("SessionID", "value3");
      datastore.put(user3);
      
      Cookie cookie1 = new Cookie("SessionID", "value1");
      Cookie cookie2 = new Cookie("name2", "value2");
      Cookie cookie3 = new Cookie("name3", "value3");
      Cookie cookies[] = new Cookie[]{cookie1, cookie2, cookie3};

      Entity actual = userUtils.getUserFromCookie(cookies, datastore);
      Entity expected = user1;

      Assert.assertEquals(expected, actual); 
  }

  //Test given a cookie with a name that doesn't match an entity
  @Test
  public void wrongCookieName(){
      UserUtils userUtils = new UserUtils();
      Entity user1 = new Entity("user");
      user1.setProperty("SessionID", "value1");
      datastore.put(user1);

      Entity user2 = new Entity("user");
      user2.setProperty("SessionID", "value2");
      datastore.put(user2);

      Entity user3 = new Entity("user");
      user3.setProperty("SessionID", "value3");
      datastore.put(user3);

    Cookie cookie1 = new Cookie("value1", "name1");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies, datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual); 
  }

  //Test where a cookie's value doesn't match any entities values
  @Test
  public void wrongCookieValue(){
      UserUtils userUtils = new UserUtils();
      Entity user1 = new Entity("user");
      user1.setProperty("SessionID", "value1");
      datastore.put(user1);

      Entity user2 = new Entity("user");
      user2.setProperty("SessionID", "value2");
      datastore.put(user2);

      Entity user3 = new Entity("user");
      user3.setProperty("SessionID", "value3");
      datastore.put(user3);

    Cookie cookie1 = new Cookie("SessionID", "wrongvalue");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies, datastore);
    Entity expected = null;

    Assert.assertEquals(expected, actual); 
  }

  //Test given a single cookie and a single entity
  @Test
  public void oneUserOneCookie(){
    UserUtils userUtils = new UserUtils();
    Entity user1 = new Entity("user");
    user1.setProperty("SessionID", "value1");
    datastore.put(user1);

    Cookie cookie1 = new Cookie("SessionID", "value1");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies,datastore);
    Entity expected = user1;
    
    Assert.assertEquals(expected, actual);
  }

  //Test where the datastore is not passed into the function
  @Test
  public void noDatastore(){
    UserUtils userUtils = new UserUtils();
    Entity user1 = new Entity("user");
    user1.setProperty("SessionID", "value1");
    datastore.put(user1);

    Cookie cookie1 = new Cookie("SessionID", "value1");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies, null);
    Entity expected = null;
    
    Assert.assertEquals(expected, actual);
  }

  //Test where there are no entities in the datastore
  @Test
  public void noEntities(){
    UserUtils userUtils = new UserUtils();
    Cookie cookie1 = new Cookie("SessionID", "value1");
    Cookie cookies[] = new Cookie[]{cookie1};

    Entity actual = userUtils.getUserFromCookie(cookies, datastore);
    Entity expected = null;
    
    Assert.assertEquals(expected, actual);
  }

 //Test where no cookies are passed into the function
  @Test
  public void noCookies(){
    UserUtils userUtils = new UserUtils();
    Entity user1 = new Entity("user");
    user1.setProperty("SessionID", "value1");
    datastore.put(user1);

    Cookie cookies[] = new Cookie[0];

    Entity actual = userUtils.getUserFromCookie(cookies, null);
    Entity expected = null;
    
    Assert.assertEquals(expected, actual);
  }
}
