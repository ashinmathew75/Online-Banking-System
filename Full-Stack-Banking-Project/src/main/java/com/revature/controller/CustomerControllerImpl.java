package com.revature.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.service.CustomerAccount;

import io.javalin.http.Context;

public class CustomerControllerImpl implements CustomerController {
	
	private CustomerAccount customerAccount= new CustomerAccount();
	
	public void getAllBankAccounts(Context ctx) {
		
		System.out.println(AuthControllerImpl.currentUser);
		
		String cookieUser = (String) ctx.cookieStore("id");
		
//		if(cookieUser.equals(AuthControllerImpl.currentUser) ) {
//			ctx.status(201);
//			System.out.println(customerAccount.loadAllAccounts(AuthControllerImpl.currentUser));
//			ctx.json(customerAccount.loadAllAccounts(AuthControllerImpl.currentUser));
//			
//			
//		}
//		else {
//			ctx.status(405);
//		}
		
		
		//User user = new User(0, "ashin", "thomasaa");
		//BankAccount bankAccount = customerAccount.loginValidation(user);
		BankAccount bankAccount = customerAccount.loadCustomerInfoByID(AuthControllerImpl.currentUser);
		
		if(bankAccount !=null) {
			ctx.status(201);
			System.out.println(customerAccount.loadAllAccounts(bankAccount.getCustomerID()));
			ctx.json(customerAccount.loadAllAccounts(bankAccount.getCustomerID()));
		}
		else {
			ctx.status(405);
		}
		
	}

	@Override
	public void deposit(Context ctx) {
		
		System.out.println("depostMoney function called");
		// TODO Auto-generated method stub
		String depositAmount = ctx.formParam("depositAmount");
		String accountNumber = ctx.formParam("accountNumber");
		
		//System.out.println(accountNumber);
				
		double validDepositAmount;
		int validAccountNumber;
		
		if(validNumber(accountNumber)) {
			validAccountNumber = Integer.parseInt(accountNumber);
			
			BankAccount bankAccount = customerAccount.loadCustomerInfoByAccount(validAccountNumber);
			if(bankAccount != null) {
				
				if(validNumber(depositAmount)) {
					validDepositAmount = Double.parseDouble(depositAmount);
		    		if(validDepositAmount > 0) {
		    			System.out.println(validDepositAmount);
		    			bankAccount = customerAccount.loadCustomerInfoByAccount(validAccountNumber);
		    			
		    			if(bankAccount.getCustomerID() == AuthControllerImpl.currentUser) {
		    				System.out.println("currentUser is checked and true");
		    				bankAccount = customerAccount.addMoney(bankAccount, validDepositAmount);
		    				
		    				if (bankAccount != null) {
		    					System.out.println(bankAccount);
		    					ctx.status(201);
		    					System.out.println("added successfully");
		    					ctx.redirect("customerDashboard.html");
		    				}
		    				else {
		    					ctx.status(405);
		    					System.out.println("bankaccount is null");
		    					ctx.redirect("customerDashboard.html");
		    				}
		    			}
		    			else {
		    				ctx.status(401);
		    				ctx.redirect("customerDashboard.html");
		    			}
		    		}
		    		else {
		    			ctx.status(405);
		    			ctx.redirect("customerDashboard.html");
		    		}
		    	}
				
			}else {
				ctx.status(401);
				System.out.println("invalid account number");
				ctx.redirect("customerDashboard.html");
			}
			
			
		}
		else {
			ctx.status(401);
			ctx.redirect("customerDashboard.html");
		}	
	}
	
	
	@Override
	public void withdraw(Context ctx) {
		
		System.out.println("withdrawMoney function called");
		// TODO Auto-generated method stub
		String withdrawAmount = ctx.formParam("withdrawAmount");
		String withdrawAccount = ctx.formParam("withdrawAccount");
		
		//System.out.println(accountNumber);
				
		double validWithdrawAmount;
		int validWithdrawAccount;
		
		if(validNumber(withdrawAccount)) {
			validWithdrawAccount = Integer.parseInt(withdrawAccount);
			
			BankAccount bankAccount = customerAccount.loadCustomerInfoByAccount(validWithdrawAccount);
			if(bankAccount != null) {
				
				if(validNumber(withdrawAmount)) {
					validWithdrawAmount = Double.parseDouble(withdrawAmount);
		    		if(validWithdrawAmount > 0) {
		    			System.out.println(validWithdrawAmount);
		    			bankAccount = customerAccount.loadCustomerInfoByAccount(validWithdrawAccount);
		    			
		    			if(bankAccount.getCustomerID() == AuthControllerImpl.currentUser) {
		    				
		    				if(validWithdrawAmount <= bankAccount.getAccountBalance()) {
		    					System.out.println("currentUser is checked and true");
			    				bankAccount = customerAccount.withdrawMoney(bankAccount, validWithdrawAmount);
			    				
			    				if (bankAccount != null) {
			    					System.out.println(bankAccount);
			    					ctx.status(201);
			    					System.out.println("withdrawMoney successfully");
			    					ctx.redirect("customerDashboard.html");
			    				}
			    				else {
			    					ctx.status(405);
			    					System.out.println("bankaccount is null");
			    					ctx.redirect("customerDashboard.html");
			    				}
		    				}
		    				else if ((validWithdrawAmount >= bankAccount.getAccountBalance())) {
		    					ctx.status(401);
		                        System.out.print("\n***** Note : Insufficient balance in your account*****\n");
			    				ctx.redirect("customerDashboard.html");
		    				}
		    			}
		    			else {
		    				ctx.status(401);
		    				ctx.redirect("customerDashboard.html");
		    			}
		    		}
		    		else {
		    			ctx.status(406);
		    			ctx.redirect("customerDashboard.html");
		    		}
		    	}
				
			}else {
				ctx.status(401);
				System.out.println("invalid account number");
				ctx.redirect("customerDashboard.html");
			}
			
			
		}
		else {
			ctx.status(401);
			ctx.redirect("customerDashboard.html");
		}	
		
		
		
	}

