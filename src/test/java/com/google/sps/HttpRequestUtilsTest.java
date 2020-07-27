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
import com.google.sps.utils.HttpRequestUtils;
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
    public void something() {
        when(request.getParameter("name")).thenReturn("works");
        //HttpServletRequest request = mock(HttpServletRequest.class);
        // HttpServletRequest request = new HttpServletRequest();
        // request.

        String actual = HttpRequestUtils.getParameter(request, "name", "works");
        Assert.assertEquals("works", actual);
    }

}