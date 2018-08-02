package com.bridgeit.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.Utility.Consumer;
import com.bridgeit.Utility.Producer;
import com.bridgeit.Utility.ResponseError;
import com.bridgeit.model.UserModel;
import com.bridgeit.service.UserService;

@RestController
@ControllerAdvice
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private Producer producer;

	@Autowired
	private Consumer consumer;
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<ResponseError> registrationUser(@Valid @RequestBody UserModel userModel,
			BindingResult result) {
		ResponseError responseError = new ResponseError();
		System.out.println(userModel);
		System.out.println(result);
		List<ObjectError> list = result.getAllErrors();
		System.out.println(list + "------");
		if (result.hasErrors()) {
			responseError.setStatus("registration unsuccessfull");
			responseError.setErrorCode("400");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);

		}
		userService.userReg(userModel);
		responseError.setStatus("registration successfull");
		responseError.setStatusCode("200");

		return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseError> userLogin(@RequestParam("email") String email,
			@RequestParam("password") String password) {

		ResponseError responseError = new ResponseError();

		System.out.println("email: " + email + " " + "pwd:" + password);
		if (userService.login(email, password)) {
			responseError.setStatus("login successfully");
			responseError.setStatusCode("200");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
		} else {
			responseError.setStatus("something wrong");
			responseError.setErrorCode("400");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/rabbitkey", method = RequestMethod.POST)
	public void verifyUser(@RequestParam("token") String rabbitkey) {
		System.out.println("token");
		consumer.sendMessage(rabbitkey);

	}
	
	@RequestMapping(value="/generateToken/{key}",method = RequestMethod.POST)
	public void userToken(@RequestParam("key") String key) {
		
		
	}

}
