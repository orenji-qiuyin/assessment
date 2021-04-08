package com.property.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Property {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String name;
	private String location;
	private String description;
	private float price;
	private Integer house_sqft;
	private int is_fully_furnished;
	private int rise_type;
	private int show_category;
	private boolean is_approved = false;

	public Property() {
		super();
	}
	
	public Property(Integer id, String name, String location, String description, float price, Integer house_sqft,
			int is_fully_furnished, int rise_type, int show_category, boolean is_approved) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.description = description;
		this.price = price;
		this.house_sqft = house_sqft;
		this.is_fully_furnished = is_fully_furnished;
		this.rise_type = rise_type;
		this.show_category = show_category;
		this.is_approved = is_approved;
	}
	
	public Property(String name, String location, String description, float price, Integer house_sqft,
			int is_fully_furnished, int rise_type, int show_category) {
		super();
		this.name = name;
		this.location = location;
		this.description = description;
		this.price = price;
		this.house_sqft = house_sqft;
		this.is_fully_furnished = is_fully_furnished;
		this.rise_type = rise_type;
		this.show_category = show_category;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public Integer getHouseSqft() {
		return house_sqft;
	}
	
	public void setHouseSqft(Integer house_sqft) {
		this.house_sqft = house_sqft;
	}
	
	public Integer getIsFullyFurnished() {
		return is_fully_furnished;
	}
	
	public void setIsFullyFurnished(int is_fully_furnished) {
		this.is_fully_furnished = is_fully_furnished;
	}
	
	public Integer getRiseType() {
		return rise_type;
	}
	
	public void setRiseType(int rise_type) {
		this.rise_type = rise_type;
	}
	
	public Integer getShowCategory() {
		return show_category;
	}
	
	public void setShowCategory(int show_category) {
		this.show_category = show_category;
	}
	
	public boolean isIs_approved() {
		return is_approved;
	}

	public void setIs_approved(boolean is_approved) {
		this.is_approved = is_approved;
	}
}
