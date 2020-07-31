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
 
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.sps.HttpRequestUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import java.io.IOException;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import com.google.sps.utils.PasswordReaderUtils;
 
@RunWith(JUnit4.class)
public final class PasswordReaderUtilsTest{
 
    //Test where the .txt file has more than 1 line of code
    @Test
    public void getFirstLineFromFile_multipleLines() throws IOException{
        String expected = "value1";
        String actual = PasswordReaderUtils.getFirstLineFromFile("src/test/testData/passwordTest.txt");
 
        Assert.assertEquals(expected, actual);
    }
 
    //Test where the .txt file only has one line of code
    @Test
    public void getFirstLineFromFile_oneLine() throws IOException{
        String expected = "value1";
        String actual = PasswordReaderUtils.getFirstLineFromFile("src/test/testData/passwordTest2.txt");
 
        Assert.assertEquals(expected, actual);
    }
 
    //Test with no lines in the .txt file
    @Test
    public void getFirstLineFromFile_noLines() throws IOException{
        String expected = null;
        String actual = PasswordReaderUtils.getFirstLineFromFile("src/test/testData/passwordTest3.txt");
 
        Assert.assertEquals(expected, actual);
    }
}
