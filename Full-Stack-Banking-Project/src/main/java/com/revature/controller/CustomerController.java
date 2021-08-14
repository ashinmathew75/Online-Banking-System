package com.revature.controller;

import io.javalin.http.Context;

public interface CustomerController {
	
	public void getAllBankAccounts(Context ctx);
	
	public void deposit(Context ctx);
	
	public void withdraw(Context ctx);
	
	public void transfer(Context ctx);

	public void logout(Context ctx);
	
	public boolean checkUser(Context ctx);

	public void addAnotherAccount(Context ctx);

	public void registerAccount(Context ctx);
	
	

}
