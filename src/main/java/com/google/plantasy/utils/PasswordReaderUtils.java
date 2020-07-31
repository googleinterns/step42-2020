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
 
package com.google.sps.utils;
 
import java.io.*; 
import java.util.logging.Logger;
 
public final class PasswordReaderUtils {
    private static final Logger log = Logger.getLogger(PasswordReaderUtils.class.getName());
 
 
    //takes in a file location and outputs the file as a String
    //API key must be stored on a single line with no other information.
    public static String getFirstLineFromFile(String fileLocation) throws IOException, FileNotFoundException{
        
        File file;
        try{
            file = new File(fileLocation);
        } catch(Exception e){
            log.severe("Could not find file at location: " + fileLocation);
            return "noKey";
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader); 
        String apiKey = "noKey";
 
        apiKey = br.readLine();
        fileReader.close();
 
        return apiKey;
    }
}