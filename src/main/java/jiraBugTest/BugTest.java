package jiraBugTest;

	import io.restassured.RestAssured;
	import io.restassured.path.json.JsonPath;

	import static io.restassured.RestAssured.*;

	import java.io.File;

	public class BugTest {

		public static void main(String[] args) {
			RestAssured.baseURI = "https://mayankasao.atlassian.net/";
			String createIssueResponse = given().header("Content-Type", "application/json")
					.header("Authorization","Basic bWF5YW5rYXNhb0BnbWFpbC5jb206QVRBVFQzeEZmR0YwcFRhQ3o0VUR2LUNzSHhoakJJZDdIOGRrTE5CeG5MOFlsSXRiRm5lbE1Va0hrRUZ0SjZ4LU9uZ2NQUFo1TTBOLU5GaGNSR1hMZ1ZTRnVfcEdXTENCWkhHbkhTU0thc1RIRUlJb19hMG5UdlI4U1hyckpWUGpmZ0tudjZKRXk5cFJ6d1VoeWd4ejNJbGktbWhPWEJ1MmpvTVRxbTFWYUhSdF9HMnBJOC1SdjhFPTRCN0I0Mjcz")
					
					.body("{\n"
							+ "    \"fields\": {\n"
							+ "       \"project\":\n"
							+ "       {\n"
							+ "          \"key\": \"SCRUM\"\n"
							+ "       },\n"
							+ "       \"summary\": \"WebSite items are not working + Automation\",\n"
							+ "      \"issuetype\": {\n"
							+ "          \"name\": \"Bug\"\n"
							+ "       }\n"
							+ "   }\n"
							+ "}")
					.log().all().post("rest/api/3/issue").then().log().all().assertThat().statusCode(201).contentType("application/json")
					.extract().response().asString();

			JsonPath js = new JsonPath(createIssueResponse);
			String issueId = js.getString("id");
			System.out.println("Id of the Post call is : "+ issueId);
			
			//Add attachment
			

			
			given().header("X-Atlassian-Token", "no-check").pathParam("key", issueId)
			.header("Authorization","Basic bWF5YW5rYXNhb0BnbWFpbC5jb206QVRBVFQzeEZmR0YwcFRhQ3o0VUR2LUNzSHhoakJJZDdIOGRrTE5CeG5MOFlsSXRiRm5lbE1Va0hrRUZ0SjZ4LU9uZ2NQUFo1TTBOLU5GaGNSR1hMZ1ZTRnVfcEdXTENCWkhHbkhTU0thc1RIRUlJb19hMG5UdlI4U1hyckpWUGpmZ0tudjZKRXk5cFJ6d1VoeWd4ejNJbGktbWhPWEJ1MmpvTVRxbTFWYUhSdF9HMnBJOC1SdjhFPTRCN0I0Mjcz")
			.multiPart("file", new File ("/Users/mayanka/Documents/Data/MayankaSao.jpg")).log().all().post("rest/api/3/issue/{key}/attachments")
			.then().log().all().assertThat().statusCode(200);
			
			System.out.println("Image attached successfully");
		}

		}


