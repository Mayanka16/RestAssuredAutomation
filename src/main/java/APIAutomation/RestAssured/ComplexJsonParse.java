package APIAutomation.RestAssured;

import APIAutomation.file.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String args[]) {
		JsonPath js = new JsonPath(Payload.coursePrice());

		/*
		 * { "dashboard": { "purchaseAmount": 1162, "website": "rahulshettyacademy.com"
		 * }, "courses": [ { "title": "Selenium Python", "price": 50, "copies": 6 }, {
		 * "title": "Cypress", "price": 40, "copies": 4 }, { "title": "RPA", "price":
		 * 45, "copies": 10 }, { "title": "Appium", "price": 36, "copies": 7 }] }
		 */

		// print number of courses print by API
		int count = js.getInt("courses.size()");
		System.out.println("Number of courses print by API : " + count);

		// Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Print Purchase Amount : " + totalAmount);

		// Print Title of the first course
		String firstCourse = js.getString("courses[0].title");
		System.out.println("Title of the first course : " + firstCourse);

		// Print All course titles and their respective Prices
		for (int i = 0; i < count; i++) {
			String courseTitles = js.getString("courses[" + i + "].title");
			// System.out.println(js.getString("courses[" + i + "].title").toString()); //to
			// convert to string instead of storing into variable
			int coursePrices = js.getInt("courses[" + i + "].price");
			System.out.println("Course titles are : " + courseTitles + " and price : " + coursePrices);
		}

		// Print no of copies sold by RPA Course

		// Method 1
//			int copies = js.getInt("courses[2].copies");
//			System.out.println("Number of copies sold by RPA course : " + copies);

		// Method 2
		for (int i = 0; i < count; i++) {
			String courseTitles = js.get("courses[" + i + "].title");

			if (courseTitles.equalsIgnoreCase("RPA")) {
				int copies = js.get("courses[" + i + "].copies");
				System.out.println("Number of copies sold by RPA course : " + copies);
				break;

			}

		}
		
	}

}