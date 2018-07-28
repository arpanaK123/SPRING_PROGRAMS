package com.bridgeit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.exception.ErrorDetails;
import com.bridgeit.model.UserModel;
import com.bridgeit.service.UserService;

@RestController
@ControllerAdvice
public class UserController {

	@Autowired
	private UserService userService;
	
	ErrorDetails errorDetails;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<UserModel> registrationUser(@Valid @RequestBody UserModel userModel, BindingResult result) {
		List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		System.out.println(result);
		if (result.hasErrors()) {
			
        List<ObjectError> list=   result.getAllErrors();
        System.out.println(list);
             
			return new ResponseEntity<UserModel>(HttpStatus.BAD_REQUEST);

		}
		System.out.println(userModel);

		userService.callToUserdDAO(userModel);

		return new ResponseEntity<UserModel>(HttpStatus.OK);

	}

	// @RequestMapping(value = "/registration", method = RequestMethod.POST)
	// public String registrationUser(@Valid @RequestBody UserModel userModel,
	// BindingResult result,
	// HttpServletRequest request, HttpServletResponse response) {
	//
	// if (result.hasErrors()) {
	// return "error";
	// }
	// System.out.println(userModel);
	//
	// userService.callToUserdDAO(userModel);
	//
	// return "home";
	//
	// }

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(@RequestBody @Valid UserModel userModel, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(userModel);
		userService.callToUserdDAO(userModel);

		return "login";

	}

}
