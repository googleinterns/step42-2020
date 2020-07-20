// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// package com.google.sps;

// import org.junit.Assert;
// import org.junit.Before;
// import org.junit.After;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.junit.runners.JUnit4;
// import java.io.*;
// import javax.servlet.*;
// import javax.servlet.http.*;
// import com.google.sps.utils.CookieUtils; 

// @RunWith(JUnit4.class)
// public final class CookieUtilsTest {

//   //given a list of cookies, it should find the one with the mentioned name.
//   @Test
//   public void findCookieByName() {
//         Cookie cookie1 = new Cookie("name1", "value1");
//         Cookie cookie2 = new Cookie("name2", "value2");
//         Cookie cookie3 = new Cookie("name3", "value3");

//         Cookie cookieArray[] = new Cookie[]{cookie1,cookie2,cookie3};

//         Cookie actual = CookieUtils.getCookieFromName(cookieArray, "name1");

//         Assert.assertEquals(cookie1, actual);     
//   }

// //Test when no cookie with given name exists
//   @Test
//   public void cookiesWithWrongName() {
//         Cookie cookie1 = new Cookie("name1", "value1");
//         Cookie cookie2 = new Cookie("name2", "value2");
//         Cookie cookie3 = new Cookie("name3", "value3");

//         Cookie cookieArray[] = new Cookie[]{cookie1,cookie2,cookie3};

//         Cookie actual = CookieUtils.getCookieFromName(cookieArray, "noname");
//         Cookie expected = null;

//         Assert.assertEquals(expected, actual);     
//   }

// //Test with a single cookie and value pair
//   @Test
//   public void oneCookieAndName() {
//         Cookie cookie1 = new Cookie("name1", "value1");

//         Cookie cookieArray[] = new Cookie[]{cookie1};

//         Cookie actual = CookieUtils.getCookieFromName(cookieArray, "name1");
//         Cookie expected = cookie1;

//         Assert.assertEquals(cookie1, actual);     
//   }

// //Test with a single cookie, where the value is searched for instead of the name
//   @Test
//   public void valueInsteadOfName() {
//         Cookie cookie1 = new Cookie("name1", "value1");

//         Cookie cookieArray[] = new Cookie[]{cookie1};

//         Cookie actual = CookieUtils.getCookieFromName(cookieArray, "value1");
//         Cookie expected = null;

//          Assert.assertEquals(expected, actual);     
//   }

// //Test where no cookies are given in the array
//   @Test
//   public void noCookiesWithName() {
//         Cookie cookieArray[] = new Cookie[0];

//         Cookie actual = CookieUtils.getCookieFromName(cookieArray, "name1");
//         Cookie expected = null;

//         Assert.assertEquals(expected, actual);     
//   }

// //Test where a list of cookies are given and an empty string
// @Test
//   public void cookiesWithoutName(){
//         Cookie cookie1 = new Cookie("name1", "value1");
//         Cookie cookie2 = new Cookie("name2", "value2");
//         Cookie cookie3 = new Cookie("name3", "value3");

//         Cookie cookieArray[] = new Cookie[]{cookie1, cookie2, cookie3};

//         Cookie actual = CookieUtils.getCookieFromName(cookieArray, "");
//         Cookie expected = null;

//         Assert.assertEquals(expected, actual);  
//   }
  
// }
