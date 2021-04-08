package com.property.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.property.constants.APIConstants;
import com.property.entity.Property;
import com.property.repository.PropertyRepository;
import com.property.service.PropertyService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyControllerMockTest {

	@Autowired
	private PropertyService propertyService;

	@MockBean
	private PropertyRepository propertyRepository;

	@Test
	public void testCreatePropertyWithMockRepository() throws Exception {

		Property p = new Property("nameQ", "locatinQ", "descQ", 1000, 700
				, APIConstants.IS_FULLY_FURNISHED_FULLY, APIConstants.RISE_TYPE_HIGH, APIConstants.SHOW_CATEGORY_RENT);
		when(propertyRepository.save(p)).thenReturn(p);

		assertTrue(propertyService.create(p).getName().contains("nameQ"));
	}
	
	@Test
	public void testUpdatePropertyWithMockRepository() throws Exception {

		Property p = new Property(1,"nameQ", "locatinQ", "descQ", 1000, 700
				, APIConstants.IS_FULLY_FURNISHED_FULLY, APIConstants.RISE_TYPE_HIGH, APIConstants.SHOW_CATEGORY_RENT,false);
		when(propertyRepository.save(p)).thenReturn(p);

		assertTrue(propertyService.create(p).getId()==1);
	}
	
	@Test
	public void testRetrievePropertyWithMockRepository() throws Exception {

		Optional<Property> optProperty = Optional.of(new Property(1,"nameQ", "locatinQ", "descQ", 1000, 700
				, APIConstants.IS_FULLY_FURNISHED_FULLY, APIConstants.RISE_TYPE_HIGH, APIConstants.SHOW_CATEGORY_RENT,false));
		when(propertyRepository.findById(1)).thenReturn(optProperty);

		assertTrue(propertyService.get(1).getName().contains("nameQ"));
	}
}
