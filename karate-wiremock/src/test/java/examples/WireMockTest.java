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
	      get(urlEqualTo("/user/get"))
		.willReturn(aResponse()
		  .withStatus(200)
		  .withHeader("Content-Type", "application/json")
		  .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));
	 
	    stubFor(
	      post(urlEqualTo("/user/create"))
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
