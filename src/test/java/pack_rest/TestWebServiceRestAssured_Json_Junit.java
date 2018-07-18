package pack_rest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.json.JSONObject;

import com.runner.CustomJUnitRunner;
import com.annotations.FunctionalTest;

//CustomJUnitRunner is a custom JUnit runner.
/*@RunWith is not needed if running test cases from main method of 
TestJUnitCoreRunner using JUnitCore class.*/
//@RunWith(CustomJUnitRunner.class)
public class TestWebServiceRestAssured_Json_Junit {

	@Rule
	public ErrorCollector ec = new ErrorCollector();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//Method runs Before TestClass
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {	
		//Method runs After TestClass
	}

	@Before
	public void setUp() throws Exception {
		//Method runs Before TestMethod
	}

	@After
	public void tearDown() throws Exception {
		//Method runs After TestMethod
	}

	/*Validate header - approach 1*/
	@Test
	@Category(FunctionalTest.class)
	public void test_StatusCode_ContentType_Length() {		
		/*String baseUrl = "http://ergast.com/api/f1/";
		RestAssured.baseURI = baseUrl;*/
		try{
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
		}catch(Throwable t){
			ec.addError(t);
		}
	}

	/*Validate size of JSon*/
	@Test
	public void test_SizeOfJson(){
		try{
			String url = "http://ergast.com/api/f1/2008/10/results.json";
			given().
			when().
			get(url).
			then().
			assertThat().
			body("MRData.RaceTable.Races[0].Results.position", Matchers.hasSize(20));
		}catch(Throwable t){
			ec.addError(t);
		}
	}

	/*Fetch value from JSon response*/
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
		//		System.out.printf("Point in round %s is: " + point1 + "\n", roundVar);

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

		//		System.out.printf("Point in round %s is: " + point2 + "\n", roundVar);
		//		System.out.println("Total points: " + (point1 + point2));

