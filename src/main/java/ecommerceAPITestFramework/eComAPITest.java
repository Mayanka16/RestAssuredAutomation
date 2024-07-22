package ecommerceAPITestFramework;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import sun.security.util.Password;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class eComAPITest {

	public static void main(String[] args) {

		/*
		 * ADD REQUEST - LOGIN TO ECOM (https://rahulshettyacademy.com/client)
		 * 
		 * Author : Mayanka Sao Date : 22 July 2024 HTTP Request : POST Explanation :
		 * This POST API will create a new login request with provided username and
		 * Password
		 */

		RequestSpecification loginRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		eComLogin loginReq = new eComLogin();
		loginReq.setUserEmail("maya@gmail.com");
		loginReq.setUserPassword("Quality44%");

		RequestSpecification reqLogin = given().log().all().spec(loginRequest).body(loginReq);

		APIResponsePojo response = reqLogin.when().log().all().post("/api/ecom/auth/login").then().extract().response()
				.as(APIResponsePojo.class);

		String userID = response.getUserId();
		System.out.println("UserId is : " + response.getUserId());
		String autorizationToken = response.getToken();
		System.out.println("Token is : " + response.getToken());
		System.out.println("Message is : " + response.getMessage());

		/*
		 * ADD NEW PRODUCT TO HOME PAGE- ADDING A PRODUCT
		 * 
		 * Author : Mayanka Sao Date : 22 July 2024 HTTP Request : POST Explanation :
		 * This POST API will create a new item in homepage
		 */

		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", autorizationToken).build();

		RequestSpecification reaAddProduct = given().log().all().spec(addProductBaseReq)
				.formParam("productName", "Skirt").formParam("productAddedBy", userID)
				.formParam("productCategory", "fashion").formParam("productSubCategory", "Bottom")
				.formParam("productPrice", 1).formParam("productDescription", "people").formParam("productFor", "women")
				.multiPart("productImage", new File("//Users//mayanka//Downloads//08267530250-p.jpg"));

		String addProdcutResponse = reaAddProduct.when().post("/api/ecom/product/add-product").andReturn().then().log()
				.all().statusCode(201).extract().response().asString();

		JsonPath js = new JsonPath(addProdcutResponse);
		String productID = js.get("productId");
		System.out.println("Product ID is : " + productID);

		/*
		 * CREATE PRODUCT - PLACING ORDER 
		 * 
		 * Author : Mayanka Sao Date : 22 July 2024
		 * HTTP Request : POST Explanation : This POST API will place a order which was
		 * created earlier
		 */
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", autorizationToken).setContentType(ContentType.JSON).build();

		CreateProduct orderDetails = new CreateProduct();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productID);

		List<CreateProduct> ls = new ArrayList<CreateProduct>();
		ls.add(orderDetails);
		CreateProductOrder order = new CreateProductOrder();
		order.setOrders(ls);

		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(order);
		String responseBasAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response().asString();

		System.out.println("Response of Add Order is : " + responseBasAddOrder);

		/*
		 * GET PRODUCT - VIEW THE PRODUCT 
		 * 
		 * Author : Mayanka Sao Date : 22 July 2024 HTTP
		 * Request : PUT Explanation : This PUT request will show the data using orderID
		 */
		JsonPath createOrdjs = new JsonPath(responseBasAddOrder);
		List<Object> orderId = createOrdjs.getList("orders"); // need to use list to get the "orderId"
		System.out.println("Order ID is : " + orderId);
		RequestSpecification viewOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", autorizationToken).setContentType(ContentType.JSON).build();

		RequestSpecification viewOrdReq = given().log().all().spec(viewOrderBaseReq).queryParam("id", orderId);
		String viewOrderResponse = viewOrdReq.when().get("/api/ecom/order/get-orders-details").then().log().all()
				.extract().response().asString();

		System.out.println("Get Request Response is : " + viewOrderResponse);

		/*
		 * DELETE PRODUCT- DELETE THE PRODUCT 
		 * 
		 * Author : Mayanka Sao Date : 22 July 2024
		 * HTTP Request : DELETE Explanation : This DELETE request will delete the data
		 * using ProductID
		 */

		RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", autorizationToken).setContentType(ContentType.JSON).build();

		RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq).pathParam("productID",
				productID);
		String deleteProdResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productID}").then()
				.log().all().extract().response().asString();

		JsonPath js1 = new JsonPath(deleteProdResponse);
		String deleteMessage = js1.get("message");
		System.out.println("Deleted Message is : " + deleteMessage);

	}

}
