package application.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.Models.F_Fish_Buyers_Account;



public class Foreign_Fish_Buyers_AccountServices {
	
	Connection connection;
	
	public boolean addEntry(F_Fish_Buyers_Account entry ) throws SQLException {
		
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery= "INSERT INTO F_Fish_Buyers_Account (Date, Reason,To_Pay,Paid)" + 
							"VALUES (?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,entry.getDate());
			preparedStatement.setString(2, entry.getReason());
			preparedStatement.setDouble(3, entry.getTo_Pay());
			preparedStatement.setDouble(4, entry.getPaid());
		
			resultSet=preparedStatement.executeUpdate();
			
			if(resultSet != 0) {
				return true;
			}
			else 
				return false;
	
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
	}
	
	public boolean addEntryUncleared(F_Fish_Buyers_Account unclearEntry ) throws SQLException {
		connection=DBConnection.Connector();
		PreparedStatement preparedStatement=null;
		int resultSet;
		String insertQuery=  "INSERT INTO F_Fish_Uncleared (Date, Reason,To_Pay,Paid)" + 
				"VALUES (?,?,?,?)";
		
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,unclearEntry.getDate());
			preparedStatement.setString(2, unclearEntry.getReason());
			preparedStatement.setDouble(3, unclearEntry.getTo_Pay());
			preparedStatement.setDouble(4, unclearEntry.getPaid());
			resultSet=preparedStatement.executeUpdate();
			
			if(resultSet != 0) {
				return true;
			}
			else 
				return false;
	
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}finally {
			connection.close();
		}
		
	}

}
