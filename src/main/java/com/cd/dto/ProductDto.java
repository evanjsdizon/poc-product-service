package com.cd.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductDto {

	@PositiveOrZero
	private int id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	@Positive
	@NotNull
	private BigDecimal price;
	
	@PositiveOrZero
	private int stock;

	public ProductDto() {
		super();
	}

	public ProductDto(@PositiveOrZero int id, @NotNull String name, @NotNull String description,
			@Positive BigDecimal price, @PositiveOrZero int stock) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
