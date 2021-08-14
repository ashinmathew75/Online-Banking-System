package com.revature.models;

public class AdminAccount extends User{
	private int employeeID;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeeTitle;

	public AdminAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminAccount(int employeeID, String employeeFirstName, String employeeLastName, String employeeTitle) {
		super();
		this.employeeID = employeeID;
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.employeeTitle = employeeTitle;
	}



	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getEmployeeTitle() {
		return employeeTitle;
	}

	public void setEmployeeTitle(String employeeTitle) {
		this.employeeTitle = employeeTitle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((employeeFirstName == null) ? 0 : employeeFirstName.hashCode());
		result = prime * result + ((employeeLastName == null) ? 0 : employeeLastName.hashCode());
		result = prime * result + ((employeeTitle == null) ? 0 : employeeTitle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminAccount other = (AdminAccount) obj;
		if (employeeFirstName == null) {
			if (other.employeeFirstName != null)
				return false;
		} else if (!employeeFirstName.equals(other.employeeFirstName))
			return false;
		if (employeeLastName == null) {
			if (other.employeeLastName != null)
				return false;
		} else if (!employeeLastName.equals(other.employeeLastName))
			return false;
		if (employeeTitle == null) {
			if (other.employeeTitle != null)
				return false;
		} else if (!employeeTitle.equals(other.employeeTitle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeAccount [employeeFirstName=" + employeeFirstName
				+ ", employeeLastName=" + employeeLastName + ", employeeTitle=" + employeeTitle + "]";
	}

	
	

	

}

