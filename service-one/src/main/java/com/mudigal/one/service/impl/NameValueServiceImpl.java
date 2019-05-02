package com.mudigal.one.service.impl;

import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mudigal.one.dao.NameValueDao;
import com.mudigal.one.domain.NameValue;
import com.mudigal.one.model.AllNameValueTO;
import com.mudigal.one.model.NameValueTO;
import com.mudigal.one.service.NameValueService;
import com.mudigal.one.service.impl.NameValueServiceImpl;

/**
 * @author Barış Can Akdağ
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
    private ServiceOneRabbitMessageProducer serviceOneRabbitMessageProducer;

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
            serviceOneRabbitMessageProducer.sendMessageToQueue(savedData);
        }
        return savedData;
    }

    private Random random = new Random();

    @Override
    public AllNameValueTO getAllNameValues(String name) {

        System.out.println("Gelen Name  : " + name + " " + "Application Name  :  " + applicationName);

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
        allNameValueTO.setOriginalName("Ürün Service");
        allNameValueTO.setOriginalValue(allNameValueTO.getRemainingNameValuePair().get("Ürün "));
        allNameValueTO.getRemainingNameValuePair().remove("Ürün ");
        return allNameValueTO;
    }

    @Override
    @Scheduled(fixedDelay = 5000)
    public NameValueTO generateUUID() {
        return generateUUID(applicationName);
    }


    private String[] prodcuts = {"Çicek", "Kalem", "Bilgisayar", "Parfum", "Çorap",
            "Motor", "Kulaklik", "Fare", "Sigara", "Gözlük",
            "Batarya"};

    @Override
    public NameValueTO generateUUID(String applicationName) {
        NameValueTO nameValueTO = new NameValueTO();
        nameValueTO.setName("Ürün ");
        Integer randomValue = random.nextInt(9);
        nameValueTO.setValue(prodcuts[randomValue]);
        logger.info("Saved Information: " + updateNameValue(nameValueTO));
        return nameValueTO;
    }

}
