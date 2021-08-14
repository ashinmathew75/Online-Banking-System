package com.revature.controller;

import io.javalin.http.Context;

public interface EmployeeController {

	public void getUnapprovedBankAccounts(Context ctx);

	public void approveAccount(Context ctx);

	public void getAllAccount(Context ctx);

	public void searchByAccount(Context ctx);

}
