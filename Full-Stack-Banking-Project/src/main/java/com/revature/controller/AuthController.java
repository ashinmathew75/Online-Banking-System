package com.revature.controller;

import io.javalin.http.Context;

public interface AuthController {
	
	
	public void customerlogin(Context ctx);
	
	public void employeelogin(Context ctx);
	
	public void logout(Context ctx);
	
	public boolean checkUser(Context ctx);

}
