package com.mudigal.three.service.impl;

import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mudigal.three.dao.NameValueDao;
import com.mudigal.three.domain.NameValue;
import com.mudigal.three.model.AllNameValueTO;
import com.mudigal.three.model.NameValueTO;
import com.mudigal.three.service.NameValueService;

/**
 * 
 * @author Barış Can Akdağ
 *
 */
@Service(value = "nameValueService")
public class NameValueServiceImpl implements NameValueService {

	private Logger logger = Logger.getLogger(NameValueServiceImpl.class);
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@Autowired
	private NameValueDao nameValueDao;

	@Autowired
	private ServiceThreeRabbitMessageProducer serviceThreeRabbitMessageProducer;

	@Override
	public NameValueTO updateNameValue(NameValueTO nameValueTO) {
		return updateNameValue(nameValueTO, false);
	}

	@Override
	public NameValueTO updateNameValue(NameValueTO nameValueTO, boolean fromRabbit) {
		logger.info("Saving: " + nameValueTO);
		NameValueTO savedData = dozerBeanMapper
				.map(nameValueDao.save(dozerBeanMapper.map(nameValueTO, NameValue.class)), NameValueTO.class);
		if (!fromRabbit) {
			serviceThreeRabbitMessageProducer.sendMessageToQueue(savedData);
		}
		return savedData;
	}

	@Override
	public AllNameValueTO getAllNameValues(String name) {
		System.out.println("Gelen Name  : " + name  + " " + "Application Name  :  " + applicationName);
		Iterable<NameValue> nameValues = nameValueDao.findAll();
		AllNameValueTO allNameValueTO = new AllNameValueTO();
		for (NameValue nameValue : nameValues) {
			if (nameValue.getName().equals(name)) {
				allNameValueTO.setOriginalName(nameValue.getName());
				allNameValueTO.setOriginalValue(nameValue.getValue());
			} else {
				allNameValueTO.getRemainingNameValuePair().put(nameValue.getName(), nameValue.getValue());
			}
		}

		System.out.println(allNameValueTO.toString());
		allNameValueTO.setOriginalName("Fiyat-Service");
		allNameValueTO.setOriginalValue(allNameValueTO.getRemainingNameValuePair().get("Fiyat"));
		allNameValueTO.getRemainingNameValuePair().remove("Fiyat");

		return allNameValueTO;
	}
	

	private Random random=new Random();
	@Override
	@Scheduled (fixedDelay= 5000)
	public NameValueTO generateUUID() {
		return generateUUID(applicationName);
	}

	@Override
	public NameValueTO generateUUID(String applicationName) {
		NameValueTO nameValueTO = new NameValueTO();
		Integer randomValue=random.nextInt(600);
		nameValueTO.setName("Fiyat");
		nameValueTO.setValue(randomValue.toString() + " TL");
		logger.info("Saved Information: " + updateNameValue(nameValueTO));
		return nameValueTO;
	}

}
