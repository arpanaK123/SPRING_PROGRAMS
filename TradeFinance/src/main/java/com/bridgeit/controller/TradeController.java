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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.Utility.ResponseError;
import com.bridgeit.model.UserModel;
import com.bridgeit.service.TradeService;

@RestController
@ControllerAdvice
public class TradeController {

//	@Autowired
//	TradeService tradeService;
//	
//	@RequestMapping(value = "/registration", method = RequestMethod.POST)
//	public ResponseEntity<ResponseError> registrationUser(@Valid @RequestBody UserModel userModel, BindingResult result,
//			HttpServletRequest request) throws IOException {
//		System.out.println("error: " + result);
//		String userId = userModel.getId();
//		ResponseError responseError = new ResponseError();
//		List<ObjectError> list = result.getAllErrors();
//		if (result.hasErrors()) {
//			responseError.setStatus("please enter valid input,registration unsuccessfull");
//			responseError.setStatusCode("400");
//			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
//
//		}
//		try {
//			boolean userreg = tradeService.userReg(userModel);
//			if (userreg != true) {
//
//				responseError.setStatus("user already exit ");
//				responseError.setStatusCode("500");
//			} else {
////				String url = request.getRequestURL().toString();
////				url = url.substring(0, url.lastIndexOf("/")) + "/" + "userToken/" + userModel.getAuthentication_key();
////				producer.sendMessage("tradefinancebridgelabz@gmail.com", userModel.getEmail(), url);
//				responseError.setStatus("registration successfull");
//				responseError.setStatusCode("200");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
//	}
}
