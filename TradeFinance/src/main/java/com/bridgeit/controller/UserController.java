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

				consumer.sendMessage("tradefinancebridgelabz@gmail.com", userModel.getEmail(), url);
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

		if (userService.checkUserVerified(email)) {
			responseError.setStatus("user is not active");
			responseError.setStatusCode("500");
		}
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

		// return ResponseEntity<ResponseError>;
	}

//	@SuppressWarnings("unused")
//	@RequestMapping(value = "/login/{authentication_key}", method = RequestMethod.POST)
//	public ResponseEntity<ResponseError> userLoginCkeck(@RequestParam("email") String email,
//			String authentication_key) {
//		ResponseError responseError = new ResponseError();
//		UserModel user1 = new UserModel();
//		user1 = null;
//		if (user1 != null) {
//			UserModel user = userService.getPersonByEmail(email);
//			responseError.setStatus("login successfull");
//			responseError.setStatusCode("200");
//			responseError.setUsermodel(user);
//			return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
//		}
//		responseError.setStatus("user is not active");
//		responseError.setStatusCode("500");
//		return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
//
//
//	}

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

	@RequestMapping(value = "/resetPassword/{authentication_key}", method = RequestMethod.POST)
	public ResponseError resetPassword(@PathVariable("authentication_key") String authentication_key,
			HttpServletRequest request) {
		ResponseError responseError = new ResponseError();
		
        String user=((UserModel) request).getAuthentication_key();
		boolean status = userService.userCheckByKey(authentication_key) != null;
		if (status) {

			responseError.setStatus("your password is reset");
			responseError.setStatusCode("200");
			// responseError.setUsermodel(userModel);
		} else {
			responseError.setStatusCode("something wrong");
			responseError.setStatusCode("500");
		}
		return responseError;

	}

}
