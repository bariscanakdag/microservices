package com.microservice.two.dao;

import org.springframework.data.repository.CrudRepository;
import com.microservice.two.domain.NameValue;

/**
 * 
 * @author Barış Can Akdağ
 *
 */
public interface NameValueDao extends CrudRepository<NameValue, Long> {

}
