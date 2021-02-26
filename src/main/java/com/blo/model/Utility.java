package com.blo.model;

import javax.servlet.http.HttpServletRequest;

public class Utility {

	 public static String getSiteURL(HttpServletRequest request) {
		 /* commented out cos siteURL returns "http://localhost:8080"  */
	        //String siteURL = request.getRequestURL().toString();
	        //return siteURL.replace(request.getServletPath(), "");
		
		 return Constants.ALLOWED_ORIGIN;
	    }
	
}
