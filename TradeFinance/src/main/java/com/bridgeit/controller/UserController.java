package com.bridgeit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
@RequestMapping(value="/registration",method=RequestMethod.POST)

public ResponseEntity<?> registrationUser(HttpServletRequest request,HttpServletResponse response)
{

	return new ResponseEntity<Object>(HttpStatus.OK);
	
}


}
