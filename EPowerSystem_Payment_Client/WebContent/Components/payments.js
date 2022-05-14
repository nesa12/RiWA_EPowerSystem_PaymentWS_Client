$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateProjectForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidProjectIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PaymentsAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onProjectSaveComplete(response.responseText, status);
		}
	});
});

function onProjectSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidProjectIDSave").val("");
	$("#formPayment")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidProjectIDSave").val(
					$(this).closest("tr").find('#hidProjectIDUpdate').val());
			$("#payment_status").val($(this).closest("tr").find('td:eq(0)').text());
			$("#amount").val($(this).closest("tr").find('td:eq(1)').text());
			$("#no_of_days").val($(this).closest("tr").find('td:eq(2)').text());
			$("#c_id").val($(this).closest("tr").find('td:eq(3)').text());
		});

// REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentsAPI",
		type : "DELETE",
		data : "id=" + $(this).data("pid"),
		dataType : "text",
		complete : function(response, status) {
			onProjectDeleteComplete(response.responseText, status);
		}
	});
});

function onProjectDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divPaymentsGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENT-MODEL=========================================================================
function validateProjectForm() {
	// PAYMENT STATUS 
    if ($("#payment_status").val().trim() == "") 
    { 
        return "Insert Payment Status."; 
    } 
    
    // NUMBER OF DAYS REMAINING
    if ($("#no_of_days").val().trim() == "") 
    { 
        return "Insert Number of days remaining."; 
    } 
    
    // is NUMBER OF DAYS a numerical value 
    var dAmount = $("#no_of_days").val().trim(); 
    if (!$.isNumeric(dAmount)) 
    { 
        return "Insert a numerical value for No of Days."; 
    } 

    
    // TOTAL AMOUNT DUE
    if ($("#amount").val().trim() == "") 
    { 
        return "Insert Total Amount Due."; 
    } 
    
    // is amount numerical value 
    var dAmount = $("#amount").val().trim(); 
    if (!$.isNumeric(dAmount)) 
    { 
        return "Insert a numerical value for Total Amount Due."; 
    } 
    
    // amount convert to decimal price 
    $("#amount").val(parseFloat(dAmount).toFixed(2)); 
    
    // DESCRIPTION
    if ($("#c_id").val().trim() == "") 
    { 
        return "Insert Customer ID."; 
    } 


	return true;
}