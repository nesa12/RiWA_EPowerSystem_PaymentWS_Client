<%@page import="com.PaymentManagementServices"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.2.1.min.js"></script>
        <script src="Components/payments.js"></script>
		<title>Payment Management</title>
	</head>
	
	<body>
		<div class="container">
			<div class="row">
				<div class="col">
					<h2 align="center">Payment Management</h2>
					<br>
					<form id="formPayment" name="formPayment" method="POST" action="PaymentManagementServices.jsp">
						Payment Status: 
						<input 
							id="payment_status" 
							name="payment_status" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Total Amount Due: 
						<input 
							id="amount"
							name="amount" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Number of Days for Payment: 
						<input 
							id="no_of_days" 
							name="no_of_days" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Customer ID: 
						<input 
							id="c_id" 
							name="c_id" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						<input 
							id="btnSave" 
							name="btnSave" 
							type="button" 
							value="Save" 
							class="btn btn-primary"
						>

					<input type="hidden" name="hidProjectIDSave" id="hidProjectIDSave" value="">
					</form>
				
					<br>
					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>
					<br>
					<div id="divPaymentsGrid">
						<%
							PaymentManagementServices projectObj = new PaymentManagementServices(); 
							out.print(projectObj.viewPaymentsDetails());
						%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>