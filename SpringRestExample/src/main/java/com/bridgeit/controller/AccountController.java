package com.bridgeit.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bridgeit.model.Account;

@Controller
@RequestMapping("/accountController")
public class AccountController {
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public @ResponseBody Account[] getAccount() {
		Account[] accounts = new Account[] { new Account("11", "Priya", BigDecimal.valueOf(2505.89)),
				new Account("12", "Riya", BigDecimal.valueOf(2456.90)) };

		return accounts;
	}
}
