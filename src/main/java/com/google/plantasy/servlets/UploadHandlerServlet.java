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
import com.google.plantasy.utils.UserUtils;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*; 
 
/** Blobstore upload handler: gets blobkey (Blobstore rewrites the request to contain a blobkey) 
    for an uploaded image and stores it in datastore 
    (see https://cloud.google.com/appengine/docs/standard/java/blobstore#3_implement_upload_handler
    for documentation of an upload handler)
 */
@WebServlet("/image-upload-handler-blobstore")
public class UploadHandlerServlet extends HttpServlet {
 
  BlobstoreService blobstoreService;
  DatastoreService datastore;
 
  public UploadHandlerServlet(){
    blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    datastore = DatastoreServiceFactory.getDatastoreService();
  }
 
  // TODO: Limit this so it can only be used as a blobstore upload handler; cut off end-user access
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
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

    UserUtils.addUploadPoints(userEntity, datastore);
    
    // getting the blobkey + making it part of the request
    ImmutableMap<String, List<BlobKey>> blobs = ImmutableMap.copyOf(blobstoreService.getUploads(request));
    ImmutableList<BlobKey> blobKeys = ImmutableList.copyOf(blobs.get("image"));
    String blobKey = blobKeys.get(0).getKeyString();
 
    UserUtils.addBlobKey(blobKey, userEntity, datastore);
    
    response.sendRedirect("/imageUpload.html"); 
  }
}
