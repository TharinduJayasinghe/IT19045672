<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="cartModel.Cart" %>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.2.1.js" integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
	<script src="Components/cart.js"></script>
</head>
<body>
	<div class="container"><div class="row"><div class="col-6"> 
	
	<h1>Cart Management</h1>
	<form id="formItem" name="formItem">
 		 Category: 
 		 <input id="category" name="category" type="text" 
 				class="form-control form-control-sm">
 				
 		 <br> Quantity: 
		 <input id="quantity" name="quantity" type="text" 
		 		class="form-control form-control-sm">
		 		
		 <br> Item name: 
		 <input id="itemName" name="itemName" type="text" 
		 		class="form-control form-control-sm">
		 		
		 <br>
		 <input id="btnSave" name="btnSave" type="button" value="Save" 
		 		class="btn btn-primary">
		 <input type="hidden" id="hidItemIDSave" 
		 		name="hidItemIDSave" value="">
	</form>
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	<br>
	<div id="divItemsGrid">
		 <%
			 Cart cartObj = new Cart(); 
			 out.print(cartObj.readCart()); 
		 %>
	</div>
</div> </div> </div>
</body>
</html>