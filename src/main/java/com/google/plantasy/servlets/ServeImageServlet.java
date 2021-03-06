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
 
package com.googl.plantasy.servlets;
 
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Serves the image file corresponding to the given blob key
*/ 
@WebServlet("/get-image")
public class ServeImageServlet extends HttpServlet {
 
  BlobstoreService blobstoreService;  
 
  public ServeImageServlet(){
    blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  }

  /**
   * Replaces the getServingUrl method (Obtains a URL that can dynamically 
   * serve the image stored as a blob by passing the blobkey to blobstore service)
  */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    BlobKey blobKey = new BlobKey(request.getParameter("blobKey"));
    blobstoreService.serve(blobKey, response);
  }
}
