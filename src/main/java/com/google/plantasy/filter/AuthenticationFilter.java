package com.google.sps.filter;
 
import javax.servlet.http.Cookie;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.plantasy.utils.UserUtils;
 
public class AuthenticationFilter implements Filter {
    private ServletContext context;
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }
 
    /**
    * Checks that a session ID stored as a cookie is also attached to a user entity in the database.
    * If it is not attached, it redirects to the login page.
    * 
    * This function should be called every time a user tries to go to a new page. 
    *
    */
 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
 
        Cookie cookies[] = req.getCookies();
        Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);
 
        //check to make sure there is an active session and that it's attached to a person
        if(userEntity == null){
            this.context.log("Unauthorized access request");
            res.sendRedirect("/login-page");
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }
}

