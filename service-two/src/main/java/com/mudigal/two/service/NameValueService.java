package com.mudigal.two.service;

import com.mudigal.two.model.AllNameValueTO;
import com.mudigal.two.model.NameValueTO;

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
