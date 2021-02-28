package com.blo.model;

//This class is final to prevent it from being extended
public final class Constants {

	// constant ALLOWED_ORIGIN is public because we anticipate needing to access it
	// outside of our package
	public static final String ALLOWED_ORIGIN = "https://myclr.netlify.app";  //

	public static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

	// we've defined a private constructor so it can't be instantiated
	private Constants() {

	}
}
