package basicAPIValidations;

import org.testng.Assert;
import org.testng.annotations.Test;

import basicAPIValidationsFile.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {
		JsonPath js = new JsonPath(Payload.coursePrice());

		// Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		int count = js.getInt("courses.size()");
		for (int i = 0; i < count; i++) {
			int price = js.get("courses[" + i + "].price");
			int copies = js.get("courses[" + i + "].copies");
			int amount = price * copies;
			System.out.println("Amount is : " + amount);
			sum = sum + amount;

		}
		System.out.println("Sum is : " + sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(purchaseAmount, sum);
			
		}
		
	}
