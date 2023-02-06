package com.dh.userprofile;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UserProfileApplicationTests {

	private String body = "{" +
			"   \"userName\":\"Usuario01\",\n" +
			"   \"password\":\"Holis1234!\"\n" +
			"}";
	private String token = "";

	@BeforeTestClass
	public void setup() {
		RestAssured.baseURI = "http://localhost:8081";
		token =	given().
				//header("Content-type", "application/json").
						contentType(ContentType.JSON).
				body(body).
				when().
				post("/users/login").
				jsonPath().
				get("accessToken");
	}

	@Test
	public void postUserTest() {
		RestAssured.baseURI = "http://localhost:8081";
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject request = new JSONObject();
		String jsonBody = "{" +
				"   \"name\":\"Sprint2\",\n" +
				"   \"lastName\":\"Review\",\n" +
				"   \"dni\":\"314241000\",\n" +
				"   \"email\":\"5f8nbztdk3@temporaremail.com\",\n" +
				"   \"telephone\":\"8888888888\",\n" +
				"   \"password\":\"Sprint2!\"\n" +
		"}";
				given().
				header("Content-type", "application/json").
				contentType(ContentType.JSON).
				log().all().
				body(jsonBody).
				when().
				post("/users").
				then().
				log().all().
				statusCode(200);
	}

	@Test
	public void verifyUserTest() {
		RestAssured.baseURI = "http://localhost:8081";
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject request = new JSONObject();
		String jsonBody = "{" +
				"   \"userCode\":\"867360\",\n" +
				"   \"userName\":\"Sprint2\"" +
				"}";
		System.out.println(map.toString());

		given().
				header("Content-type", "application/json").
				contentType(ContentType.JSON).
				log().all().
				body(jsonBody).
				when().
				post("/users/verify").
				then().
				log().all().
				statusCode(200);
	}

	@Test
	void getUserTest(){
		RestAssured.baseURI = "http://localhost:8081";
				given().
//				header("Authentication", "Bearer " + token).
				when().
				get("/users/10").
				then().
				statusCode(200).
				body("name", equalTo("Sprint2"));
	}

	@Test
	void getUsersTest(){
		RestAssured.baseURI = "http://localhost:8081";
				given().
				when().
				get("/users").
				then().
				statusCode(200).
				body("[0].name", equalTo("Usuario007")).
				and().
				body("[9].name", equalTo("Sprint2"));
	}

}
