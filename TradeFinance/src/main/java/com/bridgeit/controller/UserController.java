package com.bridgeit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.Utility.BalanceResponce;
import com.bridgeit.Utility.Consumer;
import com.bridgeit.Utility.ContractResponse;
import com.bridgeit.Utility.GenerateTokens;
import com.bridgeit.Utility.Producer;
import com.bridgeit.Utility.ResponseError;
import com.bridgeit.model.ContractId;
import com.bridgeit.model.ContractLists;
import com.bridgeit.model.TradeContractModel;
import com.bridgeit.model.UserModel;
import com.bridgeit.model.Usermodel;
import com.bridgeit.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
				// String url = request.getRequestURL().toString();
				// url = url.substring(0, url.lastIndexOf("/")) + "/" + "userToken/" +
				// userModel.getAuthentication_key();
				// producer.sendMessage("tradefinancebridgelabz@gmail.com",
				// userModel.getEmail(), url);
				responseError.setStatus("registration successfull");
				responseError.setStatusCode("200");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseError> userLogin(@RequestBody Usermodel userModel) {

		ResponseError responseError = new ResponseError();
		// UserModel user1 = new UserModel();
		System.out.println("email: " + userModel.getEmail() + " " + "pwd:" + userModel.getPassword());

		if (!userService.isVerifiedUser(userModel.getEmail())) {
			responseError.setStatus("user is not active");
			responseError.setStatusCode("500");
			return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
		} else if (userService.login(userModel.getEmail(), userModel.getPassword())) {
			UserModel user = userService.getPersonByEmail(userModel.getEmail());
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
	public ResponseError userToken(@PathVariable("authentication_key") String authentication_key,
			HttpServletResponse response) throws IOException {

		ResponseError responseError = new ResponseError();

		UserModel userModel = userService.getUserByUniqueKey(authentication_key);

		if (userModel != null) {

			userModel.setVerified(true);
			userService.update(userModel);
			responseError.setStatus("user is active");
			responseError.setStatusCode("200");
			// response.sendRedirect("http://localhost:8080/tradeFinance/#!/login");
			response.sendRedirect("http://127.0.0.1:46223/#!/login");

		} else {
			responseError.setStatus("user is In_Activate");
			responseError.setStatusCode("500");
		}
		return responseError;

	}

	@RequestMapping(value = "/forgotPasword", method = RequestMethod.POST)
	public ResponseEntity<ResponseError> userResetPassword(@RequestBody Usermodel userModel,
			HttpServletRequest request) {
		System.out.println("email----" + userModel.getEmail());
		UserModel user = userService.getPersonByEmail(userModel.getEmail());
		ResponseError responseError = new ResponseError();
		if (user != null) {
			System.out.println("------------------");
			String url = request.getRequestURL().toString();
			// url = url.substring(0, url.lastIndexOf("/")) + "/" + "resetPassword/" +
			// user.getAuthentication_key();
			url = "http://127.0.0.1:34280/#!/resetPassword" + "/" + user.getAuthentication_key();
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
			HttpServletResponse response) throws IOException {

		ResponseError responseError = new ResponseError();
		UserModel user = userService.getUserByUniqueKey(authentication_key);

		if (user != null) {
			response.sendRedirect("http://127.0.0.1:34280/#!/resetPassword");
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
	public ResponseEntity<ResponseError> resetPassword(@RequestBody UserModel userModel) throws IOException {
		System.out.println("----------------");
		System.out.println("usermodel: " + userModel.getPassword());
		ResponseError responseError = new ResponseError();
		String key = userModel.getAuthentication_key();
		System.out.println("key-----> " + key);
		userService.userChangePassword(userModel.getPassword(), key);

		userService.userChangePassword(key, userModel.getPassword());
		responseError.setStatus("password changed");
		responseError.setStatusCode("200");
		return new ResponseEntity<ResponseError>(responseError, HttpStatus.OK);
	}

	@RequestMapping(value = "/createContract", method = RequestMethod.POST)
	public ResponseEntity<ContractResponse> createContract(@RequestBody TradeContractModel contract) {
		System.out.println("contract: " + contract);
		ContractResponse response = new ContractResponse();
		boolean saved = userService.createContract(contract);

		if (saved) {
			response.setStatusCode("200");
			response.setStatus("success");
			response.setContractModel(contract);
			return new ResponseEntity<ContractResponse>(response, HttpStatus.OK);
		}
		response.setStatusCode("400");
		response.setStatus("something wrong");
		return new ResponseEntity<ContractResponse>(response, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/createContractToken", method = RequestMethod.POST)
	public ResponseEntity<ContractResponse> createContract(@RequestBody TradeContractModel contract,
			@RequestHeader("token") String jwtToken) {
		System.out.println("token:"+token);
		ContractResponse response = new ContractResponse();

		boolean saved = userService.createContract(contract, jwtToken);

		if (saved) {
			response.setStatusCode("200");
			response.setStatus("success");
			response.setContractModel(contract);
			return new ResponseEntity<ContractResponse>(response, HttpStatus.OK);
		}
		response.setStatusCode("400");
		response.setStatus("something wrong");
		return new ResponseEntity<ContractResponse>(response, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/getContractByContractId", method = RequestMethod.POST)
	public ResponseEntity<ContractResponse> getContractBy(@RequestBody TradeContractModel contractModel) {
		System.out.println("contract: " + contractModel);
		ContractResponse response = new ContractResponse();
		TradeContractModel tradeContractModel = userService.getContractByContractId(contractModel.getContractId());

		if (tradeContractModel != null) {
			response.setStatusCode("200");
			response.setStatus("success");
			response.setContractModel(tradeContractModel);
			return new ResponseEntity<ContractResponse>(response, HttpStatus.OK);
		}
		response.setStatusCode("400");
		response.setStatus("something wrong");
		return new ResponseEntity<ContractResponse>(response, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/getContractByToken", method = RequestMethod.POST)
	public ResponseEntity<ContractResponse> getContract(@RequestBody ContractId contractId,
			@RequestHeader("token") String jwtToken) throws InvalidArgumentException, JsonParseException, JsonMappingException, ProposalException, IOException {
		System.out.println("token "+jwtToken);
System.out.println("contract_id"+contractId.getContractId());
		TradeContractModel contract = userService.getContractFromBlockChain(contractId.getContractId(), jwtToken);
		System.out.println("contract"+contract);
		ContractResponse response = new ContractResponse();
		if (contract == null) {
			response.setStatusCode("400");
			response.setStatus("something wrong");
			return new ResponseEntity<ContractResponse>(response, HttpStatus.BAD_REQUEST);
		}
		response.setStatusCode("200");
		response.setStatus("success");
		response.setContractModel(contract);
		return new ResponseEntity<ContractResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateContractByToken", method = RequestMethod.POST)
	public ResponseEntity<ContractResponse> updateContract(@RequestBody TradeContractModel contract,
			@RequestHeader("token") String jwtToken) throws InvalidArgumentException {
		System.out.println("token"+token);
		ContractResponse response = new ContractResponse();
		boolean saved = userService.updateContract(jwtToken, contract);

		if (saved) {
			response.setStatus("success");
			response.setStatusCode("200");
			response.setContractModel(contract);
			TradeContractModel contractResponse = userService.getContractResponse(contract.getContractId());
			response.setContractModel(contractResponse);
			return new ResponseEntity<ContractResponse>(response, HttpStatus.OK);
		}
		response.setStatusCode("400");
		response.setStatus("something wrong");
		return new ResponseEntity<ContractResponse>(response, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/getBalance", method = RequestMethod.POST)
	public ResponseEntity<BalanceResponce> getBalance(@RequestHeader("token") String token) {

		BalanceResponce response = new BalanceResponce();
		int balance = userService.getUserBalance(token);
		if (balance != -1) {
			//response.setAccountNumber(token);
			response.setBalance(balance);
			return new ResponseEntity<BalanceResponce>(response, HttpStatus.OK);
		}

		//response.setAccountNumber(accountNumber);
		response.setBalance(balance);
		return new ResponseEntity<BalanceResponce>(response, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getAllContractBytoken", method = RequestMethod.POST)
	public ResponseEntity<ContractLists> getAllContracts(@RequestHeader("token") String jwtToken) {

		List<TradeContractModel> usersContracts = userService.getAllContract(jwtToken);
		ContractLists contractsResponse = new ContractLists();

		if (usersContracts.isEmpty() || usersContracts == null) {
			contractsResponse.setCode(400);
			contractsResponse.setContracts(null);
			contractsResponse.setStatus("something wrong");
			return new ResponseEntity<ContractLists>(contractsResponse, HttpStatus.BAD_REQUEST);
		}

		contractsResponse.setCode(200);
		contractsResponse.setContracts(usersContracts);
		contractsResponse.setStatus("success");
		return new ResponseEntity<ContractLists>(contractsResponse, HttpStatus.OK);

	}

}
