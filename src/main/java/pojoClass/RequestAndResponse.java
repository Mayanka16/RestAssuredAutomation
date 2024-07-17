package pojoClass;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class RequestAndResponse {
	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String oAuthPOSTResponse = given().log().all()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").post("/oauthapi/oauth2/resourceOwner/token").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(oAuthPOSTResponse);
		String accessToken = js.getString("access_token");
		System.out.println("Access token is : " + accessToken);

		PojoClassGetCourseDeserialization putResponse = given().log().all().queryParam("access_token", accessToken)
				.get("/oauthapi/getCourseDetails").then().log().all().assertThat().statusCode(401).extract().response()
				.as(PojoClassGetCourseDeserialization.class);

		System.out.println(putResponse);

		System.out.println("URL is : " + putResponse.getUrl());
		System.out.println("Instructor name is : " + putResponse.getInstructor());

	}
}
