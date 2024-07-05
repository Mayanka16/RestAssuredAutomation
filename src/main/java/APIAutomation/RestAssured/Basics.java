package APIAutomation.RestAssured;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import APIAutomation.file.Payload;

public class Basics {

	public static void main(String[] args) {
		// given() = all input details
		// when() = Submit the API - resource, http method
		// Then = validate the response
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.addPlace()).when().post("maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)");

	}

}
