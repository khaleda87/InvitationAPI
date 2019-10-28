package com.Invitation.api;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TC_002 {
	@Test
	public void updateInvite(){
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
		
		String requestBody1 = "<applicationData>\r\n" + "<appID>1234</appID>\r\n" + "<appName>Invitation</appName>r\\n\\" + 
		                     "<appRole>provideID</appRole>\r\n" + "<appLocation>XXX</appLocation>\r\n" +"</applicationData>\r\n"
				             + "<userName>abcde</userName>\r\n" + "<userEmail>abc@gmail.com</userEmail>\r\n" + "</userData>\r\n" +
		                     "<inviteOption>\r\n" + "<inviteExpiry>10-26-2019</inviteExpiry>\r\n" + "</invitationRequest>";
		
		Response response1 = null;
		response1 = RestAssured.given()
				    .auth()
				    .oauth2(token)
				    .contentType(ContentType.XML)
				    .contentType(ContentType.XML)
				    .body(requestBody1)
				    .when()
				    .put("/update/{ID}");
		
		int statusCode = response1.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		
				
	}
	
}