		//		Assert.assertThat((point1 + point2), Matchers.equalTo(703));
		//ErrorCollector can be used instead of Assertions:
		ec.checkThat((point1 + point2), Matchers.equalTo(703));
	}

	/*Validate header - approach 2*/
	@Test
	public void test_StatusCode_Headers() {
		String baseUrl = "http://ergast.com/api/f1/";
		RestAssured.baseURI = baseUrl;		
		String pathUrl = "2017/circuits.json";

		RequestSpecification httpRequest = RestAssured.given();
		Response httpResponse = httpRequest.get(pathUrl);

		//Print all header
		/*Headers headers = httpResponse.getHeaders();
		for(Header header: headers){
			System.out.print("Name: " + header.getName() + ",\t");
			System.out.println("Value: " + header.getValue());
		}*/

		//Print content type
		String contentType = httpResponse.getHeader("Content-Type");
		//		System.out.println("Content type is: " + contentType);
		//Print status code
		//		System.out.println("Status code: " + httpResponse.getStatusCode());
		//		System.out.println("Status line: " + httpResponse.getStatusLine());

		//		Assert.assertThat(contentType, Matchers.equalTo("application/json; charset=utf-8"));
		//		Assert.assertThat(httpResponse.getStatusCode(), Matchers.equalTo(200));
		//		Assert.assertThat(httpResponse.getStatusLine(), Matchers.containsString("200"));

		//Use of ErrorCollector
		ec.checkThat(contentType, Matchers.equalTo("application/json; charset=utf-8"));
		ec.checkThat(httpResponse.getStatusCode(), Matchers.equalTo(200));
		ec.checkThat(httpResponse.getStatusLine(), Matchers.containsString("200"));
	}

	/*Validate XML reponse*/
	@Test	
	public void test_XmlParsing() {		
		//Use of RequestSpecification
		String pathUrl = "http://ergast.com/api/f1/2017/circuits";
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder = requestSpecBuilder.		
				setBaseUri(pathUrl).
				//				setContentType(ContentType.JSON).
				addQueryParam("limit", "10"); //added query param		
		RequestSpecification addRequestSpec = requestSpecBuilder.build();		

		RequestSpecification httpRequest = RestAssured.given().spec(addRequestSpec);//.filter(filter);		

		Response httpResponse = httpRequest.get();
		//		System.out.println(httpResponse.getContentType()); //returns application/xml
		//		System.out.println(httpResponse.getStatusLine());	//returns HTTP/1.1 200 OK
		//		System.out.println(httpResponse.getBody().asString());//returns XML response

		//Get XML response
		XmlPath xmlPath = httpResponse.getBody().htmlPath();

		//		System.out.println(xmlPath.getString("MRdata.CircuitTable.Circuit[0].CircuitName"));//prints Circuit[0]-->CircuitName
		//Print all Circuit names using GPath.
		//For XML response, use GPath notation to iterate through the response
		//		xmlPath.getList("MRdata.CircuitTable.Circuit.CircuitName").stream().forEach(System.out::println);

		//		Assert.assertThat(xmlPath.getString("MRdata.CircuitTable.Circuit.size()"), Matchers.equalTo("10"));
		//Use of ErrorCollector
		ec.checkThat(xmlPath.getString("MRdata.CircuitTable.Circuit.size()"), Matchers.equalTo("10"));
	}

	/*Validate conversion of XML response (received from server) to JSon using ResponseBuilder*/
	@Test	
	public void test_ChangeResponseBodyFromXmlToJson() {		
		//Use of RequestSpecification
		String pathUrl = "http://ergast.com/api/f1/2017/circuits";
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder = requestSpecBuilder.		
				setBaseUri(pathUrl).
				//				setContentType(ContentType.JSON).
				addQueryParam("limit", "10"); //added query param		
		RequestSpecification addRequestSpec = requestSpecBuilder.build();		

		RequestSpecification httpRequest = RestAssured.given().spec(addRequestSpec);//.filter(filter);		

		//COnvert XML response to JSon 
		Response httpResponse = httpRequest.get();		
		Response newHttpResponse = new ResponseBuilder().clone(httpResponse).setContentType(ContentType.JSON).build();

		//		System.out.println(newHttpResponse.getContentType()); //returns application/json
		//		System.out.println(newHttpResponse.getStatusLine());	//returns HTTP/1.1 200 OK
		//		System.out.println(newHttpResponse.getBody().asString());//still it returns XML response
		Assert.assertThat(newHttpResponse.getContentType(), Matchers.equalTo("application/json"));		
	}

	/*Validate Basic challenged authorization*/
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
		//		System.out.println(response.getStatusLine());//HTTP/1.1 200 OK
		//		System.out.println(response.getBody().path("FaultId").toString());//Read body from json response	

		Assert.assertThat(response.getStatusLine(), Matchers.equalTo("HTTP/1.1 200 OK"));
		Assert.assertThat(response.getBody().path("FaultId").toString(), Matchers.equalTo("OPERATION_SUCCESS"));		
	}

	/*1. Serialize User class object to Json request
	 *2. Deserialize Json response to CustomJsonResponse object
	 *NOTES: It is mandatory to have class variable names similar to json keys, else value wil not be sent in request or fetcehd from response 
	 * */ 
	@Test	
	public void test_Serialize_UserToJson_Deserialize_RespToObj(){

		String pathUrl = "http://restapi.demoqa.com/customer";
		//complete request URL: "http://restapi.demoqa.com/customer?FirstName=Virender570&LastName=Singh570&UserName=atul%40570&Password=password1&Email=emai570@gmail.com";

		//user object to be serialized
		User user = new User("Virender511", "Singh511", "atul511", "password1", "emai5881@gmail.com");
		//getters and setters are mandatory in User class

		//Set BaseURI and content type in RequestSpecBuilder		
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();		
		requestSpecBuilder = requestSpecBuilder.		
				setBaseUri(pathUrl).
				setContentType(ContentType.JSON);				 	
		RequestSpecification addRequestSpec = requestSpecBuilder.build();		

		RequestSpecification httpRequest = RestAssured.given().
				spec(addRequestSpec).
				body(user);//Serialized user object to JSon request		

		Response response = httpRequest.post("/register");
		//		System.out.println(response.getStatusLine());	//HTTP/1.1 201 Created
		//		System.out.println(response.getBody().asString());

		//		Assert.assertThat("Status is not 201", response.getStatusLine(), Matchers.containsString("201"));

		//Deserialization
		//		System.out.println("Deserializing json response");
		CustomJsonResponse customJsonResponse = response.as(CustomJsonResponse.class);
		//		System.out.println(customJsonResponse.getMessage());
		//		System.out.println(customJsonResponse.getSuccessCode());
		//		System.out.println(customJsonResponse.getFaultId());//User already exists
		//		System.out.println(customJsonResponse.getFault());//FAULT_USER_ALREADY_EXISTS
		//		Assert.assertThat("Message is null", customJsonResponse.getMessage(), Matchers.anything());
		//		Assert.assertThat("Message is null", customJsonResponse.getSuccessCode(), Matchers.containsString("201"));
		Assert.assertThat("Message is null", customJsonResponse.getFaultId(), Matchers.equalTo("User already exists"));
		Assert.assertThat("Message is null", customJsonResponse.getFault(), Matchers.equalTo("FAULT_USER_ALREADY_EXISTS"));
	}


	/*Validate post request using JSon object
	Validate post request using Map*/
	@Test
	public void test_PostRequest_JsonObject_Map(){
		String pathUrl = "http://restapi.demoqa.com/customer";

		//Prepare request body in Json object
		JSONObject requestParam_jsonObj = new JSONObject();
		//Map<String, String> requestParam_jsonObj = new HashMap<>();
		requestParam_jsonObj.put("FirstName", "Virender411"); // Cast
		requestParam_jsonObj.put("LastName", "Singh411");		 
		requestParam_jsonObj.put("UserName", "atul@411");
		requestParam_jsonObj.put("Password", "password1");
		requestParam_jsonObj.put("Email",  "emai673@gmail.com");		

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
		//		System.out.println(response.getStatusLine());	//HTTP/1.1 201 Created
		//		System.out.println(response.getBody().asString());

		//Validate response using response specification
		//		ResponseSpecification responseSpec = new ResponseSpecBuilder().
		//				expectStatusCode(200).
		//				expectBody("SuccessCode", Matchers.equalTo("OPERATION_SUCCESS")).
		//				build();
		//		
		//		responseSpec.validate(response);		
		Assert.assertThat("Status is not 201", response.getStatusLine(), Matchers.containsString("200"));
	}

}

