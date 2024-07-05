package APIAutomation.RestAssured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import APIAutomation.file.Payload;

public class Basics {

	public static void main(String[] args) {
		// given() = all input details
		// when() = Submit the API - resource, http method
		// Then = validate the response
		
		
	
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//POST 
		System.out.println("-----------------POST REQUEST STARTED---------------");
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.addPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();


		System.out.println("The response is : " + response);

		JsonPath js = new JsonPath(response); // to read response

		String status = js.getString("status");
		System.out.println("Status from response is : " + status);

		String placeID = js.getString("place_id");
		System.out.println("Place id from response is : " + placeID);
		
		
		
		System.out.println("---------------POST REQUEST ENDED---------------");

		// Update address
		System.out.println("---------------PUT REQUEST STARTED---------------");
		
		String newAddress = "PSOTS, Bangalore";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\n" + "\"place_id\":\"" + placeID + "\",\n" + "\"address\":\""+newAddress+"\",\n"
						+ "\"key\":\"qaclick123\"\n" + "}\n" + " ")
				.when().put("/maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
		
		System.out.println("---------------PUT REQUEST ENDED---------------");

		// Get Response
		
		System.out.println("---------------GET REQUEST STARTED---------------");
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID).when()
				.get("/maps/api/place/get/json").then().assertThat().log().all().statusCode(200)
				.body("address", equalTo(newAddress));
		System.out.println("---------------GET REQUEST ENDED---------------");
	}

}
