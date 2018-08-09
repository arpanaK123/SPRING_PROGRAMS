package com.bridgeit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

import com.bridgeit.Utility.Consumer;
import com.bridgeit.Utility.GenerateTokens;
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
	private GenerateTokens token;
	@Autowired
	private Consumer consumer;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<ResponseError> registrationUser(@Valid @RequestBody UserModel userModel, BindingResult result,
			HttpServletRequest request) throws IOException {
		System.out.println("error: " + result);
		String userId = userModel.getId();
		ResponseError responseError = new ResponseError();
		List<ObjectError> list = result.getAllErrors();
		if (result.hasErrors()) {
			responseError.setStatus("please enter valid input,registration unsuccessfull");
			responseError.setStatusCode("400");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);

		}
		try {
			boolean userreg = userService.userReg(userModel);
			if (userreg != true) {

				responseError.setStatus("user already exit ");
				responseError.setStatusCode("500");
			} else {
				String url = request.getRequestURL().toString();
				url = url.substring(0, url.lastIndexOf("/")) + "/" + "userToken/" + userModel.getAuthentication_key();
				producer.sendMessage("tradefinancebridgelabz@gmail.com", userModel.getEmail(), url);
				responseError.setStatus("registration successfull");
				responseError.setStatusCode("200");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseError> userLogin(@RequestParam("email") String email,
			@RequestParam("password") String password) {

		ResponseError responseError = new ResponseError();
		// UserModel user1 = new UserModel();
		System.out.println("email: " + email + " " + "pwd:" + password);

		if (!userService.isVerifiedUser(email)) {
			responseError.setStatus("user is not active");
			responseError.setStatusCode("500");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (userService.login(email, password)) {
			UserModel user = userService.getPersonByEmail(email);
			String generateToken = userService.getToken(user);
			responseError.setStatus("login successfully");
			responseError.setStatusCode("200");
			responseError.setUsermodel(user);
			responseError.setGenerateTokens(generateToken);
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
		} else {
			responseError.setStatus("something wrong");
			responseError.setStatusCode("400");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/userToken/{authentication_key:.+}", method = RequestMethod.GET)
	public ResponseError userToken(@PathVariable("authentication_key") String authentication_key) throws IOException {

		ResponseError responseError = new ResponseError();

		UserModel userModel = userService.getUserByUniqueKey(authentication_key);

		if (userModel != null) {

			userModel.setVerified(true);
			userService.update(userModel);
			responseError.setStatus("user is active");
			responseError.setStatusCode("200");
		} else {
			responseError.setStatus("user is In_Activate");
			responseError.setStatusCode("500");
		}
		return responseError;

	}

	@RequestMapping(value = "/forgotPasword", method = RequestMethod.POST)
	public ResponseEntity<ResponseError> userResetPassword(@RequestParam("email") String email,
			HttpServletRequest request) {
		System.out.println("email----" + email);
		UserModel user = userService.getPersonByEmail(email);
		ResponseError responseError = new ResponseError();
		if (user != null) {
			System.out.println("------------------");
			String url = request.getRequestURL().toString();
			url = url.substring(0, url.lastIndexOf("/")) + "/" + "resetPassword/" + user.getAuthentication_key();
			producer.sendMessage("tradefinancebridgelabz@gmail.com", user.getEmail(), url);
			responseError.setStatus("plz check your mail and verify to change password");
			responseError.setStatusCode("200");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);

		} else {
			responseError.setStatus("enter email is not correct");
			responseError.setStatusCode("400");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);

		}
	}

	@RequestMapping(value = "/resetPassword/{authentication_key:.+}", method = RequestMethod.GET)
	public ResponseEntity<ResponseError> resetpassword(@PathVariable("authentication_key") String authentication_key,
			HttpSession session) throws IOException {

		ResponseError responseError = new ResponseError();
		UserModel user = userService.getUserByUniqueKey(authentication_key);

		if (user != null) {
			responseError.setStatus("you can set your password");
			responseError.setStatusCode("200");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
		} else {
			responseError.setStatus("something wrong , link cant be valid");
			responseError.setStatusCode("400");

			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseError> resetPassword(@RequestParam("password") String password,@RequestParam("authentication_key") String authentication_key) throws IOException {
		System.out.println("----------------");
		ResponseError responseError = new ResponseError();
		userService.userChangePassword(authentication_key, password);
		responseError.setStatus("password changed");
		responseError.setStatusCode("200");
		return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);

	}
	// @RequestMapping(value = "/resetPassword/{authentication_key:.+}", method =
	// RequestMethod.GET)
	// public ResponseEntity<ResponseError>
	// resetPassword(@PathVariable("authentication_key") String authentication_key,
	// @RequestParam("password") String password) throws IOException {
	//
	// ResponseError responseError = new ResponseError();
	// UserModel user= userService.getUserByUniqueKey(authentication_key);
	//
	// if (userService.userChangePassword(authentication_key, password)) {
	// responseError.setStatus("password changed");
	// responseError.setStatusCode("200");
	// return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
	// } else {
	// responseError.setStatus("something wrong password cant be change");
	// responseError.setStatusCode("400");
	//
	// return new ResponseEntity<ResponseError>(responseError,
	// HttpStatus.BAD_REQUEST);
	// }
	//

}