	@Override
	public void transfer(Context ctx) {
		
		System.out.println("transferMoney function called");
		// TODO Auto-generated method stub
		String transferAmount = ctx.formParam("transferAmount");
		String transferToAccount = ctx.formParam("transfer-to-account");
		String transferFromAccount = ctx.formParam("transfer-from-account");
		//System.out.println(accountNumber);
				
		double validTransferAmount;
		int validTransferToAccount;
		int validTransferFromAccount;
		
		if( (validNumber(transferToAccount) && validNumber(transferFromAccount) && (validNumber(transferAmount))) ) {
			validTransferToAccount = Integer.parseInt(transferToAccount);
			validTransferFromAccount = Integer.parseInt(transferFromAccount);
			validTransferAmount = Double.parseDouble(transferAmount);
			
			BankAccount customerBank = customerAccount.loadCustomerInfoByAccount(validTransferFromAccount);
			BankAccount transferBank = customerAccount.loadCustomerInfoByAccount(validTransferToAccount);
			
			
			if((customerBank != null) && (transferBank != null) && (validTransferAmount > 0)) {
				
				if(customerBank.getCustomerID() == AuthControllerImpl.currentUser) {
					
					if(customerBank.getAccountNumber() == validTransferToAccount ) {
						ctx.status(406);
						System.out.println("You cannot transfer money between same account");
//						ctx.redirect("customerDashboard.html");
					}
					else {
						if(customerBank.getAccountBalance() >= validTransferAmount) {
							transferBank = customerAccount.transferingMoney(customerBank, validTransferToAccount, validTransferAmount);
							if(transferBank !=null) {
					            customerBank = customerAccount.loadCustomerInfoByAccount(validTransferFromAccount);
					            System.out.println("\nSuccessfully transfered $" + validTransferAmount + " from your bank account " + customerBank.getAccountNumber() + " to account " + validTransferToAccount);
					            ctx.status(201);
		    					
		    					ctx.redirect("customerDashboard.html");
							}
						}
						else {
							ctx.status(401);
		                    System.out.print("\n***** Note : Insufficient balance in your account*****\n");
//		    				ctx.redirect("customerDashboard.html");
						}
					}

				}
				else {
					// cutomer_id dont match
					ctx.status(401);
					ctx.redirect("mainpage.html");
				}
							
			}
			else {
				ctx.status(406);
				System.out.println("bankaccount is null");
//				ctx.redirect("customerDashboard.html");
			}		
		}
		else {
			ctx.status(401);
//			ctx.redirect("customerDashboard.html");
		}
		ctx.redirect("customerDashboard.html");
	}
	
