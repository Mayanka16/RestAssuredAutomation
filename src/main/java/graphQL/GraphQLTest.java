package graphQL;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class GraphQLTest {
	public static void main(String[] args) {
		String response = given().log().all().header("Content-Type", "application/json").body("{\"query\":\"mutation($episodeName : String!, $characterName : String!)\\n{\\n  createEpisode(episode:{name:$episodeName ,air_date:\\\"10-dec\\\",episode:\\\"100\\\"})\\n  {\\n    id\\n  }\\n  createCharacter(character:{name:$characterName,type:\\\"Human\\\",status:\\\"Alive\\\",\\n  species:\\\"Human\\\",gender:\\\"Female\\\",image:\\\"10\\\",originId:90,locationId:80})\\n  {\\n    id\\n  }\\n deleteCharacters(characterIds:[7865])\\n  {\\n    charactersDeleted\\n  }\\n}\",\"variables\":{\"episodeName\":\"MJHT\",\"characterName\":\"Manka\"}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
		System.out.println("Response is : "+ response);
		
		JsonPath js = new JsonPath(response);
		String createEpi = js.getString("data.createEpisode.id");
		System.out.println("Id of createEpisode is : "+ createEpi);
		
		
	}

}
