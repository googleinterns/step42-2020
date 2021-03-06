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
 
import javax.servlet.http.Cookie;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.plantasy.utils.UserUtils;
 
@WebServlet("/get-user-info")
public class GetUserInformation extends HttpServlet {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      //get current user from servlet
      Cookie[] cookies = request.getCookies();
      Entity currentUser = UserUtils.getUserFromCookie(cookies, datastore);
 
 
      Gson gson = new Gson();
      response.setContentType("application/json;");
      response.getWriter().println(gson.toJson(currentUser));
  }
}
