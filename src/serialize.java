import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.location;
import POJO.serializeJson;
import io.restassured.RestAssured;
public class serialize {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		serializeJson sj = new serializeJson();
		sj.setName("sun");
		sj.setLanguage("eng");
		sj.setPhone_number("(+91) 983 893 3937");
		sj.setAddress("29, side layout, cohen 09");
		List<String> types = new ArrayList<String>();
		types.add("show park");
		types.add("shop");
		sj.setTypes(types);
		sj.setWebsite("http://google.com");
		sj.setAccuracy(50);
	
		location l = new location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		sj.setLocation(l);

		
		String resp = given().relaxedHTTPSValidation().queryParam("key", "qaclick123").body(sj).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(resp);
		
	}

}
