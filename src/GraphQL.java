import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import io.restassured.path.json.JsonPath;
public class GraphQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			String response = given().log().all().header("Content-Type","application/json")
			.body("{\"query\":\"# mutation($characterName:String!,$characterType:String!)\\n# {\\n#   createCharacter(character:{name: $characterName,type:$characterType,status:\\\"active\\\",species:\\\"anim\\\",gender:\\\"female\\\",image:\\\"png\\\",originId:8249,locationId:8249}){\\n#     id\\n#   }\\n# }\\n\\nquery($characterId:Int!)\\n{\\n  character(characterId:$characterId){\\n    name\\n    type\\n    status\\n  }\\n}\",\"variables\":{\"characterId\":7645}}")
			.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
			System.out.println(response);
			JsonPath js = new JsonPath(response);
			String charname= js.getString("data.character.name");
			assertEquals(charname,"deeps");
	}

}
