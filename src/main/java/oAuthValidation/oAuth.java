package oAuthValidation;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class oAuth {
	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String oAuthPOSTResponse = given().log().all()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").post("/oauthapi/oauth2/resourceOwner/token").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(oAuthPOSTResponse);
		String accessToken = js.getString("access_token");
		System.out.println("Access token is : " + accessToken);
		
		
		String oAuthPUTResponse = given().log().all().queryParam("access_token", accessToken).get("/oauthapi/getCourseDetails")
	.then().log().all().assertThat().statusCode(401).extract().response().asString();
		
		JsonPath js1 = new JsonPath(oAuthPUTResponse);
		String getCourseDetails = js1.getString("courses");
		System.out.println("Courses details are : "+ getCourseDetails);
		//System.out.println(oAuthPUTResponse);
	
	}
}