	@Override
	public void logout(Context ctx) {
		ctx.clearCookieStore();
		AuthControllerImpl.currentUser = -1;
		System.out.println("Logout Successful");
		ctx.redirect("mainpage.html");
		
	}
	
	@Override
	public void addAnotherAccount(Context ctx) {
		System.out.println("addAnotherAccount function called");
		// TODO Auto-generated method stub
		String createAccountType = ctx.formParam("createAccountType");
		String createInitialDeposit = ctx.formParam("createInitialDeposit");
		
		double validInitialDeposit;
		
		System.out.println(createAccountType);
		System.out.println(createInitialDeposit);
		
		
		if( validNumber(createInitialDeposit) ) {
			validInitialDeposit = Double.parseDouble(createInitialDeposit);
			
			BankAccount bankAccount= customerAccount.loadCustomerInfoByID(AuthControllerImpl.currentUser);
		
			if (bankAccount != null) {
				
				try {
					customerAccount.createAnotherBankAccount(bankAccount,createAccountType, validInitialDeposit);
					ctx.status(201);
					
				}catch(Exception e){
					ctx.status(401);
				}
			}
			else {
				ctx.status(401);
			}
		}
		else {
			ctx.status(401);
		}
			
		ctx.redirect("customerDashboard.html");
		
	}
	
	@Override
	public void registerAccount(Context ctx) {
		System.out.println("registerAccount function called");
		// TODO Auto-generated method stub
		String firstName = ctx.formParam("firstname");
		String lastName = ctx.formParam("lastname");
		String accountType = ctx.formParam("registerAccountType");
		String initialDeposit = ctx.formParam("initialDeposit");
		String newUsername = ctx.formParam("newUsername");
		String newPassword = ctx.formParam("newPassword");
		
		
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(accountType);
		System.out.println(initialDeposit);
		System.out.println(newUsername);
		System.out.println(newPassword);
		
		double validInitialDeposit;
		
		if( validNumber(initialDeposit) ) {
			validInitialDeposit = Double.parseDouble(initialDeposit);
			
			if(validInitialDeposit >= 0) {
    			
    			
    			//boolean isValid = false;
    			
    			if((isValidUsernamePswd(newUsername)))
                {
                	if((isValidUsernamePswd(newPassword)))
                    {
                		//isValid = true;
                		BankAccount bankAccount = new BankAccount(0, firstName, lastName, accountType, 0, validInitialDeposit, false);
                        User user = new User(0, newUsername, newUsername);
                        customerAccount.createAccount(user, bankAccount);
                        ctx.status(200);
                        ctx.redirect("mainpage.html");
                        
                    }
                }
                else {
                	//isValid = false;
                    System.out.println("\nInvalid Username or Password !! username and password must contain number characters and alphabets");
                    ctx.status(406);
                }
    			
    		}
			else {
				System.out.print("\nDeposit Amount Must be greater than 0");
				ctx.status(406);
			}
		}
		
		ctx.redirect("mainpage.html");
	}

	@Override
	public boolean checkUser(Context ctx) {
		
		return customerAccount.validateToken(ctx.cookieStore("user"));
	}
	
	private boolean validNumber(String input) {
    	try{
    	    double isValid = Double.parseDouble(input);
    	    return true;
    	} catch(Exception e) {
    	    return false; 	    
    	}	
    }
	
	private boolean isValidUsernamePswd(String input)
    {
        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
  
        Pattern p = Pattern.compile(regex);
  
        if (input == null) { return false; }
        Matcher m = p.matcher(input);
        return m.matches();
    } 


}
