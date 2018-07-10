package pack_rest;
//import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.json.JSONObject;


public class TestWebServiceRestAssured_Json_Junit {

	@Rule
	public ErrorCollector ec = new ErrorCollector();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		/*String baseUrl = "http://ergast.com/api/f1/";
		RestAssured.baseURI = baseUrl;*/		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {	
//		RestAssured.baseURI = "";
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	/*Validate header - approach 1*/
	@Test
	public void test_StatusCode_ContentType_Length() {
		System.out.println("inside");
		/*String baseUrl = "http://ergast.com/api/f1/";
		RestAssured.baseURI = baseUrl;*/
		String url = "http://ergast.com/api/f1/2017/circuits.json";
		given().
		when().
		get(url).
		then().
		assertThat().
		statusCode(200).
		and().
		contentType(ContentType.JSON).
		and().
		header("Content-Length",Matchers.equalTo("4551"));
	}
	

	
	

}
class CustomJsonResponse{
	//It is mandatory to have variable name similar to Json response else value will not be fetched
	private String SuccessCode;
	private String Message;
	public String getSuccessCode() {
		return SuccessCode;
	}
	public void setSuccessCode(String successCode) {
		this.SuccessCode = successCode;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
} 


//Below is a class to test serialization
class User{
	//It is mandatory to have variable name similar to Json request, else value will not be sent in request
	private String FirstName;
	private String LastName;
	private String UserName;
	private String Password;
	private String Email;	
	public User(String firstName, String lastName, String userName, String password, String email) {
		super();
		FirstName = firstName;
		LastName = lastName;
		UserName = userName;
		Password = password;
		Email = email;
	}
}
