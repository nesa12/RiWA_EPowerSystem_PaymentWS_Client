package com.epower.crud.PaymentManagement.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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


				
			} catch(Exception e) {
				output = "Payment details does not added.. something went wrong!.";
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
				output = "<table border='1'><tr><th>id</th><th>Payment Status</th>" +
				"<th>totalAmountDue (Rs.)</th>" + "<th>No Of Days </th>" + 
				"<th>Update</th><th>Remove</th></tr>";
				
				String query = "select * from paymentsdetails";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()) {
					
					String p_id = Integer.toString(rs.getInt("id"));
					String p_status = rs.getString("payment_status");
					String amount = rs.getString("totalAmountDue");
					String noDays = rs.getString("no_of_days");
					
					// Add into the html table
					output += "<tr><td>" + p_id + "</td>";
					output += "<td>" + p_status + "</td>";
					output += "<td>" + amount + "</td>";
					output += "<td>" + noDays + "</td>";
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
					+ "<td><form method='post' action='items.jsp'>"
					+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
					+ "<input name='cus_id' type='hidden' value='" + p_id
					+ "'>" + "</form></td></tr>";
					
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
		public String updatePaymentsDetails(String c_id, String payment_status, String p_dueAmount, String days) {
			
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
				
				output = "Updated payment details successfully";
				
			} catch (Exception e) {
				
				output = "Error while updating the " + c_id;
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//delete
		public String deletePaymentsDetails(String payid)
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
			preparedStmt.setInt(1, Integer.parseInt(payid));
			
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			output = "<h1>Deleted payment detail successfully</h1>";
			}
			catch (Exception e)
			{
				output = "Error while deleting the payment detail.";
				System.err.println(e.getMessage());
			}
		return output;
		}
		
}
