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
 
package com.google.plantasy;
 
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.plantasy.HttpRequestUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
 
@RunWith(JUnit4.class)
public final class HttpRequestUtilsTest {
 
    @Mock
    HttpServletRequest request;
 
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
 
    @Before
    public void setUp() {
        helper.setUp();
        MockitoAnnotations.initMocks(this);
    }
 
    @After
    public void tearDown() {
        helper.tearDown();
    }
 
    @Test
    //Tests when getParameterWithDefault is given valid parameters 
    public void getParameterWithDefault_validParameters() {
        when(request.getParameter("name")).thenReturn("works");
        String actual = HttpRequestUtils.getParameterWithDefault(request, "name", "works");
        Assert.assertEquals("works", actual);
    }
 
    @Test
    //Tests when getParameterWithDeafult is not given a request
    public void getParameterWithDefault_nullRequest() {
        String actual = "";
        try {
            actual = HttpRequestUtils.getParameterWithDefault(null, "name", "works");
        } catch(NullPointerException e) {
            Assert.assertEquals("", actual);
        }
    }
 
}
