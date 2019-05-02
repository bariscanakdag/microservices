package com.microservice.three.service;

import com.microservice.three.model.AllNameValueTO;
import com.microservice.three.model.NameValueTO;

/**
 * 
 * @author Barış Can Akdağ
 *
 */
public interface NameValueService {

	NameValueTO updateNameValue(NameValueTO nameValueTO);

	AllNameValueTO getAllNameValues(String name);

	NameValueTO updateNameValue(NameValueTO nameValueTO, boolean fromRabbit);

	NameValueTO generateUUID();

	NameValueTO generateUUID(String applicationName);

}
