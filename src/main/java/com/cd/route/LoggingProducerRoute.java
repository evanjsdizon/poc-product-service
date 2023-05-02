package com.cd.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class LoggingProducerRoute extends RouteBuilder {

	private static final String ENDPOINT = "kafka:%s?"
			+ "brokers=%s:%s";
	
	@Value("${app.kafka.topic}")
	private String topic;
	
	@Value("${app.kafka.host}")
	private String host;
	
	@Value("${app.kafka.port}")
	private int port;
	
	@Override
	public void configure() throws Exception {
		var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		var dataFormat = new JacksonDataFormat();
		dataFormat.setObjectMapper(objectMapper);
		
		from("direct:addLog")
		.marshal(dataFormat)
		.log("Logging Producer: ${body}")
		.to(String.format(ENDPOINT, topic, host, port));
	}

}
