package com.blog.exceptions;

public class UserNameNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	String fieldValue;

	public UserNameNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s %s %s is incorrect.", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
