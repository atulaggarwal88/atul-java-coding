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
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.expectThrows;


import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.json.JSONObject;


public class TestWebServiceRestAssured_Json_Junit {

	@Rule
	public ErrorCollector ec = new ErrorCollector();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String baseUrl = "http://ergast.com/api/f1/";
		RestAssured.baseURI = baseUrl;		
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
	@Test
	public void test_SizeOfJson(){
		String url = "http://ergast.com/api/f1/2008/10/results.json";
		given().
		when().
		get(url).
		then().
		assertThat().
		body("MRData.RaceTable.Races[0].Results.position", Matchers.hasSize(20));
	}

	@Test	
	public void test_CalculateSumOfPointsFor1stPlace(){
		/*
		 * We will calculate total number of points for 
		Position = 1st place in 
		Season = 2015; Round = 1 and 19
		 */
		String baseUrl = "http://ergast.com/api/f1/";
		String pathUrl = "{year}/{round}/constructorStandings.json";

		String yearVar = "2015";
		String roundVar = "18";

		RestAssured.baseURI = baseUrl;

		//Path param is used below. Path URL is appended to Base URL
		//Steps to fetch points for round 18 in year 2015
		RequestSpecification httpRequest = RestAssured.given()
				.pathParam("year", yearVar).
				pathParam("round", roundVar);
		Response response = httpRequest.request(Method.GET, pathUrl);
		ResponseBody responseBody = response.getBody();
		//		System.out.println(responseBody.asString());//entire Json response is printed as String
		JsonPath jsonPath1 = responseBody.jsonPath();		
		int point1 = jsonPath1.getInt("MRData.StandingsTable.StandingsLists[0].ConstructorStandings[0].points");
		System.out.printf("Point in round %s is: " + point1 + "\n", roundVar);

		//Steps to fetch points for round 1 in year 2015
		yearVar = "2015";
		roundVar = "1";
		httpRequest = RestAssured.given()
				.pathParam("year", yearVar).
				pathParam("round", roundVar);
		response = httpRequest.request(Method.GET, pathUrl);
		responseBody = response.getBody();
		JsonPath jsonPath2 = responseBody.jsonPath();		
		int point2 = jsonPath2.getInt("MRData.StandingsTable.StandingsLists[0].ConstructorStandings[0].points");
		System.out.printf("Point in round %s is: " + point2 + "\n", roundVar);

		System.out.println("Total points: " + (point1 + point2));
		Assert.assertThat((point1 + point2), Matchers.equalTo(703));		
	}

	@Test
	public void test_StatusCode_Headers() {
		String baseUrl = "http://ergast.com/api/f1/";
		RestAssured.baseURI = baseUrl;		
		String pathUrl = "2017/circuits.json";

		RequestSpecification httpRequest = RestAssured.given();
		Response httpResponse = httpRequest.get(pathUrl);

		//Print all header
		Headers headers = httpResponse.getHeaders();
		for(Header header: headers){
			System.out.print("Name: " + header.getName() + ",\t");
			System.out.println("Value: " + header.getValue());
		}		
		//Print content type
		String contentType = httpResponse.getHeader("Content-Type");
		//		System.out.println("Content type is: " + contentType);
		Assert.assertThat(contentType, Matchers.equalTo("application/json; charset=utf-8"));

		//Print status code
		//		System.out.println("Status code: " + httpResponse.getStatusCode());
		//		System.out.println("Status line: " + httpResponse.getStatusLine());
		Assert.assertThat(httpResponse.getStatusCode(), Matchers.equalTo(200));
		Assert.assertThat(httpResponse.getStatusLine(), Matchers.containsString("200"));
	}

