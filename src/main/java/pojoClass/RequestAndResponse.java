package pojoClass;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

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
				.expect().defaultParser(Parser.JSON).when().get("/oauthapi/getCourseDetails").then().log().all()
				.assertThat().statusCode(401).extract().response().as(PojoClassGetCourseDeserialization.class);

		System.out.println("URL is : " + putResponse.getUrl());
		System.out.println("Instructor name is : " + putResponse.getInstructor());
		System.out.println("Services are : " + putResponse.getServices());
		System.out.println("Expertise into : " + putResponse.getExpertise());
		System.out.println("Courses are : " + putResponse.getCourses()); // redirected to other class
		System.out.println("LinkedIn link : " + putResponse.getLinkedIn());

		// calling API class
		System.out
				.println("API WebServices courseTitle : " + putResponse.getCourses().getApi().get(1).getCourseTitle());

		// To get the price for course title without index value
		String course = "SoapUI Webservices testing";
		String price = null;
		List<pojoCLassForCourseAPI> apiCourses = putResponse.getCourses().getApi();
		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase(course)) {
			 price = apiCourses.get(i).getPrice();
			}

		}
		System.out.println("Price for the course -> "+ course + " is -> "+ price +"/-");
		
		
		//Get the course name of Web Automation
		String courseTitle[] = {"Selenium Webdriver Java", "Cypress","Protractor"};
		ArrayList<String> a = new ArrayList<String>();
		List<pojoCLassForCourseWebAutomation> w = putResponse.getCourses().getWebAutomation();
		for (int i = 0; i<w.size();i++) {
			a.add(w.get(i).getCourseTitle());
		}	
			List<String> expectedArrayList = Arrays.asList(courseTitle);
			Assert.assertTrue(a.equals(expectedArrayList));
			System.out.println("WebAutomation course titles are : "+ a + " which matches with the expected " + expectedArrayList);
		}
		
	

	}
	
	
