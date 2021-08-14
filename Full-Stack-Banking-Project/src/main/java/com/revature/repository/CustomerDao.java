package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.User;

public interface CustomerDao {
	
	List<BankAccount> bankAccount =  new ArrayList<BankAccount>();
	
	/*
	 * Method used to create new account for customers.
	 * @param firstName - first name of the new user.
	 * @param lastName - last name of the new user.
	 * @param password - password that user created for this specific account.
	 */
	public int createAccount(String firstName, String lastName);
	
	public boolean createLogin(int customerID, String username, String password);
	
	public boolean createBankTable(int customerID, int accountNumber, String accountType, Double accountBalance, boolean accountApproved);
	
	public int createCaseHistory(int accountNumber, String caseSubject, String caseDescription, boolean caseResolved);
	
	public User customerLoginValidation(String username, String password);
	
	public BankAccount selectCustomerInfoByID(int customerID);
	
	public BankAccount selectCustomerByAccountNumber(int customerID);
	
	public List<BankAccount> selectBankInfoBYID(int customerID);
	
	
	/*
	 * Function used to deposit to account
	 * @param depositeAmount - takes the money (double) the user like to deposit
	 */
	public boolean updateAccountBalance(int customerID, int accountNumber, double updatedAccountBalance);
	

	
	
	/*
	 * Function used to transfer money from one account to another
	 * @param depositeAmount - takes the money (double) the user like to deposit
	 */
	public boolean transferToAccount(int accountNumber, double transferAmount);
	
	/**
	 * Function to display the account details of a specific user
	 * Displays first name, last name, account number and account balance
	 */

	
	public List<BankAccount> caseHistory ();
	
	

}