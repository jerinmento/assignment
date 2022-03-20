package com.assignment;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Assignment1 {
	Integer userIdSize;
	String endpoint;
	Integer userid;

	@BeforeClass
	public void setup() {
		baseURI = "https://jsonplaceholder.typicode.com";
	}

	@Test(priority = 1)
	public void toGetUserIdSize() {
		basePath = "/users";
		String endpoint = baseURI + basePath;

		userIdSize = given().when().header("Content-type", "application/json").get(endpoint).then().assertThat()
				.statusCode(200).extract().response().path("id.size()");
		System.out.println("The size of user id is " + userIdSize);
	}

	@Test(priority = 2)
	public void toGetRandomUserId() {
		basePath = "/users";
		endpoint = baseURI + basePath;

		Random random = new Random();
		userid = random.nextInt(userIdSize) + 1;

		Object userEmail = given().header("Content-type", "application/json").get(endpoint + "?id=" + userid).then()
				.extract().response().path("email");
		System.out.println("The email for user " + userid + " is " + userEmail);

	}

	@Test(priority = 3)
	public void toGetUsersPost() {
		basePath = "/posts";
		endpoint = baseURI + basePath;

		given().when().header("Content-type", "application/json").get(endpoint + "?userId=" + userid).then().assertThat()
				.statusCode(200).extract().response().then().assertThat()
				.body("id", everyItem(allOf(greaterThan(0), lessThanOrEqualTo(100))));
	}

	@Test(priority = 4)
	public void toCreateUserNewPost() {
		
		basePath = "/posts";
		endpoint = baseURI + basePath;
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("userId",userid);
		map.put("title", "foo");
		map.put("body", "bar");
		
		JSONObject obj = new JSONObject(map);
		
		given().when().header("Content-type", "application/json").body(obj).
			post(endpoint).then().assertThat().statusCode(201);
		
	}
}
