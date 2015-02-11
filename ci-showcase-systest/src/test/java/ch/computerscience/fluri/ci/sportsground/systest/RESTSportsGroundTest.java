package ch.computerscience.fluri.ci.sportsground.systest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Before;
import org.junit.Test;

public class RESTSportsGroundTest {

    private HttpClient client;
    private URI sportsGroundUri;

    @Before
    public void prepareHttpClient() throws Exception {
        client = new HttpClient();
        client.getParams().setParameter("http.useragent", "Test Client");
        String endpoint = System.getProperties().getProperty("url");
        if (endpoint == null || endpoint.isEmpty()) {
            sportsGroundUri = new URI("http://localhost:8180/ci-showcase-server/sportsground", true);
        } else {
            sportsGroundUri = new URI(endpoint, true);
        }
    }

    @Test
    public void getAllSportsGroundsShouldReturnOneGroundInXML() throws Exception {
        String response = getAllSportsGrounds();
        assertThat(response, containsString("<id>1</id>"));
        assertThat(response, containsString("<name>My Ground</name>"));
    }

    @Test
    public void putSportsGroundShouldStoreSportsGround() throws Exception {
        PutMethod method = new PutMethod();
        method.setURI(sportsGroundUri);
        StringBuilder sportsGroundXml = new StringBuilder();
        sportsGroundXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        sportsGroundXml.append("<sportsGround>");
        sportsGroundXml.append("<name>My Test-Ground</name>");
        sportsGroundXml.append("<numberOfSportCourts>2</numberOfSportCourts>");
        sportsGroundXml.append("<sport><id>2</id></sport>");
        sportsGroundXml.append("</sportsGround>");
        StringRequestEntity sre = new StringRequestEntity(sportsGroundXml.toString(), "application/xml", "UTF-8");
        method.setRequestEntity(sre);
        int returnCode = client.executeMethod(method);
        assertThat(returnCode, is(HttpStatus.SC_NO_CONTENT));
        String response = getAllSportsGrounds();
        assertThat(response, containsString("<name>My Test-Ground</name>"));
    }

    private String getAllSportsGrounds() throws Exception {
        GetMethod method = new GetMethod();
        method.setURI(sportsGroundUri);
        client.executeMethod(method);
        int returnCode = client.executeMethod(method);
        assertThat(returnCode, is(HttpStatus.SC_OK));
        return method.getResponseBodyAsString();
    }
}