	@Test	
	public void test_AddHeader() {		
		//Use of RequestSpecification
		String pathUrl = "http://ergast.com/api/f1/2017/circuits";
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder = requestSpecBuilder.		
				setBaseUri(pathUrl).
				//				setContentType(ContentType.JSON).
				addQueryParam("limit", "10"); //added query param		
		RequestSpecification addRequestSpec = requestSpecBuilder.build();		

		//Added a filter with ResponseBuilder
		Filter filter = new Filter(){
			@Override
			public Response filter(FilterableRequestSpecification requestSpec,
					FilterableResponseSpecification responseSpec, FilterContext ctx) {
				// TODO Auto-generated method stub
				return new ResponseBuilder().setContentType(ContentType.JSON).build();
			}			
		};		
		RequestSpecification httpRequest = RestAssured.given().spec(addRequestSpec);//.filter(filter);		

		Response httpResponse = httpRequest.get();
		System.out.println(httpResponse.getContentType()); //returns application/xml
		System.out.println(httpResponse.getStatusLine());	//returns HTTP/1.1 200 OK
		System.out.println(httpResponse.getBody().asString());//returns XML response
	}
	@Test	
	public void test_Authorization(){
		String pathUrl = "http://restapi.demoqa.com/authentication/CheckForAuthentication";

		//Set username and password using basic auth scheme
		BasicAuthScheme basc = new BasicAuthScheme();
		basc.setUserName("ToolsQA");
		basc.setPassword("TestPassword");	

		//Set auth in RequestSpecBuilder		
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();		
		requestSpecBuilder = requestSpecBuilder.		
				setBaseUri(pathUrl).
				setContentType(ContentType.JSON).
				setAuth(basc);

		//Make http request
		RequestSpecification addRequestSpec = requestSpecBuilder.build();
		RequestSpecification httpRequest = RestAssured.given().spec(addRequestSpec);
		Response response = httpRequest.get();
		System.out.println(response.getStatusLine());//HTTP/1.1 200 OK
		System.out.println(response.getBody().path("FaultId").toString());//Read body from json response	
	}

	/*1. Serialize User class object to Json request
	 *2. Deserialize Json response to CustomJsonResponse object
	 *NOTES: It is mandatory to have class variable names similar to json keys, else value wil not be sent in request or fetcehd from response 
	 * */ 
	@Test
	@Ignore
	public void test_Serialize_UserToJson_Deserialize_RespToObj(){

		String pathUrl = "http://restapi.demoqa.com/customer";

		//user object to be serialized
		User user = new User("Virender913", "Singh913", "atul@913", "password1", "emai68913@gmail.com");
		//getters and setters are mandatory in User class

		//Set BaseURI and content type in RequestSpecBuilder		
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();		
		requestSpecBuilder = requestSpecBuilder.		
				setBaseUri(pathUrl).
				setContentType(ContentType.JSON);				 	
		RequestSpecification addRequestSpec = requestSpecBuilder.build();		

		RequestSpecification httpRequest = RestAssured.given().
				spec(addRequestSpec).
				body(user);		

		Response response = httpRequest.post("/register");
		System.out.println(response.getStatusLine());	//HTTP/1.1 201 Created
		System.out.println(response.getBody().asString());

		//Deserialization
		CustomJsonResponse customJsonResponse = response.as(CustomJsonResponse.class);
		System.out.println(customJsonResponse.getMessage());
		System.out.println(customJsonResponse.getSuccessCode());
	}

	@Test	
	@Ignore
	public void test_PostRequest_JsonObject_Map(){
		String pathUrl = "http://restapi.demoqa.com/customer";

		//Prepare request body in Json object
		JSONObject requestParam_jsonObj = new JSONObject();
		//Map<String, String> requestParam_jsonObj = new HashMap<>();
		requestParam_jsonObj.put("FirstName", "Virender828"); // Cast
		requestParam_jsonObj.put("LastName", "Singh828");		 
		requestParam_jsonObj.put("UserName", "atul@828");
		requestParam_jsonObj.put("Password", "password1");
		requestParam_jsonObj.put("Email",  "emai6828@gmail.com");		

		//Set BaseURI and content type in RequestSpecBuilder		
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();		
		requestSpecBuilder = requestSpecBuilder.		
				setBaseUri(pathUrl).
				setContentType(ContentType.JSON);
		/*
		 * Note: setting request body in Spec fails with bad response from server
		"FaultId": "Invalid post data, please correct the request",
		setBody(requestParam_jsonObj);
		 */		
		RequestSpecification addRequestSpec = requestSpecBuilder.build();

		//If Json object is used in below body method, then it has to be converted toString() else response fails
		//If map is used, then toString should not be put else it fails
		RequestSpecification httpRequest = RestAssured.given().
				spec(addRequestSpec).
				body(requestParam_jsonObj.toString());		

		Response response = httpRequest.post("/register");
		System.out.println(response.getStatusLine());	//HTTP/1.1 201 Created
		System.out.println(response.getBody().asString());

		//Validate response using response specification
		ResponseSpecification responseSpec = new ResponseSpecBuilder().
				expectStatusCode(200).
				expectBody("SuccessCode", Matchers.equalTo("OPERATION_SUCCESS")).
				build();
		responseSpec.validate(response);
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
