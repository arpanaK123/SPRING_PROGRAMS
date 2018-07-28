package com.bridgeit.Utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Response extends RuntimeException{

	public Response(String exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}
	

}
