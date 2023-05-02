package com.cd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cd.dto.ProductDto;
import com.cd.exception.ProductNotFoundException;
import com.cd.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto> getProduct(@PathVariable int id) throws ProductNotFoundException {
		return ResponseEntity.ok(productService.getProduct(id));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductDto productDto) {
		var product = productService.addProduct(productDto);
		var location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id})").buildAndExpand(product.getId()).toUri();
		
		return ResponseEntity.created(location).body(product);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto) throws ProductNotFoundException {
		return ResponseEntity.ok(productService.updateProduct(productDto));
	}
}
