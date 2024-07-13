package APIAutomation.file;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "http://216.10.245.166";

		// POST request
		String response = given().log().all().header("Content-Type", "application/json")
				.body(Payload.addBook(isbn, aisle)).when().post("/Library/Addbook.php").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath js = ReusableMethods.rawToJason(response);
		String id = js.get("ID");
		System.out.println("Id is : " + id);

		String message = js.getString("Msg");
		Assert.assertEquals("successfully added", message);
	

	// DELETE

	
		String deleteresponse = given().log().all().header("Content-Type", "application/json")
				.body(Payload.deleteBook(id)).when().delete("/Library/DeleteBook.php").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath js1 = ReusableMethods.rawToJason(deleteresponse);

		String message1 = js1.getString("msg");
		Assert.assertEquals("book is successfully deleted", message1);

	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] { { "abc6144", "6" }, { "abc7144", "7" }, { "abc8144", "8" } };
	}

}
