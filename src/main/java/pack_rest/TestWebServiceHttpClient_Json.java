package pack_rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.xml.sax.SAXException;

public class TestWebServiceHttpClient_Json {
	static Map<String, String> map = new HashMap<>();

	public static void main(String[] args) throws ClientProtocolException, IOException, SAXException, ParserConfigurationException, InterruptedException {
		// TODO Auto-generated method stub
		String restUrl_Json = "http://services.groupkt.com/country/get/all";

		validateStatusCode(restUrl_Json, HttpStatus.SC_OK);		
		validateMimeTypeCharSet(restUrl_Json, "application/json", "UTF-8");
		validateContent_Json(restUrl_Json, "Australia", "AUS");

	}
	public static void validateStatusCode(String restUrl, int expectedStatusCode) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(restUrl);		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println("Actual status code: " + statusCode);
		Assert.assertEquals(statusCode, expectedStatusCode);		
	}
	public static void validateMimeTypeCharSet(String restUrl, String expectedMimeType, String expectedCharSet) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(restUrl);		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		HttpEntity entity = response.getEntity();
		System.out.println(entity);//ResponseEntityProxy{[Content-Type: application/json;charset=UTF-8,Chunked: true]}
		System.out.println(entity.getContentType());//Content-Type: application/json;charset=UTF-8

		//Get mime type
		String actualMimeType = ContentType.getOrDefault(entity).getMimeType();
		System.out.println("Actual mime type: " + actualMimeType);
		Assert.assertEquals(actualMimeType, expectedMimeType);			

		//Get charset type
		System.out.println(ContentType.getOrDefault(entity).getCharset());
		String actualCharSet = ContentType.getOrDefault(entity).getCharset().toString();
		Assert.assertEquals(actualCharSet, expectedCharSet);

		/*
		 * Another way to retrieve content type
		System.out.println(Arrays.toString(response.getAllHeaders()));
		//[Date: Tue, 26 Jun 2018 05:33:39 GMT, Server: Apache/2.4.25 (Debian), X-Content-Type-Options: nosniff, X-XSS-Protection: 1; mode=block, Cache-Control: no-cache, no-store, max-age=0, must-revalidate, Pragma: no-cache, Expires: 0, X-Frame-Options: DENY, Keep-Alive: timeout=5, max=100, Connection: Keep-Alive, Transfer-Encoding: chunked, Content-Type: application/json;charset=UTF-8]

		System.out.println(response.getLastHeader("Content-Type"));//Content-Type: application/json;charset=UTF-8

		 */				
	}
	public static void validateContent_Json(String restUrl, String name, String expectedValue) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(restUrl);		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		HttpEntity entity = response.getEntity();		

		//Convert entity to String format		
		String result = EntityUtils.toString(entity);
		System.out.println(result);//Message in response body is printed

		//Traverse JSON object
		JSONObject jObj = new JSONObject(result).getJSONObject("RestResponse");
		JSONArray jObjArray = jObj.getJSONArray("result");

		//		System.out.println(jObjArray);//Message in response body is printed in JSON format
		//		System.out.println(jObjArray.getJSONObject(0));//first json object in array
		//		System.out.println(jObjArray.length());//249

		jObjArray.forEach(TestWebServiceHttpClient_Json::loadMapFromJson);//Traverse JSON Array and load into Map
		String alpha3_code_actual = map.get(name);
		//		System.out.println(alpha3_code_actual);//prints AUS for Country = Australia
		Assert.assertEquals(alpha3_code_actual, expectedValue);

	}
	public static void loadMapFromJson(Object obj){
		JSONObject jObj = (JSONObject)obj;
		String name = jObj.getString("name");
		String alpha3_code = jObj.getString("alpha3_code");
		map.put(name, alpha3_code);			
	}


}
