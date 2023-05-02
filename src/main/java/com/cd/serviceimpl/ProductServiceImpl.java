package com.cd.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cd.dto.LogDto;
import com.cd.dto.LogLevel;
import com.cd.dto.ProductDto;
import com.cd.exception.ProductNotFoundException;
import com.cd.repository.ProductRepository;
import com.cd.service.ProductService;
import com.cd.util.ProductTransformer;

@Service
public class ProductServiceImpl implements ProductService {

	private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Produce("direct:addLog")
	private ProducerTemplate loggingProducer;
	
	@Override
	public List<ProductDto> getAllProducts() {
		var products = productRepository.findAll().stream().map(ProductTransformer::toProductDto).toList();
		sendInfoLog("Successfully returned all products");
		return products;
	}

	@Override
	public ProductDto getProduct(int id) throws ProductNotFoundException {
		var product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
		sendInfoLog(String.format("Successfully returned product with id=[%s]", product.getId()));
		return ProductTransformer.toProductDto(product);
	}

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		var product = productRepository.save(ProductTransformer.toProduct(productDto));
		sendInfoLog(String.format("Successfully added product with id=[%s]", product.getId()));
		return ProductTransformer.toProductDto(product);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto) throws ProductNotFoundException {
		getProduct(productDto.getId());
		var product = productRepository.save(ProductTransformer.toProduct(productDto));
		sendInfoLog(String.format("Successfully updated product with id=[%s]", product.getId()));
		return ProductTransformer.toProductDto(product);
	}

	private void sendInfoLog(String msg) {
		log.info(msg);
		loggingProducer.sendBody(new LogDto(LocalDateTime.now(), LogLevel.INFO, msg));
	}
}
