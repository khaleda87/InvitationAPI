package com.Invitation.api;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TC_003 {
	
	@Test
	public void fetchInvite(){
		RestAssured.baseURI = "http://authenticate.testapp.com:5030/Service";
		String id = "12345abcde";
		PreemptiveBasicAuthScheme aut = new PreemptiveBasicAuthScheme();
		aut.setUserName(id);
		String requestBody = "<AuthenticationRequestData>\r\n" + "<applicationId>123</applicationId>\r\n" + "<applicationPassword>Test123</applicationPassword>\r\n" +
		                     "<clientIP>127.0.0.1</clientIP>\r\n" + "</AuthenticationRequestData>";
		
		
		Response response = null;
		response =  RestAssured.given().
				   contentType(ContentType.XML)
				   .accept(ContentType.XML)
				   .body(requestBody)
				   .when()
				   .post("/httpservice");
		
		
	    String token = response.xmlPath().get("ServiceResponse.ResponseBody.token");
	    
		
		RestAssured.baseURI = "URI: https://testApplication.com/invitation";
		
		
		Response response1 = null;
		response1 = RestAssured.given()
				    .auth()
				    .oauth2(token)
				    .contentType(ContentType.XML)
				    .contentType(ContentType.XML)
				    .when()
				    .get("/fetch/{ID}");
		
		int statusCode = response1.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		String contentType = response1.header("Content-Type");
		Assert.assertEquals(contentType, "test/XML");
				
	}
	


}
