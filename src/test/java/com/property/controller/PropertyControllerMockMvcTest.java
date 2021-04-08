package com.property.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.property.constants.APIConstants;
import com.property.entity.Property;
import com.property.service.PropertyService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerMockMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PropertyService propertyService;

	@Value("${spring.security.user.password}")
	private String apiKey;

	@Test
	public void addPropertyTest() throws Exception {

		Property p = new Property("nameQ", "locatinQ", "descQ", 1000, 700
				, APIConstants.IS_FULLY_FURNISHED_FULLY, APIConstants.RISE_TYPE_HIGH, APIConstants.SHOW_CATEGORY_RENT);
		
		mockMvc.perform(
				post("/api/add").contentType(MediaType.APPLICATION_JSON).content(toJson(p)).header("key", apiKey))
				.andExpect(status().isCreated());
	}

	@Test
	public void editPropertyTest() throws Exception {

		Property p = new Property(1,"nameQ", "locatinQ", "descQ", 1000, 700
				, APIConstants.IS_FULLY_FURNISHED_FULLY, APIConstants.RISE_TYPE_HIGH, APIConstants.SHOW_CATEGORY_RENT,false);

		mockMvc.perform(
				patch("/api/"+p.getId())
				.contentType(MediaType.APPLICATION_JSON).content(toJson(p)).header("key", apiKey))
				.andExpect(status().isAccepted());
	}
	
	@Test
	public void approvePropertyTest() throws Exception {

		Property p = new Property(1,"nameQ", "locatinQ", "descQ", 1000, 700
				, APIConstants.IS_FULLY_FURNISHED_FULLY, APIConstants.RISE_TYPE_HIGH, APIConstants.SHOW_CATEGORY_RENT,false);

		mockMvc.perform(
				patch("/api/approve/"+p.getId())
				.contentType(MediaType.APPLICATION_JSON).content(toJson(p)).header("key", apiKey))
				.andExpect(status().isAccepted());
	}
	
	@Test
	public void getPropertyTest() throws Exception {

		mockMvc.perform(
				get("/api/"+1) //id=1
				.header("key", apiKey))
				.andExpect(status().isOk());
	}
	
	@Test
	public void searchPropertyTest() throws Exception {

		mockMvc.perform(
				get("/api/search")
				.header("key", apiKey))
				.andExpect(status().isOk());
	}

	private String toJson(Property p) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(p);
	}
}