//Below is a class to test De-serialization
class CustomJsonResponse{
	/*It is not mandatory to have variable name similar to Json response because we have used
	@JsonProperty on setter and getter methods.
	Getter method --used while serialization
	Setter method -- used while de-serialization
	 */
	@JsonIgnore	private String successCode;	
	@JsonIgnore private String message;	
	@JsonIgnore private String faultId;
	@JsonIgnore private String fault;

	@JsonCreator
	public CustomJsonResponse(@JsonProperty("SuccessCode") String successCode, 
			@JsonProperty("Message") String message,
			@JsonProperty("FaultId") String faultId,
			@JsonProperty("Fault") String fault){
		this.successCode = successCode;
		this.message = message;
		this.faultId = faultId;
		this.fault = fault;
	}

	@JsonProperty("SuccessCode")
	public String getSuccessCode() {
		return successCode;
	}

	@JsonProperty("SuccessCode")
	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}

	@JsonProperty("Message")
	public String getMessage() {
		return message;
	}
	@JsonProperty("Message")
	public void setMessage(String message) {
		this.message = message;
	}
	@JsonProperty("FaultId")
	public String getFaultId() {
		return faultId;
	}
	@JsonProperty("FaultId")
	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}
	@JsonProperty("fault")
	public String getFault() {
		return fault;
	}
	@JsonProperty("fault")
	public void setFault(String fault) {
		this.fault = fault;
	}
} 


//Below is a class to test serialization
class User{
	/*It is not mandatory to have variable name similar to Json response because we have used
	@JsonProperty on setter and getter methods.
	Getter method --used while serialization
	Setter method -- used while de-serialization
	 */
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;	
	public User(String firstName, String lastName, String userName, String password, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}
	@JsonProperty("FirstName")
	public String getFirstName() {
		return firstName;
	}
	@JsonProperty("LastName")
	public String getLastName() {
		return lastName;
	}
	@JsonProperty("UserName")
	public String getUserName() {
		return userName;
	}
	@JsonProperty("Password")
	public String getPassword() {
		return password;
	}
	@JsonProperty("Email")
	public String getEmail() {
		return email;
	}

}
