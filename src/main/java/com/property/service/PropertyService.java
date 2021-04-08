package com.property.service;

import com.property.entity.Property;
import com.property.entity.util.PagingHeaders;
import com.property.entity.util.PagingResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.property.repository.PropertyRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Objects;

@EnableTransactionManagement
@Service
public class PropertyService {

  private final PropertyRepository propertyRepository;
  
  @Autowired
  public PropertyService(PropertyRepository propertyRepository) {
	  this.propertyRepository = propertyRepository;
  }

  /**
   * @param id element ID
   * @return element
   * @throws EntityNotFoundException Exception when retrieve element
   */
  @Transactional
  public Property get(Integer id) {
      return propertyRepository.findById(id)
                          .orElseThrow(() -> new EntityNotFoundException(String.format("Can not find the entity property (%s = %s).", "id", id.toString())));
  }
  
  /**
   * get element using Criteria.
   *
   * @param spec    *
   * @param headers pagination data
   * @param sort    sort criteria
   * @return retrieve elements with pagination
   */
  public PagingResponse get(Specification<Property> spec, HttpHeaders headers, Sort sort) {
      if (isRequestPaged(headers)) {
          return get(spec, buildPageRequest(headers, sort));
      } else {
          List<Property> entities = get(spec, sort);
          return new PagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
      }
  }

  private boolean isRequestPaged(HttpHeaders headers) {
      return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
  }

  private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
      int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
      int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
      return PageRequest.of(page, size, sort);
  }

  /**
   * get elements using Criteria.
   *
   * @param spec     *
   * @param pageable pagination data
   * @return retrieve elements with pagination
   */
  public PagingResponse get(Specification<Property> spec, Pageable pageable) {
      Page<Property> page = propertyRepository.findAll(spec, pageable);
      List<Property> content = page.getContent();
      return new PagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
  }

  /**
   * get elements using Criteria.
   *
   * @param spec *
   * @return elements
   */
  public List<Property> get(Specification<Property> spec, Sort sort) {
      return propertyRepository.findAll(spec, sort);
  }

  /**
   * create element.
   *
   * @param item element to create
   * @return element after creation
   * //     * @throws CreateWithIdException
   * @throws EntityNotFoundException Exception
   */
  @Transactional
  public Property create(Property item) {
      return save(item);
  }

  /**
   * update element
   *
   * @param id   element identifier
   * @param item element to update
   * @return element after update
   * @throws EntityNotFoundException Exception when retrieve entity
   */
  @Transactional
  public Property update(Integer id, Property item) {
      if (item.getId() == null) {
          throw new RuntimeException("Can not update entity, entity without ID.");
      } else if (!id.equals(item.getId())) {
          throw new RuntimeException(String.format("Can not update entity, the resource ID (%d) not match the objet ID (%d).", id, item.getId()));
      }
      return save(item);
  }

  /**
   * create \ update elements
   *
   * @param item element to save
   * @return element after save
   */
  protected Property save(Property item) {
      return propertyRepository.save(item);
  }

}
