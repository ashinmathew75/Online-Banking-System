package com.revature.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.repository.CustomerDao;
import com.revature.repository.CustomerDaoImpl;


public class CustomerAccount {
	
	final static Logger logger = Logger.getLogger(CustomerAccount.class);
	private static byte[] salt = new SecureRandom().getSeed(16);
	private CustomerDao customerDao = new CustomerDaoImpl();
	private Map<String, String> tokenRepo = new HashMap<>();
	
	public void createAccount(User user, BankAccount bankAccount){
		
		try{
			// calling DOA method to create customer table 
			//customerDao.createAccount(bankAccount.getFirstName(), bankAccount.getLastName());

			// getting returned cutomer_id from the customer table and setting it into BankAccount object customerID field
			bankAccount.setCustomerID(customerDao.createAccount(bankAccount.getFirstName(), bankAccount.getLastName()));
			
			user.setid(bankAccount.getCustomerID());
			// calling DOA method to create customer login table
			customerDao.createLogin(user.getid(), user.getUsername(), user.getPassword());
			
			//calling DOA method to create customer bank account table;
			customerDao.createBankTable(bankAccount.getCustomerID(), bankAccount.getAccountNumber(), bankAccount.getAccountType(), bankAccount.getAccountBalance(), bankAccount.isAccountApproved());
			
			//calling DOA method to create case history so employees and approve/reject 
			String caseSubject = "Needs Account Approvel";
			String caseDescription = null;
			boolean caseResolved = false;
			customerDao.createCaseHistory(bankAccount.getAccountNumber(), caseSubject, caseDescription, caseResolved);
			
			logger.info("New user created the customer account and added to the database with customer_id = " + bankAccount.getCustomerID());
			
		}catch(Exception e){
			e.printStackTrace();		
		}    	
	}

	public BankAccount loginValidation(User user) {
		BankAccount bankAccount ;

		user = customerDao.customerLoginValidation(user.getUsername(), user.getPassword());
		if(user != null)
		{
			if (user.getid() > 0) {
				bankAccount = loadCustomerInfoByID(user.getid());
				
				return bankAccount;
			}
			else return null;
			
		}
		else {
			return null;
		}
		
		// return true;
		
	}
	
	public BankAccount loadCustomerInfoByID(int customerID) {
		BankAccount  bankAccount = null;
		
		try {
			bankAccount = customerDao.selectCustomerInfoByID(customerID);

			return bankAccount;
		}
		catch (Exception e){
			System.out.println("Cannot find the customer account");
			return bankAccount;
		}
		
	}
	
	public BankAccount loadCustomerInfoByAccount(int accountNumber) {
		BankAccount  bankAccount = null;
		
		try {
			bankAccount = customerDao.selectCustomerByAccountNumber(accountNumber);

			return bankAccount;
		}
		catch (Exception e){
			System.out.println("Cannot find the customer account");
			return bankAccount;
		}
		
	}
	
	public BankAccount addMoney(BankAccount bankAccount, double depositAmount) {
		
		if(depositAmount >= 0 ) {
			
			if(bankAccount.isAccountApproved()) {
				double totalBalance = (bankAccount.getAccountBalance()) + depositAmount ;
				
				customerDao.updateAccountBalance(bankAccount.getCustomerID(), bankAccount.getAccountNumber(), totalBalance);
				
				if (customerDao.updateAccountBalance(bankAccount.getCustomerID(), bankAccount.getAccountNumber(), totalBalance)) {
					bankAccount = loadCustomerInfoByAccount(bankAccount.getAccountNumber());			
					return bankAccount;
				}
				else return bankAccount = null;

			}
			else return bankAccount = null;
			
		}
		else {
			return bankAccount = null;
		}
	}
	
	public BankAccount withdrawMoney(BankAccount bankAccount, double withdrawAmout) {
		
		if( (bankAccount.getAccountBalance()) >= withdrawAmout ) {
			double totalBalance = (bankAccount.getAccountBalance()) - withdrawAmout ;
			
			if(totalBalance > 0) {
				
				if(bankAccount.isAccountApproved()) {
					customerDao.updateAccountBalance(bankAccount.getCustomerID(), bankAccount.getAccountNumber(), totalBalance);
					bankAccount = loadCustomerInfoByAccount(bankAccount.getAccountNumber());
					
					return bankAccount;
				}
				else {
					return bankAccount = null;
				}
					
			}
			else
			{
				return bankAccount = null;
			}
		}
		else {
			return bankAccount;
		}
		
		
	}
	

	public BankAccount transferingMoney(BankAccount tranferFromAccount, int transferAccountNumber, double transferAmount) {

		BankAccount transferToAccount = new BankAccount();
		transferToAccount = customerDao.selectCustomerByAccountNumber(transferAccountNumber);
		
		if(transferToAccount != null) {	
			
			try {
				if((transferToAccount.isAccountApproved()) && (tranferFromAccount.isAccountApproved())) {
					transferToAccount = addMoney(transferToAccount, transferAmount);
					withdrawMoney(tranferFromAccount, transferAmount);
				}
				else {
					transferToAccount = null;
				}
			}catch (Exception e) {
				System.out.println("\nBankAccount of " + transferToAccount.getAccountNumber() + " is not Approved ");
				transferToAccount = null;
			}
			
		}
		else
		{
			System.out.println("\nAccount number in invalid. Tranfer process cancelled");
			transferToAccount = null;
		}
		
		return transferToAccount;
	}

	public void createAnotherBankAccount(BankAccount bankAccount, String customerAcountType, double initialDeposit) {
		try {
			
			int newAccountNumber = bankAccount.createAccountNumber();
			customerDao.createBankTable(bankAccount.getCustomerID(), newAccountNumber, customerAcountType, initialDeposit, false);
			
			//calling DOA method to create case history so employees and approve/reject 
			String caseSubject = "Needs Account Approvel";
			String caseDescription = null;
			boolean caseResolved = false;
			customerDao.createCaseHistory(newAccountNumber, caseSubject, caseDescription, caseResolved);
			
			logger.info("New bank account added to the database with customer_id = " + bankAccount.getCustomerID());
			
		}catch(Exception e){
			e.printStackTrace();		
		}   
		
	}
	
	public List<BankAccount> loadAllAccounts(int customerID) {
		List<BankAccount> bankAccount = customerDao.selectBankInfoBYID(customerID);
		return bankAccount;		
	}

	public String createToken(String username) {

		String token = simpleHash(username);
		tokenRepo.put(token, username);
		
		return token;
	}
	


	public boolean validateToken(String token) {

		return tokenRepo.containsKey(token);
	}
	
	
	private String simpleHash(String username) {
		String hash = null;
		
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("SHA-512");
			
			md.update(salt);
			
			byte[] bytes = md.digest(username.getBytes());
			
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(0));
			}
			
			hash = sb.toString();
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return hash;
	}
	 

	
}
