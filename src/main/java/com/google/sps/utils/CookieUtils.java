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

package com.google.sps;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public final class CookieUtils {
     private static final Logger log = Logger.getLogger(UserUtils.class.getName());
        /**
    * Returns a single Cookie object that can then be used in a servlet. 
    * The name arguement must be the name of a cookie active
    * on the front-end for a Cookie to be retrieved.
    * <p>
    * 
    * @param  cookies  an array of cookies (usually all of the cookies on the front end)
    * @param  name     the name of a specific cookie
    * @return          the cookie w the name passed in as a parameter
    */

    public static Cookie getCookieFromName(Cookie cookies[], String name){
        if(name == ""){
            log.severe("found null search parameter while trying to run function getCookieFromName");
            return null;
        }
        if(cookies.length == 0){
             log.severe("found no cookies while trying to run function getCookieFromName");
             return null;
        }
         
        for(int i = 0; i < cookies.length; i++){
            Cookie cookie = cookies[i];
            if(cookie.getName() == name){
                return cookie;
            }
        }
        return null;
    }
}
