package com.dh.userwallet;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UserWalletApplicationTests {

	private String body = "{" +
			"   \"userName\":\"Usuario01\",\n" +
			"   \"password\":\"Holis1234!\"\n" +
			"}";
	private String token = "";

	@BeforeTestClass
	public void setup() {
		RestAssured.baseURI = "http://localhost:8080";
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
	public void postAccountByIdTest() {
		RestAssured.baseURI = "http://localhost:8080";

		token =	given().
				//header("Content-type", "application/json").
						contentType(ContentType.JSON).
				body(body).
				when().
				post("/users/login").
				jsonPath().
				get("accessToken");
		Map<String, String> headers = new HashMap<String, String>() {
			{
				put("Accept", "application/json");
				put("Authorization", "Bearer " + token);
			}
		};
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject request = new JSONObject();
		String jsonBody = "{" +
				"   \"amount\":\"10000\"\n" +
				"}";
		given().

				headers(headers).
				contentType(ContentType.JSON).
				log().all().
				body(jsonBody).
				when().
				post("/accounts/10").
				then().
				log().all().
				statusCode(200);
	}

	@Test
	public void postCardByIdTest() {
//		RestAssured.baseURI = "http://localhost:8080";

		token =	given().
				//header("Content-type", "application/json").
						contentType(ContentType.JSON).
				body(body).
				when().
				post("http://localhost:8080/users/login").
				jsonPath().
				get("accessToken");
		Map<String, String> headers = new HashMap<String, String>() {
			{
				put("Accept", "application/json");
				put("Authorization", "Bearer " + token);
			}
		};
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject request = new JSONObject();
		String jsonBody = "{" +
				"   \"cardNumber\":\"314241000\",\n" +
				"   \"expirationDate\":\"04/28\",\n" +
				"   \"fullName\":\"PabloOrozco\",\n" +
				"   \"amount\":\"1000\",\n" +
				"   \"securityCode\":\"123\"\n" +
				"}";
		given().
				headers(headers).
				contentType(ContentType.JSON).
				log().all().
				body(jsonBody).
				when().
				post("http://localhost:8082/accounts/9/cards").
				then().
				log().all().
				statusCode(200);
	}

	@Test
	void getAccountTest(){
		RestAssured.baseURI = "http://localhost:8080";

		token =	given().
				//header("Content-type", "application/json").
						contentType(ContentType.JSON).
				body(body).
				when().
				post("/users/login").
				jsonPath().
				get("accessToken");
		Map<String, String> headers = new HashMap<String, String>() {
			{
				put("Accept", "application/json");
				put("Authorization", "Bearer " + token);
			}
		};

		given().
			headers(headers).
				when().
				log().all().
				get("/accounts/12").
				then().
				statusCode(200).
				body("userId", equalTo(10));
	}

//	@Test
//	void getAccountsTest(){
//		RestAssured.baseURI = "http://localhost:8082";
//		given().
//				when().
//				get("accounts/1").
//				then().
//				statusCode(200).
//				body("[0].userId", equalTo("4"));
//	}



	@Test
	void getTransactionsTest(){
		RestAssured.baseURI = "http://localhost:8080";
		token =	given().
				//header("Content-type", "application/json").
						contentType(ContentType.JSON).
				body(body).
				when().
				post("/users/login").
				jsonPath().
				get("accessToken");
		Map<String, String> headers = new HashMap<String, String>() {
			{
				put("Accept", "application/json");
				put("Authorization", "Bearer " + token);
			}
		};
		given().
				contentType(ContentType.JSON).
				header("Authorization", "Bearer " + token).
				when().
				get("accounts/2/transactions").
				then().
				statusCode(200).
				log().all().
				body("[0].amount", equalTo(12));
	}



	@Test
	public void postTransferenceTest() {
//		RestAssured.baseURI = "http://localhost:8080";

		token =	given().
				//header("Content-type", "application/json").
						contentType(ContentType.JSON).
				body(body).
				when().
				post("http://localhost:8080/users/login").
				jsonPath().
				get("accessToken");
		Map<String, String> headers = new HashMap<String, String>() {
			{
				put("Accept", "application/json");
				put("Authorization", "Bearer " + token);
			}
		};
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject request = new JSONObject();
		String jsonBody = "{" +
				"   \"amount\":\"100\",\n" +
				"   \"cardNumber\":\"314241000\"\n" +
				"}";
		given().
				headers(headers).
				contentType(ContentType.JSON).
				log().all().
				body(jsonBody).
				when().
				post("http://localhost:8082/accounts/9/transferences").
				then().
				log().all().
				statusCode(200);
	}

	@Test
	public void postTransactionTest() {
//		RestAssured.baseURI = "http://localhost:8080";

		token =	given().
				//header("Content-type", "application/json").
						contentType(ContentType.JSON).
				body(body).
				when().
				post("http://localhost:8080/users/login").
				jsonPath().
				get("accessToken");
		Map<String, String> headers = new HashMap<String, String>() {
			{
				put("Accept", "application/json");
				put("Authorization", "Bearer " + token);
			}
		};
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject request = new JSONObject();
		String jsonBody = "{" +
				"   \"amount\":\"100\",\n" +
				"   \"date\":\"12/12\",\n" +
				"   \"description\":\"test transaction\",\n" +
				"   \"destinatedCvu\":\"0000030247416488348021\",\n" +
				"   \"originCvu\":\"0000030207540334220508\",\n" +
				"   \"type\":\"egreso\"\n" +
				"}";
		given().
				headers(headers).
				contentType(ContentType.JSON).
				log().all().
				body(jsonBody).
				when().
				post("http://localhost:8082/accounts/4/transactions").
				then().
				log().all().
				statusCode(200);
	}

	@Test
	void getCardByIdTest(){
		RestAssured.baseURI = "http://localhost:8080";

		token =	given().
				//header("Content-type", "application/json").
						contentType(ContentType.JSON).
				body(body).
				when().
				post("/users/login").
				jsonPath().
				get("accessToken");
		Map<String, String> headers = new HashMap<String, String>() {
			{
				put("Accept", "application/json");
				put("Authorization", "Bearer " + token);
			}
		};
		given().
				headers(headers).
				when().
				get("accounts/9/cards").
				then().
				statusCode(200).
				body("[0].cardNumber", equalTo("314241000"));
	}

	@Test
	void deleteCardByIdTest() {
		RestAssured.baseURI = "http://localhost:8080";
		token =	given().
				//header("Content-type", "application/json").
						contentType(ContentType.JSON).
				body(body).
				when().
				post("/users/login").
				jsonPath().
				get("accessToken");
		Map<String, String> headers = new HashMap<String, String>() {
			{
				put("Accept", "application/json");
				put("Authorization", "Bearer " + token);
			}
		};
		given().
				headers(headers).
				when().
				delete("accounts/9/cards/41").
				then().
				statusCode(200);
	}



}


