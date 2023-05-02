package com.cd.util;

import com.cd.dto.ProductDto;
import com.cd.entity.Product;

public class ProductTransformer {

	private ProductTransformer() {}
	
	public static ProductDto toProductDto(Product product) {
		if (product == null) return null;
		
		return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock());
	}
	
	public static Product toProduct(ProductDto dto) {
		if (dto == null) return null;
		
		if (dto.getId() > 0) {
			return new Product(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock());
		}
		return new Product(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock());
	}
}
