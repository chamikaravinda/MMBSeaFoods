package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

public class DashboardHomeService {
	
	Connection connection ;
	
	NumberFormat formatter = new DecimalFormat("#0.00"); 
	
	public String TotalLots() throws SQLException {
		
		int total = 0;
		connection =DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet result =null;
		String query = "select * from Fish_Lot where Sold_To IS NULL "; 
		preparedStatement = connection.prepareStatement(query);
		
		result = preparedStatement.executeQuery();
		
		while(result.next()) {
			
			total++;
			
		}
		connection.close();
		return Integer.toString(total);
		
	}
	
	public String TotalCommition() throws SQLException {
		
		double total = 0;
		connection =DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet result =null;
		String query = "select * from Third_Party_Acc_Uncleared"; 
		preparedStatement = connection.prepareStatement(query);
		
		result = preparedStatement.executeQuery();
		
		while(result.next()) {
			
			total=total+result.getDouble("To_Pay");			
		}
		String Stotal = formatter.format(total);
		connection.close();
		return "Rs."+Stotal;
		
	}
	
	public String TotalBoat() throws SQLException {
		
		double total = 0;
		connection =DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet result =null;
		String query = "select * from Boat_Account_UnCleared"; 
		preparedStatement = connection.prepareStatement(query);
		
		result = preparedStatement.executeQuery();
		
		while(result.next()) {
			
			total=total+result.getDouble("To_Pay");			
		}
		String Stotal = formatter.format(total);
		connection.close();
		return "Rs."+Stotal;
		
	}
	
	public String TotalBuyer() throws SQLException {
		
		double total = 0;
		connection =DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		ResultSet result =null;
		String query = "select * from F_Fish_Uncleared"; 
		preparedStatement = connection.prepareStatement(query);
		
		result = preparedStatement.executeQuery();
		
		while(result.next()) {
			
			total=total+result.getDouble("To_Pay");			
		}
		String Stotal =formatter.format(total);
		connection.close();
		return "Rs."+Stotal;
		
	}

}
