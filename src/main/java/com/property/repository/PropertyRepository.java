package com.property.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.property.entity.Property;

@Repository
public interface PropertyRepository extends PagingAndSortingRepository<Property, Integer>, JpaSpecificationExecutor<Property> {
}