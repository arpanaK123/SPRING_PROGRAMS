package com.bridgeit.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bridgeit.Utility.ResponseError;
import com.bridgeit.model.UserModel;
import com.bridgeit.service.UserService;

@RestController
@ControllerAdvice
public class UserController {

	@Autowired
	private UserService userService;

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

	@RequestMapping(value = "/VerifiedEmail/{token}", method = RequestMethod.GET)
	public String verifyUser(@PathVariable("token") String token, UserModel userModel,
			RedirectAttributes redirectAttributes) {
		return token;

		// Optional<UserModel> user=
		// return token;

	}

}
