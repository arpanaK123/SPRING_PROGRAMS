package com.bridgeit.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bridgeit.model.Employee;

@Controller
@RequestMapping("/employee-module/addNew")
@SessionAttributes("employee")
public class EmployeeController {

	//private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	// EmployeeManager manager;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		Employee employeeVO = new Employee();
		model.addAttribute("employee", employeeVO);
		return "addEmployee";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("employee") Employee employeeVO, BindingResult result,
			SessionStatus status) {
		// Validation code start
		boolean error = false;

		System.out.println(employeeVO); // Verifying if information is same as input by user

		if (employeeVO.getFirstName().isEmpty()) {
			result.rejectValue("firstName", "error.firstName");
			error = true;
		}

		if (employeeVO.getLastName().isEmpty()) {
			result.rejectValue("lastName", "error.lastName");
			error = true;
		}

		if (employeeVO.getEmail().isEmpty()) {
			result.rejectValue("email", "error.email");
			error = true;
		}

		if (error) {
			return "addEmployee";
		}
		// validation code ends

		// Store the employee information in database
		// manager.createNewRecord(employeeVO);

		// Mark Session Complete
		status.setComplete();
		return "redirect:addNew/success";
	}

	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String success(Model model) {
		return "addSuccess";
	}

}
