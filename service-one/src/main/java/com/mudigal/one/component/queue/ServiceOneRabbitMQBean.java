package com.mudigal.one.component.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.logging.Logger;

/**
 * RabbitMQ bean configurations
 * 
 * @author Barış Can Akdağ
 *
 */
@Configuration
public class ServiceOneRabbitMQBean implements RabbitListenerConfigurer {

	Logger logger=Logger.getLogger(getClass().getName());

	public final static String queueName = "com.mudigal.microservices-sample.service-one";
    public final static String exchangeName = "com.mudigal.microservices-sample.services-exchange";
    public final static String routingKeyName = "com.mudigal.microservices-sample.service-*";
	
    @Bean
    Queue queue() {

		logger.info("geldiiiii");return new Queue(queueName, true);
    }

    @Bean
    TopicExchange exchange() {
		logger.info("geldiiiii");
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
		logger.info("geldiiiii");

        return BindingBuilder.bind(queue).to(exchange).with(routingKeyName);
    }

    @Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		logger.info("geldiiiii");
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		logger.info("geldiiiii");
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
    	logger.info("geldiiiii");
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		logger.info("geldiiiii");
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		logger.info("geldiiiii");
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

}
