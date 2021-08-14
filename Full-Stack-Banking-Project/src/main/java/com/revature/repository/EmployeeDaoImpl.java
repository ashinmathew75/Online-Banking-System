package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.AdminAccount;
import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class EmployeeDaoImpl implements EmployeeDao {

	
	@Override
	public int createEmployeeAccount(String employeefirstName, String employeelastName, String employeeTitle) {
		int employee_id = -1;
        String sql = "INSERT INTO employee_table (first_name, last_name, employee_title) VALUES (?,?,?) RETURNING employee_id;";

        try (Connection conn = ConnectionFactory.getConnection()) {

            PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employeefirstName);
            preparedStatement.setString(2, employeelastName);
            preparedStatement.setString(3, employeeTitle);

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
            	employee_id = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee_id;
	}

	@Override
	public boolean createEmployeeLogin(int employeeID, String employeeUsername, String employeePassword) {
		boolean loginCreated =  false;
        String sql = "INSERT INTO employee_login (employee_id, employee_username, employee_pswd) VALUES (?,?,?) RETURNING employee_id;";

        try (Connection conn = ConnectionFactory.getConnection()) {

        	PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        	preparedStatement.setInt(1, employeeID);
        	preparedStatement.setString(2, employeeUsername);
        	preparedStatement.setString(3, employeePassword);

        	preparedStatement.execute();

            loginCreated = true;
        }
        catch (SQLException e) {
        	loginCreated = false;
        	e.printStackTrace();
        }
        return loginCreated;
	}
	
	
	@Override
	public List<BankAccount> getPendingAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BankAccount> getCustomerAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User employeeLoginValidation(String username, String password) {
		User user = null;

    	String sql = "SELECT * FROM employee_login WHERE employee_username = '"+username+"' AND employee_pswd = '"+password+"' ;";
    	
    	try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				
				user = new User(
						resultSet.getInt("employee_id"),
						resultSet.getString("employee_username"), 
						resultSet.getString("employee_pswd"));	
				
				return user;
			}		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public AdminAccount selectEmployeeInfoByID(int employeeID) {
		AdminAccount adminAccount = null;
    	
    	String sql = "SELECT * FROM employee_table WHERE employee_id = " + employeeID + ";";
    	
    	try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet resultSet = ps.executeQuery();			
			
			while(resultSet.next()) {	
				adminAccount = new AdminAccount(
						resultSet.getInt("employee_id"),
						resultSet.getString("first_name"), 
						resultSet.getString("last_name"),
						resultSet.getString("employee_title")
						);	
				
				return adminAccount;
			}
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return adminAccount;  	
    	
	}

	@Override
	public List<BankAccount> getUnApprovedAccounts() {
		
		List<BankAccount> listofUnapprovedAccounts = new ArrayList<>();
		
		String sql = "SELECT customer_table.customer_id, first_name, last_name, account_Number, account_type, account_balance, account_approved "
				+ "	FROM customer_table "
				+ "Full OUTER JOIN account_information "
				+ "ON account_information.customer_id = customer_table.customer_id "
				+ "WHERE account_information.account_approved = false;";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet resultSet = ps.executeQuery();			
			
			while(resultSet.next()) {	
				listofUnapprovedAccounts.add(new BankAccount(
						resultSet.getInt("customer_id"),
						resultSet.getString("first_name"), 
						resultSet.getString("last_name"),
						resultSet.getString("account_type"),
						resultSet.getInt("account_number"),
						resultSet.getDouble("account_balance"),
						resultSet.getBoolean("account_approved")
						));	
			}
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listofUnapprovedAccounts;
	}
	
	@Override
	public List<BankAccount> getAllAccounts() {
		List<BankAccount> listofAllAccounts = new ArrayList<>();
		
		String sql = "SELECT customer_table.customer_id, first_name, last_name, account_Number, account_type, account_balance, account_approved "
				+ "	FROM customer_table "
				+ "Full OUTER JOIN account_information "
				+ "ON account_information.customer_id = customer_table.customer_id ";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet resultSet = ps.executeQuery();			
			
			while(resultSet.next()) {	
				listofAllAccounts.add(new BankAccount(
						resultSet.getInt("customer_id"),
						resultSet.getString("first_name"), 
						resultSet.getString("last_name"),
						resultSet.getString("account_type"),
						resultSet.getInt("account_number"),
						resultSet.getDouble("account_balance"),
						resultSet.getBoolean("account_approved")
						));	
			}
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listofAllAccounts;
	}

	

	@Override
	public boolean approveAccount(int customerID, int accountNumber, boolean accountApproved) {
		String sql = "UPDATE account_information SET account_approved = " + accountApproved + " WHERE customer_id = " + customerID + " and account_number = " + accountNumber + ";";

        try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.execute();		
			
			return true;

		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
		
	}

	@Override
	public BankAccount getByAccountNumber(int accountNumber) {
		BankAccount bankAccount = null;
    	
    	//String sql = "SELECT customer_id, account_number, account_balance FROM account_information WHERE account_number = " + accountNumber + ";";
    	
    	String sql = "SELECT customer_table.customer_id, first_name, last_name, account_Number, account_type, account_balance, account_approved "
				+ "	FROM customer_table "
				+ "Full OUTER JOIN account_information "
				+ "ON account_information.customer_id = customer_table.customer_id "
				+ "WHERE account_information.account_number =" + accountNumber +";";
	
    	
    	
    	try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet resultSet = ps.executeQuery();			
			
			while(resultSet.next()) {	
				bankAccount = new BankAccount(
						resultSet.getInt("customer_id"),
						resultSet.getString("first_name"), 
						resultSet.getString("last_name"),
						resultSet.getString("account_type"),
						resultSet.getInt("account_number"),
						resultSet.getDouble("account_balance"),
						resultSet.getBoolean("account_approved")
						);	
				
				return bankAccount;
			}
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	
    	return bankAccount; 
	}
	

}
