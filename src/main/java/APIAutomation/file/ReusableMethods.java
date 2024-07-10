package APIAutomation.file;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath rawToJason(String response) {
		JsonPath js1 = new JsonPath(response);
		return js1;
		
		
	}

}
