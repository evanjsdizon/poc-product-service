package com.cd.exception;

public class ProductNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -925431074969124950L;

	public ProductNotFoundException() {
		super("Product cannot be found.");
	}
	
	public ProductNotFoundException(int id) {
		super(String.format("Product with id=[%s] cannot be found.", id));
	}

	public ProductNotFoundException(String message) {
		super(message);
	}

}
