package com.property.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
import java.util.List;

import com.property.entity.Property;
import com.property.entity.util.PagingHeaders;
import com.property.entity.util.PagingResponse;
import com.property.service.PropertyService;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@Controller	// This means that this class is a Controller
@Configuration
@RequestMapping(path="/api") // This means URL's start with /api (after Application path)
public class PropertyController {

	private final PropertyService propertyService;
	
	@Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
	
	@PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Property> create(@RequestBody Property item) {        
        Property persistedProperty = propertyService.create(item);
	    return ResponseEntity
	        .created(URI
	                 .create(String.format("/property/%o", item.getId())))
	        .body(persistedProperty);
    }
	
	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Property> update(@PathVariable(name = "id") Integer id, @RequestBody Property item) {
		Property persistedProperty = propertyService.update(id, item);
		
		return ResponseEntity
				.accepted()
				.body(persistedProperty);
    }
	
	@PatchMapping(value = "/approve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Property> update(@PathVariable(name = "id") Integer id) {
		Property getProperty = propertyService.get(id);
		Property persistedProperty = new Property();
		if (getProperty!=null) {
			getProperty.setIs_approved(true);
			persistedProperty = propertyService.update(id, getProperty);
		}
		
        return ResponseEntity
				.accepted()
				.body(persistedProperty);
    }
	
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Property> get(@PathVariable(name = "id") Integer id) {
		Property persistedProperty = propertyService.get(id);
		
		return ResponseEntity
				.ok()
				.body(persistedProperty);
    }
	
    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Property>> get(
            @And({
            		@Spec(path = "name", params = "name", spec = Like.class),
            		@Spec(path = "location", params = "location", spec = Like.class),
                    @Spec(path = "description", params = "description", spec = Like.class),
                    @Spec(path = "price", params = "price", spec = Equal.class),
                    @Spec(path = "price", params = {"priceGt", "priceLt"}, spec = Between.class),
                    @Spec(path = "house_sqft", params = "house_sqft", spec = Equal.class),
                    @Spec(path = "house_sqft", params = {"house_sqftGt", "house_sqftLt"}, spec = Between.class),
                    @Spec(path = "is_fully_furnished", params = "is_fully_furnished", spec = Equal.class),
                    @Spec(path = "rise_type", params = "rise_type", spec = Equal.class),
                    @Spec(path = "show_category", params = "show_category", spec = Equal.class),
                    @Spec(path = "is_approved", params = "is_approved", spec = Equal.class)
            }) Specification<Property> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        final PagingResponse response = propertyService.get(spec, headers, sort);
        
        return new ResponseEntity<>(
        		response!=null?response.getElements():null
        				, response!=null?returnHttpHeaders(response):null
        						, HttpStatus.OK);
    }
	
	public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}
