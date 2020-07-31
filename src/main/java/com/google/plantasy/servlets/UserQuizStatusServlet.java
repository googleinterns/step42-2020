// package com.google.plantasy.servlets;
 
// import com.google.appengine.api.datastore.DatastoreService;
// import com.google.appengine.api.datastore.DatastoreServiceFactory;
// import com.google.appengine.api.datastore.Entity;
// import com.google.appengine.api.datastore.PreparedQuery;
// import com.google.appengine.api.datastore.Query;
// import com.google.gson.Gson;
// import java.io.IOException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import com.google.appengine.api.datastore.FetchOptions;
// import java.text.DateFormat;
// import java.util.Date;
// import com.google.appengine.api.datastore.Key;
// import com.google.plantasy.QuizTimingPropertiesUtils;
// import com.google.plantasy.UserUtils;
 
// //This servlet checks to see if the user took the quiz that was available today
// //If they did take the quiz the true boolean value will be returned 
// @WebServlet("/user-quiz-status-servlet")
// public class UserQuizStatusServlet extends HttpServlet {
 
//     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 
//     public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
 
//         Cookie cookies[] = request.getCookies();
//         if(cookies == null){
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             return;
//         }
//         Entity userEntity = UserUtils.getUserFromCookie(cookies, datastore);
//         if(userEntity == null){
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             return;
//         }
 
//         QuizTimingPropertiesUtils timing_utils = new QuizTimingPropertiesUtils();
//         //These variables get the last time the user took a quiz and the latest time a quiz was created
//         Long time_quiz_was_made = timing_utils.getQuizTimestampProperty("Game", "currentGame", userEntity.getProperty("currentGame").toString(), datastore);
//         Long latest_time_user_took_quiz = timing_utils.getQuizTimestampProperty("user", "userID", userEntity.getProperty("userID").toString(), datastore);
 
//         Gson gson = new Gson();
//         response.setContentType("application/json;");
 
//         response.getWriter().println(gson.toJson(timing_utils.userTookQuiz(latest_time_user_took_quiz, current_quiz_time)));
//     }
// }
