package com.bridgeit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
		String userId = userModel.getId();
		ResponseError responseError = new ResponseError();
		List<ObjectError> list = result.getAllErrors();
		if (result.hasErrors()) {
			responseError.setStatus("registration unsuccessfull");
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
				
				consumer.sendMessage("tradefinancebridgelabz@gmail.com", userModel.getEmail(),url);
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

		System.out.println("email: " + email + " " + "pwd:" + password);

		if (userService.login(email, password)) {
			UserModel user = userService.getPersonByEmail(email);
			responseError.setStatus("login successfully");
			responseError.setStatusCode("200");
			responseError.setUsermodel(user);
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
		} else {
			responseError.setStatus("something wrong");
			responseError.setStatusCode("400");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/verifyKey", method = RequestMethod.POST)
	public void verifyUser(@RequestParam("token") String rabbitkey) {
		System.out.println("token");
		// consumer.sendMessage(rabbitkey);

	}

	@RequestMapping(value = "/userToken/{token}", method = RequestMethod.POST)
	public void userToken(@PathVariable("token") String token, UserModel userModel) throws IOException {
		// userService.userReg(userModel);
		// token.createVerificationToken()
		System.out.println(token);

	}

}
