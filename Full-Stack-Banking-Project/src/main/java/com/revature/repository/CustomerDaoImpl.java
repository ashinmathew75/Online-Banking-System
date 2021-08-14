package com.revature.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public int createAccount(String firstName, String lastName) {
        
        int cutomer_id = -1;
        String sql_customer_table = "INSERT INTO customer_table (first_name, last_name) VALUES (?,?) RETURNING customer_id;";

        try (Connection conn = ConnectionFactory.getConnection()) {

            PreparedStatement preparedStatement = conn.prepareStatement(sql_customer_table, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                cutomer_id = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cutomer_id;

    }

    @Override
    public boolean createLogin(int customerID, String username, String password) {
        // TODO Auto-generated method stub
    	
    	boolean loginCreated =  false;
        String sql_customerLogin = "INSERT INTO customer_login (customer_id, customer_username, customer_pswd) VALUES (?,?,?) RETURNING customer_id;";

        try (Connection conn = ConnectionFactory.getConnection()) {

        	PreparedStatement preparedStatement = conn.prepareStatement(sql_customerLogin, PreparedStatement.RETURN_GENERATED_KEYS);

        	preparedStatement.setInt(1, customerID);
        	preparedStatement.setString(2, username);
        	preparedStatement.setString(3, password);

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
    public boolean createBankTable(int customerID, int accountNumber, String accountType, Double accountBalance, boolean accountApproved) {
    	
    	boolean BankTableCreated =  false;
        String sql_bank_table = "INSERT INTO account_information (customer_id, account_number, account_type, account_balance, account_approved) VALUES (?,?,?,?,?) RETURNING customer_id;";

        try (Connection conn = ConnectionFactory.getConnection()) {

        	PreparedStatement preparedStatement = conn.prepareStatement(sql_bank_table, PreparedStatement.RETURN_GENERATED_KEYS);

        	preparedStatement.setInt(1, customerID);
        	preparedStatement.setInt(2, accountNumber);
        	preparedStatement.setString(3, accountType);
        	preparedStatement.setDouble(4, accountBalance);
        	preparedStatement.setBoolean(5, accountApproved);

        	preparedStatement.execute();

        	BankTableCreated = true;
        }
        catch (SQLException e) {
        	BankTableCreated = false;
        	e.printStackTrace();
        }
        return BankTableCreated;	
    }

    @Override
    public int createCaseHistory(int accountNumber, String caseSubject, String caseDescription, boolean caseResolved) {

    	int case_id = -1;
        String sql_case_history = "INSERT INTO case_history (account_number, case_subject, case_description, case_resolved) VALUES (?,?,?,?) RETURNING case_id;";

        try (Connection conn = ConnectionFactory.getConnection()) {

            PreparedStatement preparedStatement = conn.prepareStatement(sql_case_history, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setString(2, caseSubject);
            preparedStatement.setString(3, caseDescription);
            preparedStatement.setBoolean(4, caseResolved);

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
            	case_id = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return case_id;
    }
    
    @Override
    public User customerLoginValidation(String username, String password) {
    	User user = null;

    	String sql = "SELECT * FROM customer_login WHERE customer_username = '"+username+"' AND customer_pswd = '"+password+"' ;";
    	
    	try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				
				user = new User(
						resultSet.getInt("customer_id"),
						resultSet.getString("customer_username"), 
						resultSet.getString("customer_pswd"));	
				
				return user;
			}		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
    	
    }
    

    public BankAccount selectCustomerInfoByID(int customerID) {
    	BankAccount bankAccount = null;
    	
    	String sql = "SELECT customer_table.customer_id, first_name, last_name, account_Number, account_type, account_balance, account_approved "
    				+ "	FROM customer_table "
    				+ "Full OUTER JOIN account_information "
    				+ "ON account_information.customer_id = customer_table.customer_id "
    				+ "WHERE customer_table.customer_id =" + customerID +";";
    	
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
    
    public BankAccount selectCustomerByAccountNumber(int accountNumber) {
    	
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
	
    @Override
	public List<BankAccount> selectBankInfoBYID(int customerID) {

    	List<BankAccount> listofBankAccounts = new ArrayList<>();
		
		String sql = "SELECT customer_table.customer_id, first_name, last_name, account_Number, account_type, account_balance, account_approved "
				+ "	FROM customer_table "
				+ "Full OUTER JOIN account_information "
				+ "ON account_information.customer_id = customer_table.customer_id "
				+ "WHERE account_information.customer_id = " + customerID + ";" ;
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet resultSet = ps.executeQuery();			
			
			while(resultSet.next()) {	
				listofBankAccounts.add(new BankAccount(
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
		
		return listofBankAccounts;   	
    }

    @Override
    public boolean updateAccountBalance(int customerID, int accountNumber, double updatedAccountBalance) {
        
        String sql = "UPDATE account_information SET account_balance = " + updatedAccountBalance + 
        		" where customer_id = " + customerID + " and account_number = " + accountNumber + ";";

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
    public boolean transferToAccount(int accountNumber, double transferAmount) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public List < BankAccount > caseHistory() {
        // TODO Auto-generated method stub
        return null;
    }


}