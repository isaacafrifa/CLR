package com.blo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenericResponse {
	private String message;
    private String error;
 
    public GenericResponse(String message) {
        super();
        this.message = message;
    }
 
}
