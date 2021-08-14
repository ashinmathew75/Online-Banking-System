package com.revature.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.AdminAccount;
import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.service.CustomerAccount;
import com.revature.service.EmployeeAccount;

import io.javalin.http.Context;

public class AuthControllerImpl implements AuthController {

	private CustomerAccount customerAccount= new CustomerAccount();
	private EmployeeAccount employeeAccount= new EmployeeAccount();
    final static Logger logger = Logger.getLogger(AuthControllerImpl.class);
    public static int currentUser = -1;
    public static int employeeUser = -1; 

    
	@Override
	public void customerlogin(Context ctx) {	

		String customerUsername = ctx.formParam("username");
		String customerPassword = ctx.formParam("password");
		
		
		User user = new User(0, customerUsername, customerPassword);
		
				
		BankAccount bankAccount = customerAccount.loginValidation(user);
		
		
		if(bankAccount != null) {
			ctx.status(200);
			currentUser = bankAccount.getCustomerID();
			ctx.cookieStore("user", customerAccount.createToken(customerUsername));

			System.out.println(bankAccount);
			//System.out.println(ctx.cookieStore("user"));
			//ctx.redirect("customerDashboard.html");
		}
		else {
			ctx.status(401);
			//ctx.redirect("mainpage.html");
		}			
	}
	
	@Override
	public void logout(Context ctx) {
		ctx.clearCookieStore();
		currentUser = -1;
		employeeUser = -1; 
		System.out.println("Logout Successful");
		ctx.redirect("mainpage.html");
		
	}

	@Override
	public boolean checkUser(Context ctx) {
		
		return customerAccount.validateToken(ctx.cookieStore("user"));
	}

	@Override
	public void employeelogin(Context ctx) {
		String employeeUsername = ctx.formParam("employeeUsername");
		String employeePassword = ctx.formParam("employeePassword");
		
		
		User user = new User(0, employeeUsername, employeePassword);
		
				
		AdminAccount adminAccount = employeeAccount.loginValidation(user);
		
		
		if(adminAccount != null) {
			
			System.out.print("\nLogin Successfully !!, Welcome " + adminAccount.getEmployeeFirstName() + " " + adminAccount.getEmployeeLastName() + "\n");
        	logger.info("Employee login Successfully !! , employee_id = " + adminAccount.getEmployeeID());
        		
			ctx.status(200);

			System.out.println(adminAccount);
			System.out.println(adminAccount.getEmployeeID());
			employeeUser = adminAccount.getEmployeeID();
			
			ctx.redirect("employeeDashboard.html");
		}
		else {
			logger.info("Employee login Failed !! , employeeUsername = " + employeeUsername);
			ctx.status(401);
			ctx.redirect("employeeLogin.html");
		}			
		
	}

}
