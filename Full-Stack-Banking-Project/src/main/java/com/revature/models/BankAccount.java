package com.revature.models;

public class BankAccount extends User{
	
	private int customerID;
	private String firstName;
	private String lastName;
	private String accountType;
	private int accountNumber;
	private double accountBalance;
	private boolean accountApproved;
	

	public BankAccount() {
		super();	
	}
		

	public BankAccount(int customerID, String firstName, String lastName, String accountType, int accountNumber, double accountBalance, boolean accountApproved) {
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountType = accountType;
		
//		if((accountType == "Checking Account") || (accountType == "Saving Account")) {
//			this.accountType = accountType;
//		}
//		else if (accountType == null) {
//			System.out.println("account type returning null");
//		}
//		else { this.accountType = determinAccountType(accountType); }
		
		if(accountNumber == 0) {
			this.accountNumber = createAccountNumber();
		}
		else { this.accountNumber = accountNumber; }
		
		this.accountBalance = accountBalance;
		this.accountApproved = accountApproved;

		
		// passing new account information to database every time when constructor is called
//		CustomerDao customerDao = new CustomerDaoImpl(firstName,lastName, accountType, accountBalance, 
//				username, password);
	}
	
	public int getCustomerID() {
		return customerID;
	}


	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public int getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}


	public double getAccountBalance() {
		return accountBalance;
	}


	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}


	public boolean isAccountApproved() {
		return accountApproved;
	}



	public void setAccountApproved(boolean accountApproved) {
		this.accountApproved = accountApproved;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountApproved ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(accountBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + accountNumber;
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + customerID;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		if (accountApproved != other.accountApproved)
			return false;
		if (Double.doubleToLongBits(accountBalance) != Double.doubleToLongBits(other.accountBalance))
			return false;
		if (accountNumber != other.accountNumber)
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (customerID != other.customerID)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BankAccount [customerID=" + customerID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", accountType=" + accountType + ", accountNumber=" + accountNumber + ", accountBalance="
				+ accountBalance + ", accountApproved=" + accountApproved + "]";
	}
	


	private String determinAccountType(String accountType) {
		
		if(Integer.parseInt(accountType) == 1) {
			return "Checking Account";
		}
		else {
			return "Savings Account";
		}
		
	}
	
	public int createAccountNumber() {
		return ( (int)((Math.random() * 90000000)+10000000) );
	}
	
	public void  displayAccountDetails() {
		System.out.printf("\n\nAccount Details");
		System.out.printf("\n--------------------\n");
		System.out.printf("%-10s %-10s %-10s\n", "First Name	", ":" , firstName);
		System.out.printf("%-10s %-10s %-10s\n", "Last Name	", ":", lastName);
		System.out.printf("%-10s %-10s %-10s\n", "Account Type	", ":", accountType );
		System.out.printf("%-10s %-10s %-10s\n", "Account Number	", ":", accountNumber);
		System.out.printf("%-10s %-10s %-10s\n", "Account Balance	", ":", accountBalance);	
		}

}
