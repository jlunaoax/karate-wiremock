package examples;

import com.github.tomakehurst.wiremock.WireMockServer;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.junit4.Karate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;


@RunWith(Karate.class)
@CucumberOptions(tags = { "@wiremock" })
public class WireMockTest {

	private static WireMockServer wireMockServer = new WireMockServer();
	//WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8080));
	
	@BeforeClass
	public static void setUp() throws Exception {
		wireMockServer.start();
	  configureFor("localhost", 8080);
	    stubFor(
	      get(urlEqualTo("/user/get")).atPriority(1) //1 is highest
		.willReturn(aResponse()
		  .withStatus(200)
		  .withHeader("Content-Type", "application/json")
		  .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));
	
	    stubFor(
	      get(urlEqualTo("/user/get/1234")).atPriority(1) //1 is highest
		.willReturn(aResponse()
		  .withStatus(200)
		  .withHeader("Content-Type", "application/json")
		  .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));
		
	    	//Catch-all case
		stubFor(get(urlMatching("/user/get/.*")).atPriority(5)
		    .willReturn(aResponse()
			.withStatus(401)
			.withBody("{\"status\":\"Error\",\"message\":\"Forbiden\"}")));


	    //When a request cannot be mapped to a response, Wiremock returns an HTML response with a 404 status code.	
            stubFor(any(anyUrl())
                .atPriority(10)
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("{\"status\":\"Error\",\"message\":\"Server not found\"}")));

	    stubFor(
	      post(urlEqualTo("/user/create")).atPriority(1)
		.withRequestBody(containing("id"))
		.willReturn(aResponse()
		  .withStatus(200)
		  .withHeader("Content-Type", "application/json")
		  .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));
	 
	}
	 
	@AfterClass
	public static void tearDown() throws Exception {
	    wireMockServer.stop();
	}
}
