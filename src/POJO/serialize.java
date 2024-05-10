package POJO;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.location;
import POJO.serializeJson;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
public class serialize {

	public static void main(String[] args) {
		//RestAssured.baseURI = "https://rahulshettyacademy.com";
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

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		
		RequestSpecification req1 = given().spec(req).relaxedHTTPSValidation().body(sj);
		ResponseSpecification respon = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		Response resp = req1.when().post("/maps/api/place/add/json").then().spec(respon).extract().response();
		String response = resp.asString();
		System.out.println(response);
		
	}

}
