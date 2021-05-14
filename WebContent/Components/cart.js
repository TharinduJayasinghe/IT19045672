$(document).ready(function()
{ 
	if ($("#alertSuccess").text().trim() == "") 
	{ 
		$("#alertSuccess").hide(); 
	} 
	$("#alertError").hide(); 
}); 

//SAVE
$(document).on("click", "#btnSave", function(event)
{ 
	// Clear alerts---------------------
	 $("#alertSuccess").text(""); 
	 $("#alertSuccess").hide(); 
	 $("#alertError").text(""); 
	 $("#alertError").hide();
 
	// Form validation-------------------
	var status = validateCartForm(); 
	if (status != true) 
	{ 
		$("#alertError").text(status); 
		$("#alertError").show(); 
		return; 
 	} 

	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT"; 
	 $.ajax( 
	 { 
	 url : "CartsAPI", 
	 type : type, 
	 data : $("#formItem").serialize(), 
	 dataType : "text", 
	 complete : function(response, status) 
	 { 
	 	onCartSaveComplete(response.responseText, status); 
	 } 
 }); 
});

function onCartSaveComplete(response, status)
{ 
	if (status == "success") 
	{ 
		 var resultSet = JSON.parse(response); 
		 if (resultSet.status.trim() == "success") 
		 { 
		 $("#alertSuccess").text("Successfully saved."); 
		 $("#alertSuccess").show(); 
		 $("#divItemsGrid").html(resultSet.data); 
		 } else if (resultSet.status.trim() == "error") 
		 { 
		 $("#alertError").text(resultSet.data); 
		 $("#alertError").show(); 
		 } 
		 } else if (status == "error") 
		 { 
		 $("#alertError").text("Error while saving."); 
		 $("#alertError").show(); 
		 } else
		 { 
		 $("#alertError").text("Unknown error while saving.."); 
		 $("#alertError").show(); 
		 } 
		 $("#hidItemIDSave").val(""); 
		 $("#formItem")[0].reset(); 
	}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{ 
	 $("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val()); 
	 $("#category").val($(this).closest("tr").find('td:eq(0)').text()); 
	 $("#quantity").val($(this).closest("tr").find('td:eq(1)').text()); 
	 $("#itemName").val($(this).closest("tr").find('td:eq(2)').text()); 
});

// CLIENT-MODEL================================================================
function validateCartForm() 
{ 
	// CATEGORY
	if ($("#category").val().trim() == "") 
	{ 
	 	return "Insert category."; 
	} 
	// QUANTITY
	if ($("#quantity").val().trim() == "") 
	{ 
	 	return "Insert quantity."; 
	}
	// ITEM NAME-------------------------------
	if ($("#itemName").val().trim() == "") 
	{ 
	 	return "Insert itemName."; 
	} 
	return true;
}

$(document).on("click", ".btnRemove", function(event)
{ 
	 $.ajax( 
	 { 
		 url : "CartsAPI", 
		 type : "DELETE", 
		 data : "cartID=" + $(this).data("cartid"),
		 dataType : "text", 
	 complete : function(response, status) 
	 { 
	 	onCartDeleteComplete(response.responseText, status); 
	 } 
 }); 
});

function onCartDeleteComplete(response, status)
{ 
if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully deleted."); 
	 $("#alertSuccess").show(); 
	 $("#divItemsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
	 $("#alertError").text(resultSet.data); 
	 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
	 $("#alertError").text("Error while deleting."); 
	 $("#alertError").show(); 
	 } else
	 { 
	 $("#alertError").text("Unknown error while deleting.."); 
	 $("#alertError").show(); 
	 } 
}