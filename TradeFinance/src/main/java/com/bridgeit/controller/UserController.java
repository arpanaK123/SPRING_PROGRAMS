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

import com.bridgeit.Utility.Response;
import com.bridgeit.model.UserModel;
import com.bridgeit.service.UserService;

@RestController
@ControllerAdvice
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<UserModel> registrationUser(@Valid @RequestBody UserModel userModel, BindingResult result) {
		System.out.println(userModel);
		System.out.println(result);
		List<ObjectError> list = result.getAllErrors();
		System.out.println(list + "------");
		if (result.hasErrors()) {
			return new ResponseEntity<UserModel>(userModel, HttpStatus.BAD_REQUEST);
		}
		userService.userReg(userModel);
		return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> userLogin(@RequestParam("email") String email,
			@RequestParam("password") String password) {
		System.out.println("email: " + email + " " + "pwd:" + password);
		if (userService.login(email, password)) {
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/VerifiedEmail/{token}", method = RequestMethod.GET)
	public String verifyUser(@PathVariable("token") String token, UserModel userModel,
			RedirectAttributes redirectAttributes) {
				return token;

		//Optional<UserModel> user=
	//	return token;

	}

}
