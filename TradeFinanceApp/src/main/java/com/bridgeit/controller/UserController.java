package com.bridgeit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgeit.model.UserModel;
import com.bridgeit.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationUser(@RequestBody UserModel userModel, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(userModel);
		userService.callToUserdDAO(userModel);

		return "home";

	}

}
