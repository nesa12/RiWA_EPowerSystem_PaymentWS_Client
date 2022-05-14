package com.epower.crud.PaymentManagement.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.epower.crud.PaymentManagement.services.PaymentManagementServices;

@Path("/Payments")
public class PaymentManagementResource {

	PaymentManagementServices payServ = new PaymentManagementServices();
	
	//View all payments
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String viewPaymentsInfo(){
		return payServ.viewPaymentsDetails();
	}
	
	//insert payments
	@POST
	@Path("/addPaymentData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String insertPaymentsInfo(@FormParam("payment_status") String payment_status, @FormParam("totalAmountDue") double amount,
								@FormParam("no_of_days") String days, @FormParam("c_id") String c_id) 
	{
		
		String output = payServ.insertPaymentsDetails(payment_status, amount, days, c_id);
		return output + "<h1>Add customer payment details successfully.</h1>";
	}
	
	@PUT
	@Path("/updatePaymentData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePaymentsInfo(@FormParam("payment_status") String payment_status, @FormParam("totalAmountDue") String amount,
			@FormParam("no_of_days") String days,@FormParam("c_id") String c_id ) {
			
		String output = payServ.updatePaymentsDetails(c_id, payment_status, amount,days);
		
		return output;
	}
	
	
	//Delete payment
	@DELETE
	@Path("/removePaymentData")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePaymentsInfo(String payments)
	{
		//Convert the input string to an XML document
		Document pdoc = Jsoup.parse(payments, "", Parser.xmlParser());
		
		//Read the value from the element customer id
		String pid = pdoc.select("id").text();
		String output = payServ.deletePaymentsDetails(pid);
	
	return output;
	}
}
