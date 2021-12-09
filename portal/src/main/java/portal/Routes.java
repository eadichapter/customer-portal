package portal;

import static spark.Spark.post;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import spark.Request;

public class Routes {

	public Routes() {
		post("/new-account", (request, response) -> { 
			try {
				response.type("application/json");
				String email = extractEmail(request);
				response.body(responseMessage(email));
				response.status(201);
				return response.body();
			} catch (Exception e) {
				e.printStackTrace();
				response.status(500);
				return e.getMessage();
			}
		});
	}

	private String responseMessage(String email) {
		return String.format("{\"response\": \"Account created for %s\"}", email);
	}

	private String extractEmail(Request request) {
		String customer = extractCustomerValue(request);
		return extractEmailVaue(customer);
	}

	private String extractCustomerValue(Request request) {
		JsonObject jsonObject = new JsonParser().parse(request.body()).getAsJsonObject();
		return jsonObject.get("customer").toString();
	}
	
	private String extractEmailVaue(String customer) {
		JsonObject asJsonObject = new JsonParser().parse(customer).getAsJsonObject();
		return asJsonObject.get("email").getAsString();
	}

}
