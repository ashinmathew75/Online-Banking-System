package com.revature.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.AdminAccount;
import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.repository.EmployeeDao;
import com.revature.repository.EmployeeDaoImpl;


public class EmployeeAccount {

	private EmployeeDao employeeDao = new EmployeeDaoImpl();
    final static Logger logger = Logger.getLogger(EmployeeAccount.class);

	
	public boolean createAccount(User user, AdminAccount adminAccount){
		
		try{
			// calling DOA method to create customer table 
			//employeeDao.createAccount(adminAccount.getFirstName(), adminAccount.getLastName());

			// getting returned cutomer_id from the customer table and setting it into adminAccount object customerID field
			adminAccount.setEmployeeID(employeeDao.createEmployeeAccount(adminAccount.getEmployeeFirstName(), adminAccount.getEmployeeLastName(), adminAccount.getEmployeeTitle()));
			
			user.setid(adminAccount.getEmployeeID());
			// calling DOA method to create customer login table
			employeeDao.createEmployeeLogin(user.getid(), user.getUsername(), user.getPassword());
			return true;
			
		}catch(Exception e){
			e.printStackTrace();	
			return false;
		}    	
	}

	public AdminAccount loginValidation(User user) {
		AdminAccount adminAccount ;

		user = employeeDao.employeeLoginValidation(user.getUsername(), user.getPassword());
		
		if(user != null)
		{
			if (user.getid() > 0) {
				adminAccount = loadEmployeeInfoByID(user.getid());
				return adminAccount;
			}
			else return null;		
		}
		else {
			return null;
		}		
		// return true;
	}
	
	public AdminAccount loadEmployeeInfoByID(int employeeID) {
		AdminAccount adminAccount = null;
		
		try {
			adminAccount = employeeDao.selectEmployeeInfoByID(employeeID);

			return adminAccount;
		}
		catch (Exception e){
			System.out.println("Cannot find the customer account");
			return adminAccount;
		}
		
	}

	public List<BankAccount> loadUnapprovedAccounts() {
		
		return employeeDao.getUnApprovedAccounts();		
	}
	
	public List<BankAccount> loadAllAccounts() {
		
		return employeeDao.getAllAccounts();		
	}

	public boolean approveAccount(int customerID, int accountNumber, boolean accountApproved) {
		
		return employeeDao.approveAccount(customerID, accountNumber, accountApproved);
	}
	
	public BankAccount getAccount(int accountNumber) {
		BankAccount bankAccount = null;	
		try {
			bankAccount = employeeDao.getByAccountNumber(accountNumber);
			
			if( !(bankAccount.isAccountApproved()) ){
				return bankAccount;
			}
			else {
				return bankAccount = null;
			}
			
		}
		catch (Exception e){
			return bankAccount = null;
		}
	}
	
}
