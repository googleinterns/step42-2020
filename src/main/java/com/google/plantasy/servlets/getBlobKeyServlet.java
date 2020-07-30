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
 
package com.google.plantasy.servlets;
 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.plantasy.utils.UserUtils; 
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
/** Retrieves blobkey from Datastore for the image of the logged in user */
@WebServlet("/get-blob-key")
public class getBlobKeyServlet extends HttpServlet {
  
  DatastoreService datastore;
 
  public getBlobKeyServlet(){
    datastore = DatastoreServiceFactory.getDatastoreService();
  }
 
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
 
    // get the user entity
    Cookie cookies[] = request.getCookies();
    if(cookies == null){
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }
    Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);
    if(userEntity == null){
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }
 
    String blobKey = (String) userEntity.getProperty("blobKey");
    if(blobKey == null){
      // if the user hasn't uploaded a picture yet, nothing is printed
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
 
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(blobKey));
  }
} 
