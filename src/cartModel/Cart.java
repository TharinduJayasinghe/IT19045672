package cartModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Cart {
	//A common method to connect to the DB
		private Connection connect() 
		{ 
			Connection con = null; 
			try
			{ 
				Class.forName("com.mysql.jdbc.Driver"); 
		 
				//Provide the correct details: DBServer/DBName, username, password 
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pafproject", "root", ""); 
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
			return con; 
		} 
		// add function
		public String insertCartItem(String category, String quantity, String itemName) //insert
		{ 
			String output = ""; 
			try
			{ 
				Connection con = connect(); //calling the connection method to check
				if (con == null) 
				{
					return "Error while connecting to the database for inserting.";
				} 
				// create a prepared statement
				String query = " insert into cart(`cartID`,`category`,`quantity`,`itemName`)"+" values (?, ?, ?, ?)"; //
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				// binding values
				preparedStmt.setInt(1, 0); 
				preparedStmt.setString(2, category); 
				preparedStmt.setString(3, quantity); 
				preparedStmt.setString(4, itemName); 
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				String newCart = readCart(); 
				output = "{\"status\":\"success\", \"data\": \"" + newCart + "\"}";  
			} 
			catch (Exception e) 
			{ 
				output = "{\"status\":\"error\", \"data\": \"Error while adding cart items.\"}"; 
				System.err.println(e.getMessage()); 
			} 
		 return output; 
		 }
		

	public String readCart() { //retrieve
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{
				return "Error while connecting to the database for reading.";
			} 
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Category</th>" 
					+"<th>Quantity</th><th>Item Name</th>" 
					+ "<th>Update</th><th>Remove</th></tr>";
  
	 
			String query = "select * from cart"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			// iterate through the rows in the result set
			while (rs.next()) //loop raw to raw
			{ 
				String cartID = Integer.toString(rs.getInt("cartID")); 
				String category = rs.getString("category"); 
				String quantity = Integer.toString(rs.getInt("quantity")); 
				String itemName = rs.getString("itemName"); 
				
				//add to the html table
				output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + cartID
						 + "'>" + category + "</td>";
				 output += "<td>" + quantity + "</td>"; 
				 output += "<td>" + itemName + "</td>"; 
			
				// buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-cartid='"
						 + cartID + "'>" + "</td></tr>";
			} 
			con.close(); 
			// Complete the html table
			output += "</table>"; 
		} 
		catch (Exception e) //check errors
		{ 
			output = "Error while reading the items."; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	 	}

	public String updateCartItems(String cartID, String category, String quantity, String itemName){ //update
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for updating.";
			 } 
			 // create a prepared statement
			 String query = "UPDATE cart SET category=?,quantity=?,itemName=? WHERE cartID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setString(1, category); 
			 preparedStmt.setInt(2, Integer.parseInt(quantity)); 
			 preparedStmt.setString(3, itemName); 
			 preparedStmt.setInt(4, Integer.parseInt(cartID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newCart = readCart(); 
		 	 output = "{\"status\":\"success\", \"data\": \"" + 
		 			newCart + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while updating cart details.\"}";  
				System.err.println(e.getMessage());
		 } 
		 	return output; 
		 }


	public String deleteCartItems(String cartID) { //delete
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{
				return "Error while connecting to the database for deleting.";
			} 
			// create a prepared statement
			String query = "delete from cart where cartID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(cartID)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newCart = readCart(); 
			output = "{\"status\":\"success\", \"data\": \"" + newCart + "\"}"; 
		} 
		catch (Exception e) 
		{ 
			output = "{\"status\":\"error\", \"data\": \"Error while deleting cart items.\"}";  
			System.err.println(e.getMessage());
		} 
			return output; 
		}
}
