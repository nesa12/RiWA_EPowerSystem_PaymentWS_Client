package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class PaymentManagementServices {
	
	//connection
			private Connection connect() {
				Connection con = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					
					String url = String.format("jdbc:mysql://localhost:3306/electricgrid_db");
					String username = "root";
					String password = "";
					
					con = DriverManager.getConnection(url,username, password);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return con;
			}
		
		//insert payment details
		public String insertPaymentsDetails(String payment_status, double amount, String no_of_days, String c_id) {
			
			String output = "";
			
			try {
				
				Connection con = connect();
				
				if(con == null)
				{return "Error while connecting to the database for inserting data";}
				
				String insertQuery = "insert into paymentsdetails (`payment_status`,`totalAmountDue`, `no_of_days`, `c_id`)" + "values(?,?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertQuery);
				ps.setString(1, payment_status);
				ps.setDouble(2, amount);
				ps.setString(3, no_of_days);
				ps.setString(4, c_id);

				ps.execute();
				con.close();

				String newPaymentDet = viewPaymentsDetails(); 
				output = "{\"status\":\"success\", \"data\": \"" + newPaymentDet + "\"}"; 
				
			} catch(Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the payment details.\"}";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//view all payments
		public String viewPaymentsDetails() {
			
			String output ="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for read the payment details";}
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Payment Status</th><th>totalAmountDue (Rs.)</th>" + "<th>No Of Days </th>" + "<th>id</th>" +
				"<th>Update</th><th>Remove</th></tr>";
				
				String query = "select * from paymentsdetails";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()) {
					
					String id = Integer.toString(rs.getInt("id"));
					String p_status = rs.getString("payment_status");
					String amount = rs.getString("totalAmountDue");
					String noDays = rs.getString("no_of_days");
					
					// Add into the html table
					output += "<tr><td>" + p_status + "</td>";
					output += "<td>" + amount + "</td>";
					output += "<td>" + noDays + "</td>";
					output += "<td>" + id + "</td>";
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-pid='"
							+ id + "'>" + "</td></tr>";
					
				}
				
				con.close();
				
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading payments";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		//update payment --> Payment update is unnecessary <--
		public String updatePaymentsDetails(String payment_status, String p_dueAmount, String days, String c_id) {
			
			String output="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for updating the " + c_id;}
				
				// create a prepared statement
				String query = "UPDATE paymentsdetails SET payment_status=?, totalAmountDue=?, no_of_days=? WHERE c_id=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, payment_status);
				preparedStmt.setString(2, p_dueAmount);
				preparedStmt.setString(3, days);
				preparedStmt.setString(4, c_id);
				
				// execute the statement
				preparedStmt.execute();
				
				con.close();
				
				String newPaymentDet = viewPaymentsDetails(); 
				output = "{\"status\":\"success\", \"data\": \"" + newPaymentDet + "\"}"; 
				
			} catch (Exception e) {
				
				output = "{\"status\":\"error\", \"data\": \"Error while updating the payment details.\"}"; 
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//delete
		public String deletePaymentsDetails(String id)
		{
			String output = "";
			try
			{
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			
			// create a prepared statement
			String query = "delete from paymentsdetails WHERE id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));
			
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			
			String newPaymentDet = viewPaymentsDetails(); 
			output = "{\"status\":\"success\", \"data\": \"" + newPaymentDet + "\"}"; 
			
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment details.\"}"; 
				System.err.println(e.getMessage());
			}
		return output;
		}
		
}
