package com.property.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.property.entity.Property;
import com.property.service.PropertyService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyControllerTest {

    @LocalServerPort
    private int port;
    
    @Autowired
	private PropertyService propertyService;

    TestRestTemplate restTemplate = new TestRestTemplate();
    
    @Value("${spring.security.user.password}")
	private String apiKey;
    
    private int testId = 1;

    HttpHeaders headers = new HttpHeaders();
    @Test
    public void testProperty() throws Exception {
    	headers.add("key", apiKey);
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	String content = "\"name\":\"planetT5\","
    			+ "\"location\":\"road galaxy, area Q5\","
    			+ "\"description\":\"the house which like a planet desu\","
    			+ "\"price\":\"1500.0\","
    			+ "\"houseSqft\":\"1000\","
    			+ "\"isFullyFurnished\":\"1\","
    			+ "\"riseType\":\"1\","
    			+ "\"showCategory\":\"0\"";   			
    	
    	// create property
    	String body = "{"
    			+ content
    			+ "}";
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> addResponse = restTemplate.exchange(
          createURLWithPort("/api/add"), HttpMethod.POST, entity, String.class);

        String actual = addResponse.getHeaders().get(HttpHeaders.LOCATION).get(0);

        testId = Integer.parseInt(actual.substring(actual.lastIndexOf("/") + 1));
        assertTrue(actual.contains("/property"));
        
        // update property
        body = "{\r\n"
        		+ "\"id\":\"" + testId + "\","
        		+ content
    			+ "}";
        
        HttpEntity<String> editEntity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> editResponse = restTemplate.exchange(
                createURLWithPort("/api/" + testId), HttpMethod.PATCH, editEntity, String.class);
       
        assertTrue(editResponse.getStatusCodeValue()==HttpStatus.ACCEPTED.value());
        
        // get property by id
        Property getProperty = propertyService.get(testId);
        
        ResponseEntity<String> getResponse = restTemplate.exchange(
          createURLWithPort("/api/" + testId), HttpMethod.GET, entity, String.class);
        
        String expected = "{\"id\":" + testId
        		+ ",\"name\":\"" + getProperty.getName() + "\""
        		+ ",\"location\":\"" + getProperty.getLocation() + "\""
        		+ ",\"description\":\"" + getProperty.getDescription() + "\""
        		+ ",\"price\":" + getProperty.getPrice()
        		+ ",\"is_approved\":" + getProperty.isIs_approved()
        		+ ",\"isFullyFurnished\":" + getProperty.getIsFullyFurnished()
				+ ",\"houseSqft\":" + getProperty.getHouseSqft()
    	        + ",\"riseType\":" + getProperty.getRiseType()
    	        + ",\"showCategory\":" + getProperty.getShowCategory()
    	        + "}";
		
        JSONAssert.assertEquals(expected, getResponse.getBody(), true);
        
        // search property list
        ResponseEntity<String> searchResponse = restTemplate.exchange(
          createURLWithPort("/api/search"), HttpMethod.GET, entity, String.class);
        
        assertTrue(searchResponse.getStatusCodeValue()==HttpStatus.OK.value());
    }    

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
