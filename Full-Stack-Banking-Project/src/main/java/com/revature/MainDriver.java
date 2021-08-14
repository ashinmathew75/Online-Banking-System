package com.revature;

import com.revature.controller.AuthController;
import com.revature.controller.AuthControllerImpl;
import com.revature.controller.CustomerController;
import com.revature.controller.CustomerControllerImpl;
import com.revature.controller.EmployeeController;
import com.revature.controller.EmployeeControllerImpl;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class MainDriver {
	
	// private static final String LOGIN_PATH = "/login";
	private static final String CUSTOMER_LOGIN_PATH = "/customerlogin";
	private static final String EMPLOYEE_LOGIN_PATH = "/employeelogin";
	private static final String LOGOUT = "/logout";
	private static AuthController authController = new AuthControllerImpl();
	
	private static final String CUSTOMER_PATH = "/customer";
	private static final String CUSTOMER_DEPOSIT = "/customerDeposit";
	private static final String CUSTOMER_WITHDRAW = "/customerWithdraw";
	private static final String CUSTOMER_TRANSFER = "/customerTransfer";
//	private static final String CUSTOMER_LOGOUT = "/customerLogout";
	private static final String CUSTOMER_Another_ACCOUNT = "/customerAnotherAccount";
	private static final String CUSTOMER_REGISTER_ACCOUNT = "/customerRegistration";
	private static CustomerController customerController = new CustomerControllerImpl();
	
	private static final String EMPLOYEE_PATH = "/employee";
	private static final String EMPLOYEE_ACCOUNT_APPROVEL = "/employeeApprovel";
	private static final String EMPLOYEE_ALL_ACCOUNT = "/employeeAllAccount";
	private static final String EMPLOYEE_SEARCH_BY_ACCOUNT = "/employeeSearchByAccount";
	private static EmployeeController employeeController = new EmployeeControllerImpl();
	
	
	public static void main(String[] args) {

		Javalin app = Javalin.create(
				config -> {
					config.addStaticFiles("/public");
				}
			).start(7000);	
		
		System.out.println("In main.");
		
		// customer login form
		app.post(CUSTOMER_LOGIN_PATH, ctx -> authController.customerlogin(ctx));
		
		// customer dashboard
		app.get(CUSTOMER_PATH, ctx -> customerController.getAllBankAccounts(ctx));
		
		// customer deposit form
		app.post(CUSTOMER_DEPOSIT, ctx -> customerController.deposit(ctx));
		
		// customer withdraw form
		app.post(CUSTOMER_WITHDRAW, ctx -> customerController.withdraw(ctx));
				
		// customer transfer form
		app.post(CUSTOMER_TRANSFER, ctx -> customerController.transfer(ctx));
		
		// customer logOut form
//		app.get(CUSTOMER_LOGOUT, ctx -> customerController.logout(ctx));
		
		// another account for existing customer
		app.post(CUSTOMER_Another_ACCOUNT, ctx -> customerController.addAnotherAccount(ctx));
				
		// register account for new customer
		app.post(CUSTOMER_REGISTER_ACCOUNT, ctx -> customerController.registerAccount(ctx));
		
		
		
		// Employee login form
		app.post(EMPLOYEE_LOGIN_PATH, ctx -> authController.employeelogin(ctx));
		
		// employee - get all unapproved accounts
		app.get(EMPLOYEE_PATH, ctx -> employeeController.getUnapprovedBankAccounts(ctx));
		
		// employee account approvel path
		app.put(EMPLOYEE_ACCOUNT_APPROVEL, ctx -> employeeController.approveAccount(ctx));
		
		// employee dashboard- display all account
		app.get(EMPLOYEE_ALL_ACCOUNT, ctx -> employeeController.getAllAccount(ctx));
		
		// employee - get account by bankAccount
		app.get(EMPLOYEE_SEARCH_BY_ACCOUNT, ctx -> employeeController.searchByAccount(ctx));
		
		
		// Customer and Employee logOut form
		app.get(LOGOUT, ctx -> authController.logout(ctx));
		
	}
	
	
	
	


}
