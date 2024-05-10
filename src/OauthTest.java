import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import POJO.API;
import POJO.getCourse;
import POJO.webautomation;

public class OauthTest {
@SuppressWarnings("unlikely-arg-type")
public static void main (String[] args) {
	
	RestAssured.baseURI = "https://rahulshettyacademy.com";
	String[] expectedlist = {"Selenium Webdriver Java","Cypress","Protractor"};
	
String response = given().relaxedHTTPSValidation().log().all().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
.formParam("scope", "trust").when().post("/oauthapi/oauth2/resourceOwner/token").then().extract().response().asString();
System.out.println(response);
	
	JsonPath js = new JsonPath(response);
	String access_token = js.get("access_token");
	
	getCourse gc = given().relaxedHTTPSValidation().queryParam("access_token", access_token).when().log().all().get("/oauthapi/getCourseDetails").as(getCourse.class);
	System.out.println(gc.getLinkedIn());	
	System.out.println(gc.getExpertise());
	
	
	System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
	
	List<API> apiCourses = gc.getCourses().getApi();
	for(int i=0; i<apiCourses.size();i++) {
		
		if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
		{
			System.out.println(apiCourses.get(i).getPrice());
		}
		
	}
	ArrayList<String> a = new ArrayList<String>();

	List<webautomation> webautCourses = gc.getCourses().getWebAutomation();
	for(int j=0; j<webautCourses.size();j++) {
		a.add(webautCourses.get(j).getCourseTitle());
	}
	List<String> b = Arrays.asList(expectedlist);
	Assert.assertTrue(a.equals(b));
	System.out.println("practrice 2");
	System.out.println("practrice 2");
	System.out.println("practrice 2");
}

}
