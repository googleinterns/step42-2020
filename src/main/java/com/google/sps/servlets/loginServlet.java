// // Copyright 2019 Google LLC
// //
// // Licensed under the Apache License, Version 2.0 (the "License");
// // you may not use this file except in compliance with the License.
// // You may obtain a copy of the License at
// //
// //     https://www.apache.org/licenses/LICENSE-2.0
// //
// // Unless required by applicable law or agreed to in writing, software
// // distributed under the License is distributed on an "AS IS" BASIS,
// // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// // See the License for the specific language governing permissions and
// // limitations under the License.

// package com.google.sps.servlets;

// import com.google.appengine.api.users.UserService;
// import com.google.appengine.api.users.UserServiceFactory;
// import java.io.IOException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
// import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
// import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
// import com.google.gson.Gson;

// @WebServlet("/loginStatus")
// //CHANGE EVERYTHING IN THIS FILE
// public class CheckLoginServlet extends HttpServlet {

//   @Override
//   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
//     GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//         // Specify the CLIENT_ID of the app that accesses the backend:
//         .setAudience(Collections.singletonList(CLIENT_ID))
//         // Or, if multiple clients access the backend:
//         //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
//         .build();

//     // (Receive idTokenString by HTTPS POST)

//     GoogleIdToken idToken = verifier.verify(idTokenString);
//     if (idToken != null) {
//     Payload payload = idToken.getPayload();

//     // Print user identifier
//     String userId = payload.getSubject();
//     System.out.println("User ID: " + userId);

//     // Get profile information from payload
//     String email = payload.getEmail();
//     boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//     String name = (String) payload.get("name");
//     String pictureUrl = (String) payload.get("picture");
//     String locale = (String) payload.get("locale");
//     String familyName = (String) payload.get("family_name");
//     String givenName = (String) payload.get("given_name");

//     // Use or store profile information
//     // ...

//     } else {
//     System.out.println("Invalid ID token.");
//     }
// }

//   @Override
//   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//   }

// }

