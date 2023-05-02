package com.cd.service;

import java.util.List;

import com.cd.dto.ProductDto;
import com.cd.exception.ProductNotFoundException;

public interface ProductService {

	List<ProductDto> getAllProducts();
	
	ProductDto getProduct(int id) throws ProductNotFoundException;
	
	ProductDto addProduct(ProductDto productDto);
	
	ProductDto updateProduct(ProductDto productDto) throws ProductNotFoundException;
}
