package basicAPIValidations;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import basicAPIValidationsFile.Payload;
import basicAPIValidationsFile.ReusableMethods;

public class Basics {

	public static void main(String[] args) throws IOException {
		// given() = all input details
		// when() = Submit the API - resource, http method
		// Then = validate the response

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// POST
		System.out.println("-----------------POST REQUEST STARTED---------------");
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				//.body(Payload.addPlace())    OR
				.body(new String(Files.readAllBytes(Paths.get("//Users//mayanka//AddPlace.json")))).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
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
				.body("{\n" + "\"place_id\":\"" + placeID + "\",\n" + "\"address\":\"" + newAddress + "\",\n"
						+ "\"key\":\"qaclick123\"\n" + "}\n" + " ")
				.when().put("/maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		System.out.println("---------------PUT REQUEST ENDED---------------");

		// Get Response

		System.out.println("---------------GET REQUEST STARTED---------------");
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
				.when().get("/maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.response().asString();

		System.out.println("The response is : " + getPlaceResponse);
		JsonPath js1 = ReusableMethods.rawToJason(getPlaceResponse);
		String actualAddress = js1.getString("address");
		Assert.assertEquals(actualAddress, newAddress);

		System.out.println("---------------GET REQUEST ENDED---------------");
	}

}
