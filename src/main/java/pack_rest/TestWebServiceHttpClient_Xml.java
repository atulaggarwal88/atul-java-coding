package pack_rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.w3c.dom.Element;*/

public class TestWebServiceHttpClient_Xml {
	static Map<String, String> map = new HashMap<>();

	public static void main(String[] args) throws ClientProtocolException, IOException, SAXException, ParserConfigurationException, InterruptedException {
		// TODO Auto-generated method stub
		
		String restUrl_Xml = "http://parabank.parasoft.com/parabank/services/bank/customers/12212";

		validateStatusCode(restUrl_Xml, HttpStatus.SC_OK);
		validateMimeTypeCharSet(restUrl_Xml, "application/xml", "UTF-8");
		validateContent_Xml(restUrl_Xml, "firstName", "John");

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

		/*
		 * Another way to retrieve content type
		System.out.println(Arrays.toString(response.getAllHeaders()));
		//[Date: Tue, 26 Jun 2018 05:33:39 GMT, Server: Apache/2.4.25 (Debian), X-Content-Type-Options: nosniff, X-XSS-Protection: 1; mode=block, Cache-Control: no-cache, no-store, max-age=0, must-revalidate, Pragma: no-cache, Expires: 0, X-Frame-Options: DENY, Keep-Alive: timeout=5, max=100, Connection: Keep-Alive, Transfer-Encoding: chunked, Content-Type: application/json;charset=UTF-8]

		System.out.println(response.getLastHeader("Content-Type"));//Content-Type: application/json;charset=UTF-8

		 */				
	}

	public static void validateContent_Xml(String restUrl_Xml, String tagName, String expectedValue) throws SAXException, IOException, ParserConfigurationException, InterruptedException{
		/*
		 * Use this code in order to explicitly set the response type to xml
		 *
		URL url = new URL(restUrl_Xml);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
		InputStream is = connection.getInputStream();
		Document document = db.parse(is);
		System.out.println(document); //this will always print [#document: null]
		 */

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(restUrl_Xml);

		NodeList nodeList = document.getElementsByTagName(tagName);		
		//		System.out.println("Length of node: " + nodeList.getLength());

		Node node = nodeList.item(0);
		//		System.out.println(node.getTextContent());//John
		String actualValue = node.getTextContent();
		Assert.assertEquals(actualValue, expectedValue);

		/*
		 * Do not use nodeList.item(0) directly because it does not print values
		System.out.println(nodeList.item(0));//[lastName: null]

		You may use either Node or Element as shown below:		
		Element elem = (Element)nodeList.item(0);
		System.out.println(elem.getTextContent());
		Or
		Node node = nodeList.item(0);
		System.out.println(node.getTextContent());
		 */		

	}
	public static void iterateThroughNode(String restUrl_Xml) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(restUrl_Xml);

		//		Print all sub nodes in a node
		NodeList nodeList2 = document.getDocumentElement().getChildNodes();
		for(int i=0; i<nodeList2.getLength(); i++){
			Node node2 = nodeList2.item(i);
			if(node2.hasChildNodes() && node2.getNodeType() == 3){
				NodeList nodeList3 = node2.getChildNodes();
				for(int j=0; j<nodeList3.getLength(); j++){
					Node node3 = nodeList3.item(i);
					System.out.println(node3.getNodeName()+ ": " + node3.getTextContent());
				}
			}else{
				System.out.println(node2.getNodeName()+ " : " + node2.getTextContent());
			}
		}
	}


}
