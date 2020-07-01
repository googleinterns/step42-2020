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

/** Stores and retrieves posts from Datastore */
@WebServlet("/get-blob-key")
public class getBlobKeyServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Query query = new Query("plantImage").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // TODO: at the moment, this just gets a random picture from the database, we'll
    // have to attach usernames 
    List<Entity> resultsList = results.asList(FetchOptions.Builder.withLimit(1));
    String blobKey = "no deal"; // TODO: find a better method for fixing this bug if it's still an issue
    if(resultsList.size() != 0){
      Entity entity = resultsList.get(0);
      blobKey = (String) entity.getProperty("blobKey");
    }

    Gson gson = new Gson();

    // Send JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(blobKey));
  }
}