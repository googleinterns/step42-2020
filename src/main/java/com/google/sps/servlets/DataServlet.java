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
 
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/** Blobstore upload handler: stores blob keys for plant images in Datastore */
@WebServlet("/blobstore-image-upload-handler")
public class DataServlet extends HttpServlet {

  BlobstoreService blobstoreService;
  DatastoreService datastore;

  public DataServlet(){
    blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  // TODO: Limit this so it can only be used as a blobstore upload handler; cut off end-user access
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // TODO: usernames
    // String username = request.getParameter("username");
 
    ImmutableMap<String, List<BlobKey>> blobs = ImmutableMap.copyOf(blobstoreService.getUploads(request));
    ImmutableList<BlobKey> blobKeys = ImmutableList.copyOf(blobs.get("image"));
    String blobKey = blobKeys.get(0).getKeyString();
 
    // TODO: might need a loop in here to check if that username already has a
    // photo and replace it
 
    Entity plantImageEntity = new Entity("plantImage");
    // plantImageEntity.setProperty("username", username); 
    plantImageEntity.setProperty("blobKey", blobKey);

    datastore.put(plantImageEntity);
    
    response.sendRedirect("/pictureUpload.html"); // TODO: send this somewhere else
  }
}
