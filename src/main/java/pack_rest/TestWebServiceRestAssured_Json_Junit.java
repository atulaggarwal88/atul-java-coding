package pack_rest;


//import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import org.hamcrest.Matchers;


public class TestWebServiceRestAssured_Json_Junit {
	int expectedStatusCode = 200;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//		http://ergast.com/api/f1
		/*String baseUrl = "http://ergast.com/api/f1/";
		RestAssured.baseURI = baseUrl;	*/	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void test_StatusCode_ContentType_Length() {
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
	@Ignore
	public void test_StatusCode_Headers() {
		/*Below shouldnt be included in every test method as baseURL is shared among tests
		 * String baseUrl = "http://ergast.com/api/f1/";
		RestAssured.baseURI = baseUrl;
		*/ 		
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
		System.out.println("Content type is: " + contentType);
		
		//Print status code
		System.out.println("Status code: " + httpResponse.getStatusCode());
		System.out.println("Status code: " + httpResponse.getStatusLine());
	}

	@Test
	@Ignore
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
	@Ignore
	public void test_CalculateSumOfPointsFor1stPlace(){
		/*
		 * We will calculate total number of points for 
		Position = 1st place in 
		Season = 2015; Round = 1 and 19
		 */
		
		
		String pathUrl = "{year}/{round}/constructorStandings.json";

		String yearVar = "2015";
		String roundVar = "18";		

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
	public void test_AddHeader() {

		String pathUrl = "http://ergast.com/api/f1/2017/circuits";
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder = requestSpecBuilder.
		addHeader("Accept", "application/json").
				setBaseUri(pathUrl).
//		setContentType(ContentType.JSON).
		addQueryParam("limit", "10"); //added query param		
		RequestSpecification addRequestSpec = requestSpecBuilder.build();
		
//		requestSpecBuilder.setBaseUri("http://ergast.com/api/f1/");
//		
		RequestSpecification httpRequest = RestAssured.given().spec(addRequestSpec).contentType(ContentType.JSON);		
		Response httpResponse = httpRequest.get();
		System.out.println(httpResponse.getContentType());
		System.out.println(httpResponse.getStatusLine());
//		System.out.println(httpResponse.getBody().asString());
		
	}

}
