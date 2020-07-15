// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.sps.CookieUtils; 

@RunWith(JUnit4.class)
public final class GetCookieGivenNameTest {
    private CookieUtils cookieUtils;

  @Before
  public void setUp() {
    cookieUtils = new CookieUtils();
  }

   @After
  public void tearDown() {
  }

  //given a list of cookies, it should find the one with the mentioned name.
  @Test
  public void findCookieByName() {
        Cookie cookie1 = new Cookie("hello", "got it");
        Cookie cookie2 = new Cookie("hi", "not this one");
        Cookie cookie3 = new Cookie("heyyy", "not this one either");

        Cookie cookieArray[] = new Cookie[]{cookie1,cookie2,cookie3};

        Cookie actual = cookieUtils.getCookieGivenName(cookieArray, "hello");
        Cookie expected = cookie1;

         Assert.assertEquals(expected, actual);     
  }

//given a list of cookies and a name that isn't in the list, returns null
  @Test
  public void cookiesWithWrongName() {
        Cookie cookie1 = new Cookie("hello", "got it");
        Cookie cookie2 = new Cookie("hi", "not this one");
        Cookie cookie3 = new Cookie("heyyy", "not this one either");

        Cookie cookieArray[] = new Cookie[]{cookie1,cookie2,cookie3};

        Cookie actual = cookieUtils.getCookieGivenName(cookieArray, "salam");
        Cookie expected = null;

         Assert.assertEquals(expected, actual);     
  }

//given a list with 1 cookie and the correct name, return the cookie
  @Test
  public void oneCookieAndName() {
        Cookie cookie1 = new Cookie("hello", "this is it");

        Cookie cookieArray[] = new Cookie[]{cookie1};

        Cookie actual = cookieUtils.getCookieGivenName(cookieArray, "hello");
        Cookie expected = cookie1;

         Assert.assertEquals(expected, actual);     
  }

//given a cookie and the value instead of the name, return null
  @Test
  public void valueInsteadOfName() {
        Cookie cookie1 = new Cookie("hello", "salam");

        Cookie cookieArray[] = new Cookie[]{cookie1};

        Cookie actual = cookieUtils.getCookieGivenName(cookieArray, "salam");
        Cookie expected = null;

         Assert.assertEquals(expected, actual);     
  }

//given no cookies and a name, return null
  @Test
  public void noCookiesWithName() {
        Cookie cookieArray[] = new Cookie[0];

        Cookie actual = cookieUtils.getCookieGivenName(cookieArray, "salam");
        Cookie expected = null;

         Assert.assertEquals(expected, actual);     
  }

//given a list of cookies, but no name, returns null
@Test
  public void cookiesWithoutName(){
        Cookie cookie1 = new Cookie("hello", "got it");
        Cookie cookie2 = new Cookie("hi", "not this one");
        Cookie cookie3 = new Cookie("heyyy", "not this one either");

        Cookie cookieArray[] = new Cookie[]{cookie1,cookie2,cookie3};

        Cookie actual = cookieUtils.getCookieGivenName(cookieArray, "");
        Cookie expected = null;

         Assert.assertEquals(expected, actual);  
  }
  
}