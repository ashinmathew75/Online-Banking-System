package com.revature.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.AdminAccount;
import com.revature.models.BankAccount;
import com.revature.service.CustomerAccount;
import com.revature.service.EmployeeAccount;

import io.javalin.http.Context;

public class EmployeeControllerImpl implements EmployeeController{

	private AuthController authController = new AuthControllerImpl();
	private CustomerAccount customerAccount = new CustomerAccount();
    private EmployeeAccount employeeAccount = new EmployeeAccount();
	
	@Override
	public void getUnapprovedBankAccounts(Context ctx) {
//		if(authController.checkUser(ctx)) {
//			ctx.status(200);
//			ctx.json(employeeAccount.loadUnapprovedAccounts());
//		}else {
//			ctx.status(405);
//		}
		
		
		AdminAccount adminAccount = employeeAccount.loadEmployeeInfoByID(AuthControllerImpl.employeeUser);
		
		if(adminAccount !=null) {
			ctx.status(200);
			ctx.json(employeeAccount.loadUnapprovedAccounts());
		}
		else {
			ctx.status(405);
		}
		
	}

	@Override
	public void approveAccount(Context ctx) {
		
		ObjectMapper om = new ObjectMapper();
		
		BankAccount bankAccount;
		try {
			bankAccount = om.readValue(ctx.body(), BankAccount.class); //json information 
			System.out.println(bankAccount);
			boolean approved = !(bankAccount.isAccountApproved());
			System.out.println(approved);
		
			bankAccount = customerAccount.loadCustomerInfoByAccount(bankAccount.getAccountNumber()); //full database object
			employeeAccount.approveAccount(bankAccount.getCustomerID(), bankAccount.getAccountNumber(), approved);
			
			ctx.status(200);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			ctx.status(405);
		}
		
		
	}

	@Override
	public void getAllAccount(Context ctx) {
		// TODO Auto-generated method stub
		
		AdminAccount adminAccount = employeeAccount.loadEmployeeInfoByID(AuthControllerImpl.employeeUser);
		
		if(adminAccount !=null) {
			ctx.status(200);
			ctx.json(employeeAccount.loadAllAccounts());
		}
		else {
			ctx.status(405);
		}
		
	}

	@Override
	public void searchByAccount(Context ctx) {
		
		String accountNumber = ctx.formParam("accountNumber");
		AdminAccount adminAccount = employeeAccount.loadEmployeeInfoByID(AuthControllerImpl.employeeUser);
		BankAccount bankAccount;
		int validAccountNumber;
		
		if(adminAccount !=null) {
			if( validNumber(accountNumber) ) {
				validAccountNumber = Integer.parseInt(accountNumber);
				System.out.println(validAccountNumber);
				
				try {
					bankAccount = customerAccount.loadCustomerInfoByAccount(validAccountNumber);
					System.out.println(bankAccount);
					
					if (bankAccount != null) {
						ctx.status(200);
						ctx.json(bankAccount);
					}
					else ctx.status(405);
					
				}catch(Exception e) {
					ctx.status(405);
				}
			}
			else ctx.status(405);
		}
		else {
			ctx.status(405);
		}	
	}
	
	private boolean validNumber(String input) {
    	try{
    	    double isValid = Double.parseDouble(input);
    	    return true;
    	} catch(Exception e) {
    	    return false; 	    
    	}	
    }

}
