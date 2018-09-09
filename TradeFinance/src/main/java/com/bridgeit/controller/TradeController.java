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
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.Utility.ContractResponse;
import com.bridgeit.Utility.ResponseAccount;
import com.bridgeit.Utility.ResponseError;
import com.bridgeit.model.TradeContractModel;
import com.bridgeit.model.UserModel;
import com.bridgeit.model.Usermodel;
import com.bridgeit.service.TradeService;
import com.bridgeit.service.UserService;

public class TradeController {

//	@Autowired
//	UserService userService;
//
//	@RequestMapping(value = "/userRegistrationAccount", method = RequestMethod.POST)
//	public ResponseEntity<ResponseError> registrationUser(@Valid @RequestBody UserModel userModel, BindingResult result,
//			HttpServletRequest request) throws IOException {
//		System.out.println("error: " + result);
//		String userId = userModel.getId();
//		ResponseError responseError = new ResponseError();
//		List<ObjectError> list = result.getAllErrors();
//		if (result.hasErrors()) {
//			responseError.setStatus("please enter valid input,registration Un_successfull");
//			responseError.setStatusCode("400");
//			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
//
//		}
//		try {
//			boolean userreg = userService.userReg(userModel);
//			if (userreg != true) {
//
//				responseError.setStatus("user already exit ");
//				responseError.setStatusCode("500");
//			} else {
//
//				// String url = request.getRequestURL().toString();
//				// url = url.substring(0, url.lastIndexOf("/")) + "/" + "userToken/" +
//				// userModel.getAuthentication_key();
//				// producer.sendMessage("tradefinancebridgelabz@gmail.com",
//				// userModel.getEmail(), url);
//				responseError.setStatus("registration successfull");
//				responseError.setStatusCode("200");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/getAccountBy{accountnumber}", method = RequestMethod.GET)
//	public ResponseEntity<ResponseAccount> getAccountBy(@Valid @PathVariable("accountnumber") String accountnumber,
//			@RequestBody UserModel userModel, BindingResult result, HttpServletRequest request) throws IOException {
//		System.out.println("error: " + result);
//		// String userId = userModel.getId();
//		ResponseAccount responseError = new ResponseAccount();
//		List<ObjectError> list = result.getAllErrors();
//		if (result.hasErrors()) {
//			responseError.setStatus("please enter valid input");
//			responseError.setStatusCode("400");
//			return new ResponseEntity<ResponseAccount>(responseError, HttpStatus.BAD_REQUEST);
//
//		}
//		try {
//			boolean userreg = userService.getAccountBy(accountnumber);
//			if (userreg != true) {
//
//				responseError.setStatus("user already exit ");
//				responseError.setStatusCode("500");
//			} else {
//				// UserModel user = userService.getPersonByEmail(userModel.getEmail());
//
//				responseError.setTradeAccountModel(accountnumber);
//				responseError.setStatus(" successfull");
//				responseError.setStatusCode("200");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<ResponseAccount>(responseError, HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/getBalanceBy{accountnumber}", method = RequestMethod.GET)
//	public ResponseEntity<ResponseAccount> getBalanceBy(@Valid @PathVariable("accountnumber") String accountnumber,
//			BindingResult result, HttpServletRequest request) throws IOException {
//		System.out.println("error: " + result);
//		// String userId = userModel.getId();
//		ResponseAccount responseError = new ResponseAccount();
//		List<ObjectError> list = result.getAllErrors();
//		if (result.hasErrors()) {
//			responseError.setStatus("please enter valid input");
//			responseError.setStatusCode("400");
//			return new ResponseEntity<ResponseAccount>(responseError, HttpStatus.BAD_REQUEST);
//
//		}
//		try {
//			int userreg = userService.getUserBalance(accountnumber);
//			if (userreg == 0) {
//
//				responseError.setStatus("user already exit ");
//				responseError.setStatusCode("500");
//			} else {
//				responseError.setStatusCode(accountnumber);
//				responseError.setStatus("registration successfull");
//				responseError.setStatusCode("200");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<ResponseAccount>(responseError, HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/createContract", method = RequestMethod.POST)
//	public ResponseEntity<ResponseError> createContract(@Valid @RequestBody TradeContractModel contractModel,
//			BindingResult result, HttpServletRequest request) throws IOException {
//		System.out.println("error: " + result);
//		ResponseError responseError = new ResponseError();
//		List<ObjectError> list = result.getAllErrors();
//		if (result.hasErrors()) {
//			responseError.setStatus("please enter valid input,registration unsuccessfull");
//			responseError.setStatusCode("400");
//			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
//
//		}
//		try {
//			 userService.createContract(contractModel);
//			responseError.setStatus("contract created successfull");
//			responseError.setStatusCode("200");
//			// }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/getContractBy{contractId}", method = RequestMethod.GET)
//	public ResponseEntity<ContractResponse> getContractBy(@Valid @PathVariable("contractId") String contractId,
//			TradeContractModel contractModel, BindingResult result, HttpServletRequest request) throws IOException {
//		System.out.println("error: " + result);
//		// String userId = userModel.getId();
//		ContractResponse contractResponse = new ContractResponse();
//		List<ObjectError> list = result.getAllErrors();
//		if (result.hasErrors()) {
//			contractResponse.setStatus("please enter valid input");
//			contractResponse.setStatusCode("400");
//			return new ResponseEntity<ContractResponse>(contractResponse, HttpStatus.BAD_REQUEST);
//
//		}
//		try {
//			boolean userreg = userService.getContractByContractId(contractId);
//			if (userreg == true) {
//
//				contractResponse.setStatus("user already exit ");
//				contractResponse.setStatusCode("500");
//			} else {
//				// responseError.setStatus(contractModel);
//				contractResponse.setStatus("registration successfull");
//				contractResponse.setStatusCode("200");
//				contractResponse.setContractModel(contractModel);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<ContractResponse>(contractResponse, HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/deleteContractBy{contractId}", method = RequestMethod.POST)
//	public ResponseEntity<ResponseAccount> deleteContractBy(@Valid @PathVariable("contractId") String contractId,
//			TradeContractModel contractModel, BindingResult result, HttpServletRequest request) throws IOException {
//		System.out.println("error: " + result);
//		// String userId = userModel.getId();
//		ResponseAccount responseError = new ResponseAccount();
//		List<ObjectError> list = result.getAllErrors();
//		if (result.hasErrors()) {
//			responseError.setStatus("please enter valid input");
//			responseError.setStatusCode("400");
//			return new ResponseEntity<ResponseAccount>(responseError, HttpStatus.BAD_REQUEST);
//
//		}
//		try {
//			boolean userreg = userService.getContractByContractId(contractId);
//			if (userreg == true) {
//
//				responseError.setStatus("user already exit ");
//				responseError.setStatusCode("500");
//			} else {
//				// responseError.setStatus(contractModel);
//				responseError.setStatus(" successfull");
//				responseError.setStatusCode("200");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<ResponseAccount>(responseError, HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/updateContractBy{contractId}", method = RequestMethod.POST)
//	public ResponseEntity<ResponseAccount> updateContractBy(@Valid @PathVariable("contractId") String contractId,
//			TradeContractModel contractModel, BindingResult result, HttpServletRequest request) throws IOException {
//		System.out.println("error: " + result);
//		// String userId = userModel.getId();
//		ResponseAccount responseError = new ResponseAccount();
//		List<ObjectError> list = result.getAllErrors();
//		if (result.hasErrors()) {
//			responseError.setStatus("please enter valid input");
//			responseError.setStatusCode("400");
//			return new ResponseEntity<ResponseAccount>(responseError, HttpStatus.BAD_REQUEST);
//
//		}
//		try {
//			boolean userreg = userService.UpdateContractByContractId(contractId);
//			if (userreg == true) {
//
//				responseError.setStatus("user already exit ");
//				responseError.setStatusCode("500");
//			} else {
//				// responseError.setStatus(contractModel);
//				responseError.setStatus("successfull");
//				responseError.setStatusCode("200");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<ResponseAccount>(responseError, HttpStatus.OK);
//	}

}
